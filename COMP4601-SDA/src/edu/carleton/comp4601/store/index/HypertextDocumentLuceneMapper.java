package edu.carleton.comp4601.store.index;

import java.lang.reflect.InvocationTargetException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;

import edu.carleton.comp4601.models.HypertextDocument;
import edu.carleton.comp4601.store.DocumentMapper;

final class HypertextDocumentLuceneMapper implements DocumentMapper<HypertextDocument, Document> {

	@Override
	public Document serialize(HypertextDocument input) {
		Document output = new Document();
		
		output.add(new StoredField(IndexDocumentFields.CONTENT, input.getContent()));
		output.add(new StoredField(IndexDocumentFields.TYPE, "text/html"));
		
		return output;
	}

	@Override
	public HypertextDocument deserialize(Document input)
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
