package edu.carleton.comp4601.store;

import java.util.List;
import java.util.Optional;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.graph.GraphMapper;
import edu.carleton.comp4601.store.graph.GraphProvider;
import edu.carleton.comp4601.store.index.LuceneMapper;
import edu.carleton.comp4601.store.index.LuceneProvider;
import edu.carleton.comp4601.store.mongo.MongoDBConfig;
import edu.carleton.comp4601.store.mongo.MongoMapper;
import edu.carleton.comp4601.store.mongo.MongoProvider;

public final class DataCoordinator implements Storable<WebDocument>, Searchable<WebDocument> {
	private static Storable<WebDocument> documentsDatabase = 
			new MongoProvider<>(MongoMapper::new, getDocumentsDatabaseConfig());

	private static SearchableAndStorable<WebDocument> luceneIndex = 
			new LuceneProvider<>(LuceneMapper::new);

	private static Storable<WebDocument> graph = 
			new GraphProvider<>(GraphMapper::new);

	@Override
	public void upsert(WebDocument input) {
		documentsDatabase.upsert(input);	
		luceneIndex.upsert(input);
		graph.upsert(input);
	}

	@Override
	public Optional<WebDocument> find(Integer id) {
		return documentsDatabase.find(id);
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
