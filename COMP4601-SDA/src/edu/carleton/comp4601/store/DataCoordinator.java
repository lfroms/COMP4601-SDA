package edu.carleton.comp4601.store;

import java.util.List;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.mongo.MongoDBConfig;
import edu.carleton.comp4601.store.mongo.MongoMapper;
import edu.carleton.comp4601.store.mongo.MongoProvider;

public final class DataCoordinator implements Storable<WebDocument>, Searchable<WebDocument> {
	private static MongoProvider<WebDocument> documentsDatabase = 
			new MongoProvider<>(MongoMapper::new, getDocumentsDatabaseConfig());
	
	private static final MongoDBConfig getDocumentsDatabaseConfig() {
		return new MongoDBConfig(
				"localhost",
				27017,
				"crawler",
				"documents"
			);
	}

	@Override
	public void upsert(WebDocument input) {
		documentsDatabase.upsertDocument(input);		
	}

	@Override
	public WebDocument find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebDocument> search(String terms) {
		// TODO Auto-generated method stub
		return null;
	}
}
