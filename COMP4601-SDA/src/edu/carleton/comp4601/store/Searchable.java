package edu.carleton.comp4601.store;


import edu.carleton.comp4601.dao.DocumentCollection;
import edu.carleton.comp4601.models.Identifiable;

public interface Searchable<DataType extends Identifiable> {

	/**
	 * 
	 * @param terms
	 * @return A list of objects.
	 */
	public DocumentCollection search(String terms);

}
