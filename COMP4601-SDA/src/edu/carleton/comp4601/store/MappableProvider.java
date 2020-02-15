package edu.carleton.comp4601.store;

import java.util.Objects;
import java.util.function.Supplier;

import edu.carleton.comp4601.models.Identifiable;

public abstract class MappableProvider<DocumentType extends Identifiable> {
	protected final Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor;
	
	public MappableProvider(Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor) {
		this.mapperConstructor = Objects.requireNonNull(mapperConstructor);
	}
}
