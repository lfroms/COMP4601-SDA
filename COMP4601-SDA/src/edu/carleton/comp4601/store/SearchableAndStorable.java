package edu.carleton.comp4601.store;

import edu.carleton.comp4601.models.Identifiable;

public interface SearchableAndStorable<DataType extends Identifiable> extends Storable<DataType>, Searchable<DataType> {

}
