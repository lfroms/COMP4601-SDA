package edu.carleton.comp4601.store.index;

import java.lang.reflect.InvocationTargetException;

import org.apache.lucene.document.Document;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.ApplicationSpecificMapper;
import edu.carleton.comp4601.store.DocumentMapper;

public final class LuceneMapper extends ApplicationSpecificMapper implements DocumentMapper<WebDocument, Document> {

	@Override
	public Document serialize(WebDocument input) {
		// TODO Auto-generated method stub
		return null;
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
		
		// TODO Auto-generated method stub
		return null;
	}

}
