package edu.carleton.comp4601.store.index;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.lucene.document.Document;

import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.store.DocumentMapper;
import edu.carleton.comp4601.store.MappableProvider;
import edu.carleton.comp4601.store.SearchableAndStorable;

public final class LuceneProvider<DocumentType extends Identifiable> extends MappableProvider<DocumentType, Document>
		implements SearchableAndStorable<DocumentType> {

	public LuceneProvider(Supplier<? extends DocumentMapper<DocumentType, Document>> mapperConstructor) {
		super(mapperConstructor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void upsert(DocumentType input) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<DocumentType> find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentType> search(String terms) {
		// TODO Auto-generated method stub
		return null;
	}

}