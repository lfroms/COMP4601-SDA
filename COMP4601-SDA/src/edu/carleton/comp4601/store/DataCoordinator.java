package edu.carleton.comp4601.store;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.mongo.MongoMapper;
import edu.carleton.comp4601.store.mongo.MongoProvider;

public final class DataCoordinator {
	private static DocumentMapper<WebDocument> mongoMapper = new MongoMapper();
	private static MongoProvider<WebDocument> mongoProvider = new MongoProvider<>(mongoMapper);
}
