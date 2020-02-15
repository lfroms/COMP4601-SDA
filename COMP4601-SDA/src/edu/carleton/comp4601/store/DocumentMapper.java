package edu.carleton.comp4601.store;

import org.bson.Document;

public interface DocumentMapper<DataType> {
	
	/**
	 * 
	 * @param input
	 * @return A type that is compatible with the storage provider being mapped for.
	 */
	Document serialize(DataType input);
	
	/**
	 * 
	 * @param input
	 * @return An application-compatible type.
	 */
	DataType deserialize(Document input);

}
