package edu.carleton.comp4601.store.mongo;

import java.lang.reflect.InvocationTargetException;

import org.bson.Document;
import org.json.JSONObject;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.models.HypertextDocument;
import edu.carleton.comp4601.store.DocumentMapper;

public final class MongoMapper implements DocumentMapper<WebDocument> {

	@Override
	public Document serialize(WebDocument input) {
		return Document.parse(input.toJSON().toString());
	}

	@Override
	public WebDocument deserialize(Document input) 
			throws 
			InstantiationException, 
			IllegalAccessException, 
			IllegalArgumentException, 
			InvocationTargetException, 
			NoSuchMethodException, 
			SecurityException {

		JSONObject parsedJSON = new JSONObject(input.toJson());
		
		String typeName = parsedJSON.getString(WebDocument.TYPE_FIELD);
		Class<? extends WebDocument> Clazz = classForTypeName(typeName);
		
		return Clazz.getDeclaredConstructor().newInstance(parsedJSON);
	}
	
	private Class<? extends WebDocument> classForTypeName(String typeName) {
		switch(typeName) {
		case HypertextDocument.TYPE_NAME:
			return HypertextDocument.class;
		case "binary":
			return null;
		}
		
		return null;
	}

}
