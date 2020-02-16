package edu.carleton.comp4601.store;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.jgrapht.io.ExportException;

import edu.carleton.comp4601.models.SaveableGraph;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.graph.GraphProvider;
import edu.carleton.comp4601.store.graph.GraphMapper;
import edu.carleton.comp4601.store.index.LuceneMapper;
import edu.carleton.comp4601.store.index.LuceneProvider;
import edu.carleton.comp4601.store.mongo.GraphMongoMapper;
import edu.carleton.comp4601.store.mongo.MongoDBConfig;
import edu.carleton.comp4601.store.mongo.WebDocumentMongoMapper;
import edu.carleton.comp4601.store.mongo.MongoProvider;

public final class DataCoordinator implements Storable<WebDocument>, Searchable<WebDocument> {
	private static DataCoordinator singleInstance = null;
	
	public static DataCoordinator getInstance() {
		if (singleInstance == null) {
			singleInstance = new DataCoordinator();
		}
		
		return singleInstance;
	}
	
	// STORABLE INSTANCES ===============================================================
	
	private static Storable<WebDocument> documentsDatabase = 
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
	
	public void processAndStoreData() {
		System.out.println("NOTICE: Processing PageRank for graph...");
		Map<Integer, Double> pageRanks = graphProvider.getRanksForAllObjects();

		System.out.println("NOTICE: Indexing and persisting documents...");
		pageRanks.forEach((id, score) -> {
			WebDocument document = queue.poll();
			document.setPageRankScore(score);

			documentsDatabase.upsert(document);
		});
		
		queue.clear();
		
		saveGraphToDatabase();
	}

	@Override
	public List<WebDocument> search(String terms) {
		return luceneIndex.search(terms);
	}
	
	public List<WebDocument> searchDistributed(String terms) {
		// TODO Implement using DS
		return null;
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private final void saveGraphToDatabase() {
		String serializedGraph;
		
		try {
			serializedGraph = graphProvider.toGraphViz();

		} catch (ExportException e) {
			e.printStackTrace();

			return;
		}

		SaveableGraph saveableGraph = new SaveableGraph(1, serializedGraph);

		graphsDatabase.upsert(saveableGraph);
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
