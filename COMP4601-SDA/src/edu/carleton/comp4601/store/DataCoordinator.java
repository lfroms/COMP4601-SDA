package edu.carleton.comp4601.store;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.graph.GraphProvider;
import edu.carleton.comp4601.store.graph.GraphMapper;
import edu.carleton.comp4601.store.index.LuceneMapper;
import edu.carleton.comp4601.store.index.LuceneProvider;
import edu.carleton.comp4601.store.mongo.MongoDBConfig;
import edu.carleton.comp4601.store.mongo.MongoMapper;
import edu.carleton.comp4601.store.mongo.MongoProvider;

public final class DataCoordinator implements Storable<WebDocument>, Searchable<WebDocument> {
	private static DataCoordinator singleInstance = null;
	
	public static DataCoordinator getInstance() {
		if (singleInstance == null) {
			singleInstance = new DataCoordinator();
		}
		
		return singleInstance;
	}
	
	private static Storable<WebDocument> documentsDatabase = 
			new MongoProvider<>(MongoMapper::new, getDocumentsDatabaseConfig());

	private static SearchableAndStorable<WebDocument> luceneIndex = 
			new LuceneProvider<>(LuceneMapper::new);

	private static GraphProvider<WebDocument> graph = new GraphProvider<>(GraphMapper::new);
	
	Queue<WebDocument> queue = new ArrayDeque<WebDocument>();

	@Override
	public void upsert(WebDocument input) {
		graph.upsert(input);
		luceneIndex.upsert(input);
		queue.add(input);	
	}

	@Override
	public Optional<WebDocument> find(Integer id) {
		return documentsDatabase.find(id);
	}
	
	public void processAndStoreData() {
		System.out.println("NOTICE: Processing PageRank for graph...");
		Map<Integer, Double> pageRanks = graph.getRanksForAllObjects();

		System.out.println("NOTICE: Indexing and persisting documents...");
		pageRanks.forEach((id, score) -> {
			WebDocument document = queue.poll();
			document.setPageRankScore(score);

			documentsDatabase.upsert(document);
		});
		
		queue.clear();
	}

	@Override
	public List<WebDocument> search(String terms) {
		return luceneIndex.search(terms);
	}
	
	public List<WebDocument> searchDistributed(String terms) {
		// TODO Implement using DS
		return null;
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
}
