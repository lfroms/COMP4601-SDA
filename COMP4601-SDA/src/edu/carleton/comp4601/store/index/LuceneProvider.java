package edu.carleton.comp4601.store.index;

import java.util.List;
import java.util.function.Supplier;

import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.store.DocumentMapper;
import edu.carleton.comp4601.store.MappableProvider;
import edu.carleton.comp4601.store.Searchable;
import edu.carleton.comp4601.store.Storable;

public final class LuceneProvider<DocumentType extends Identifiable> extends MappableProvider<DocumentType>
		implements Storable<DocumentType>, Searchable<DocumentType> {

	public LuceneProvider(Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor) {
		super(mapperConstructor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void upsert(DocumentType input) {
		// TODO Auto-generated method stub

	}

	@Override
	public DocumentType find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentType> search(String terms) {
		// TODO Auto-generated method stub
		return null;
	}

}
