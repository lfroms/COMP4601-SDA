package edu.carleton.comp4601.store.mongo;

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
	private static MongoClient mongoClient = new MongoClient("localhost", 27017);
	private static MongoDatabase db = mongoClient.getDatabase("crawler");
	private static MongoCollection<Document> collection = db.getCollection("pages");

	private static final String SYSTEM_ID_FIELD = "_id";
	
	private DocumentMapper<DocumentType> mapper;

	public MongoProvider(DocumentMapper<DocumentType> mapper) {
		this.mapper = mapper;
	}

	// MARK: Public interface.

	public final void saveDocument(DocumentType document) {
		Bson filter = Filters.eq(SYSTEM_ID_FIELD, document.getId());
		ReplaceOptions options = new ReplaceOptions().upsert(true);

		Document documentToSave = mapper.serialize(document);
		collection.replaceOne(filter, documentToSave, options);
	}

	public final DocumentType findDocumentById(Integer documentId) {
		FindIterable<Document> cursor = collection.find(new BasicDBObject(SYSTEM_ID_FIELD, documentId));
		MongoCursor<Document> c = cursor.iterator();

		if (!c.hasNext()) {
			return null;
		}

		Document document = c.next();

		return mapper.deserialize(document);
	}

}
