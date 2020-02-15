package edu.carleton.comp4601.store;

import edu.carleton.comp4601.models.Identifiable;

interface Storable<DataType extends Identifiable> {
	
	/**
	 * 
	 * @param input
	 */
	public void upsert(DataType input);
	
	/**
	 * 
	 * @param id
	 * @return The object.
	 */
	public DataType find(Integer id);
}
