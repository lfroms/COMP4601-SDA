package edu.carleton.comp4601.store.graph;

import java.util.List;
import java.util.function.Supplier;

import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.store.DocumentMapper;
import edu.carleton.comp4601.store.MappableProvider;
import edu.carleton.comp4601.store.Searchable;
import edu.carleton.comp4601.store.Storable;

public final class GraphProvider<DocumentType extends Identifiable> extends MappableProvider<DocumentType>
		implements Storable<DocumentType> {

	public GraphProvider(Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor) {
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

}
