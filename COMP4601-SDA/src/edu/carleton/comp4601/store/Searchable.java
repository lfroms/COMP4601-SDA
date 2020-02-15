package edu.carleton.comp4601.store;

import java.util.List;

import edu.carleton.comp4601.models.Identifiable;

public interface Searchable<DataType extends Identifiable> {

	/**
	 * 
	 * @param terms
	 * @return A list of objects.
	 */
	public List<DataType> search(String terms);

}
