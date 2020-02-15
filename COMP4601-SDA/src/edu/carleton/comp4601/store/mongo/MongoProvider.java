package edu.carleton.comp4601.store.mongo;

import java.util.Objects;
import java.util.function.Supplier;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.store.DocumentMapper;

public final class MongoProvider<DocumentType extends Identifiable> {
	private final MongoClient mongoClient;
	private final MongoDatabase db;
	private final MongoCollection<Document> collection;

	private static final String SYSTEM_ID_FIELD = "_id";
	
	private final Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor;

	// PUBLIC INTERFACE
	
	public MongoProvider(
			Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor, MongoDBConfig config) {
	
		this.mongoClient = new MongoClient(config.getHostname(), config.getPort());
		this.db = mongoClient.getDatabase(config.getDatabaseName());
		this.collection = db.getCollection(config.getCollectionName());
		this.mapperConstructor = Objects.requireNonNull(mapperConstructor);
	}

	public final void upsertDocument(DocumentType document) {
		Bson filter = Filters.eq(SYSTEM_ID_FIELD, document.getId());
		ReplaceOptions options = new ReplaceOptions().upsert(true);

		Document documentToSave = mapperConstructor.get().serialize(document);
		collection.replaceOne(filter, documentToSave, options);
	}

	public final Identifiable getDocument(Integer documentId) {
		FindIterable<Document> cursor = collection.find(new BasicDBObject(SYSTEM_ID_FIELD, documentId));
		MongoCursor<Document> c = cursor.iterator();

		if (!c.hasNext()) {
			return null;
		}

		Document document = c.next();

		try {
			return mapperConstructor.get().deserialize(document);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
