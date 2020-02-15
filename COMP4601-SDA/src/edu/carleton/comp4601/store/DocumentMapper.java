package edu.carleton.comp4601.store;

import java.lang.reflect.InvocationTargetException;

import org.bson.Document;

import edu.carleton.comp4601.models.Identifiable;

public interface DocumentMapper<DataType extends Identifiable> {
	
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
	DataType deserialize(Document input) 
			throws 
			InstantiationException, 
			IllegalAccessException, 
			IllegalArgumentException, 
			InvocationTargetException, 
			NoSuchMethodException, 
			SecurityException ;

}
