package edu.carleton.comp4601.store.index;

import java.lang.reflect.InvocationTargetException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;

import edu.carleton.comp4601.models.BinaryDocument;
import edu.carleton.comp4601.store.DocumentMapper;

final class BinaryDocumentLuceneMapper implements DocumentMapper<BinaryDocument, Document> {

	@Override
	public Document serialize(BinaryDocument input) {
		Document output = new Document();
		
		output.add(new StoredField(IndexDocumentFields.CONTENT, input.getContent()));
		output.add(new StoredField(IndexDocumentFields.TYPE, input.getMimeType()));
		
		return output;
		
	}

	@Override
	public BinaryDocument deserialize(Document input) 
			throws
			InstantiationException,
			IllegalAccessException,
			IllegalArgumentException,
			InvocationTargetException,
			NoSuchMethodException,
			SecurityException {

		throw new UnsupportedOperationException("No way to deserialize Lucene document into WebDocument.");
	}

}
