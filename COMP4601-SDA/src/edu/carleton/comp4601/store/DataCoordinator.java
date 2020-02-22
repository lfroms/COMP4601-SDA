package edu.carleton.comp4601.store;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.jgrapht.io.ExportException;

import edu.carleton.comp4601.dao.Document;
import edu.carleton.comp4601.dao.DocumentCollection;
import edu.carleton.comp4601.helpers.DocumentScoreComparator;
import edu.carleton.comp4601.models.SaveableGraph;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.graph.GraphMapper;
import edu.carleton.comp4601.store.graph.GraphProvider;
import edu.carleton.comp4601.store.index.LuceneMapper;
import edu.carleton.comp4601.store.index.LuceneProvider;
import edu.carleton.comp4601.store.mongo.GraphMongoMapper;
import edu.carleton.comp4601.store.mongo.MongoDBConfig;
import edu.carleton.comp4601.store.mongo.MongoProvider;
import edu.carleton.comp4601.store.mongo.WebDocumentMongoMapper;
import edu.carleton.comp4601.utility.SDAConstants;
import edu.carleton.comp4601.utility.SearchException;
import edu.carleton.comp4601.utility.SearchResult;
import edu.carleton.comp4601.utility.SearchServiceManager;

public final class DataCoordinator implements Storable<WebDocument>, Searchable<WebDocument> {
	private static DataCoordinator singleInstance = null;
	
	public static DataCoordinator getInstance() {
		if (singleInstance == null) {
			singleInstance = new DataCoordinator();
		}
		
		return singleInstance;
	}
	
	// CONSTANTS ========================================================================
	
	private static final Integer GRAPH_DB_ID = 1;
	
	// STORABLE INSTANCES ===============================================================
	
	private static MongoProvider<WebDocument> documentsDatabase = 
			new MongoProvider<>(WebDocumentMongoMapper::new, getDocumentsDatabaseConfig());
	
	private static Storable<SaveableGraph> graphsDatabase = 
			new MongoProvider<>(GraphMongoMapper::new, getGraphsDatabaseConfig());

	private static SearchableAndStorable<WebDocument> luceneIndex = 
			new LuceneProvider<>(LuceneMapper::new);

	private static GraphProvider<WebDocument> graphProvider =
			new GraphProvider<>(GraphMapper::new);
	
	// PROCESSING QUEUE =================================================================
	
	Queue<WebDocument> queue = new ArrayDeque<WebDocument>();
	
	// PUBLIC INTERFACE =================================================================

	@Override
	public void upsert(WebDocument input) {
		graphProvider.upsert(input);
		luceneIndex.upsert(input);
		queue.add(input);	
	}

	@Override
	public Optional<WebDocument> find(Integer id) {
		return documentsDatabase.find(id);
	}
	
	public ArrayList<WebDocument> getAll() {
		return documentsDatabase.getAll();
	}
	
	@Override
	public void delete(Integer id) {
		luceneIndex.delete(id);
		documentsDatabase.delete(id);
		graphProvider.delete(id);
	}
	
	public boolean deleteByQuery(String query) {
		ArrayList<Document> results = luceneIndex.search(query).getDocuments();
		
		if (results.size() == 0) {
			return false;
		}
		
		results.forEach(result -> {
			delete(result.getId());
		});
		
		return true;
	}
	
	@Override
	public void reset() {
		graphProvider.reset();
		luceneIndex.reset();
		documentsDatabase.reset();
		graphsDatabase.reset();
	}
	
	public void loadPersistedData() {
		loadGraphFromDatabase();
	}
	
	public void processAndStoreData() {
		System.out.println("NOTICE: Processing PageRank for graph...");
		Map<Integer, Double> pageRanks = graphProvider.getRanksForAllObjects();

		System.out.println("NOTICE: Indexing and persisting documents...");

		pageRanks.forEach((id, score) -> {
			if (!queue.isEmpty()) {
				WebDocument document = queue.remove();
				document.setPageRankScore(pageRanks.get(document.getId()));
	
				documentsDatabase.upsert(document);
			}
		});
		
		queue.clear();
		
		saveGraphToDatabase();
	}

	@Override
	public DocumentCollection search(String terms) {
		return luceneIndex.search(terms);
	}
	
	public DocumentCollection searchDistributed(String terms) {
		DocumentCollection output = new DocumentCollection();

		SearchResult sr;
		
		try {
			sr = SearchServiceManager.getInstance().search(terms);
			
		} catch (ClassNotFoundException | IOException | SearchException e1) {
			e1.printStackTrace();
			return output;
		}

		// Perform local search.
		ArrayList<Document> docs = search(terms).getDocuments();

		// Wait 10 seconds.
		try {
			sr.await(SDAConstants.TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		} finally {
			SearchServiceManager.getInstance().reset();
		}
		
		ArrayList<Document> remoteDocs = sr.getDocs();
		
		if (remoteDocs != null) {
			// Merge documents.
			docs.addAll(sr.getDocs());
		}
		
		Collections.sort(docs, new DocumentScoreComparator());
		
		output.setDocuments(docs);
		
		return output;
	}
	
	// PRIVATE HELPERS ================================================================
	private final void saveGraphToDatabase() {
		String serializedGraph;
		
		try {
			serializedGraph = graphProvider.toGraphViz();

		} catch (ExportException e) {
			e.printStackTrace();
			System.err.println("Could not save graph data.");

			return;
		}

		SaveableGraph saveableGraph = new SaveableGraph(GRAPH_DB_ID, serializedGraph);

		graphsDatabase.upsert(saveableGraph);
	}
	
	private final void loadGraphFromDatabase() {
		Optional<SaveableGraph> savedGraph = graphsDatabase.find(GRAPH_DB_ID);
		
		if (savedGraph.isEmpty()) {
			return;
		}
		
		String serializedData = savedGraph.get().getSerializedData();	
		graphProvider.setDataUsingGraphViz(serializedData);
	}
	
	// PROVIDER CONFIGURATION ===========================================================
	
	private static final MongoDBConfig getDocumentsDatabaseConfig() {
		return new MongoDBConfig(
				"localhost",
				27017,
				"crawler",
				"documents"
			);
	}
	
	private static final MongoDBConfig getGraphsDatabaseConfig() {
		return new MongoDBConfig(
				"localhost",
				27017,
				"crawler",
				"graphs"
			);
	}
}
