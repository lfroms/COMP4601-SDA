package edu.carleton.comp4601.store.index;

import java.lang.reflect.InvocationTargetException;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

import edu.carleton.comp4601.models.BinaryDocument;
import edu.carleton.comp4601.models.HypertextDocument;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.DocumentMapper;

public final class LuceneMapper implements DocumentMapper<WebDocument, Document> {
	
	@Override
	public Document serialize(WebDocument input) {
		String typeName = input.getTypeName();
		
		switch(typeName) {
		case HypertextDocument.TYPE_NAME:
			HypertextDocumentLuceneMapper hypertextMapper = new HypertextDocumentLuceneMapper();
			
			return hypertextMapper.serialize((HypertextDocument) input);
			
		case BinaryDocument.TYPE_NAME:
			BinaryDocumentLuceneMapper binaryMapper = new BinaryDocumentLuceneMapper();
			
			return binaryMapper.serialize((BinaryDocument) input);
		}
		
		throw new RuntimeException("No mapper available to serialize \"" + typeName + "\"");
	}

	@Override
	public WebDocument deserialize(Document input) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		throw new UnsupportedOperationException("No way to deserialize Lucene document into WebDocument.");
	}

	public edu.carleton.comp4601.dao.Document deserialize(Document input, ScoreDoc scoreDoc) {
		edu.carleton.comp4601.dao.Document output = new edu.carleton.comp4601.dao.Document();
		
		output.setId(Integer.valueOf(input.get(IndexDocumentFields.ID)));
		// TODO: Set a name.
		output.setName("test doc name");
		output.setScore(scoreDoc.score);
		output.setContent(input.get(IndexDocumentFields.CONTENT));
		output.setUrl(input.get(IndexDocumentFields.URL));

		return output;
	}
}
