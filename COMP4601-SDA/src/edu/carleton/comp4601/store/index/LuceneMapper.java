package edu.carleton.comp4601.store.index;

import java.lang.reflect.InvocationTargetException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.search.ScoreDoc;

import edu.carleton.comp4601.models.BinaryDocument;
import edu.carleton.comp4601.models.HypertextDocument;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.DocumentMapper;

public final class LuceneMapper implements DocumentMapper<WebDocument, Document> {
	
	@Override
	public Document serialize(WebDocument input) {
		String typeName = input.getTypeName();
		
		Document baseDocument = createBaseDocument(input);
		
		switch(typeName) {
		case HypertextDocument.TYPE_NAME:
			HypertextDocumentLuceneMapper hypertextMapper = new HypertextDocumentLuceneMapper();
			
			Document serializedHypertext = hypertextMapper.serialize((HypertextDocument) input);
			
			serializedHypertext.forEach(field -> {
				baseDocument.add(field);
			});
			
			return baseDocument;
			
		case BinaryDocument.TYPE_NAME:
			BinaryDocumentLuceneMapper binaryMapper = new BinaryDocumentLuceneMapper();
			
			Document serializedBinary = binaryMapper.serialize((BinaryDocument) input);
			
			serializedBinary.forEach(field -> {
				baseDocument.add(field);
			});
			
			return baseDocument;
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
		output.setName(input.get(IndexDocumentFields.TITLE));
		output.setScore(scoreDoc.score);
		output.setContent(input.get(IndexDocumentFields.CONTENT));
		output.setUrl(input.get(IndexDocumentFields.URL));

		return output;
	}
	
	// PRIVATE HELPERS ==================================================================
	
	public static Document createBaseDocument(WebDocument input) {
		Document output = new Document();
		
		output.add(new StoredField(IndexDocumentFields.ID, input.getId()));
		output.add(new StoredField(IndexDocumentFields.TITLE, input.getTitle()));
		output.add(new StoredField(IndexDocumentFields.INDEXED_BY, "Lukas & Britta"));		
		output.add(new StoredField(IndexDocumentFields.URL, input.getURL().toString()));
		output.add(new StoredField(IndexDocumentFields.DATE, input.getLastCrawledTime()));
		
		return output;
	}

}
