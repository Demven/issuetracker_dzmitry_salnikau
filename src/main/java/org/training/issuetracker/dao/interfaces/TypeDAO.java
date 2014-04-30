package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.dao.entities.Type;

/**
 * Interface, providing access to the types and their data
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public interface TypeDAO {

	/**
	 * @return List of all issues' types
	 */
	public List<Type> getTypes();
	
	/**
	 * Returns Type object with stated typeId
	 * @param typeId
	 * @return Type 
	 */
	public Type getTypeById(int typeId);
	
	/**
	 * Adds new type in a data storage
	 * @param Type
	 * @return boolean - true, if it was successful
	 */
	public boolean createType(Type type);
	
	/**
	 * Update the type's data 
	 * @param Type
	 * @return boolean - true, if it was successful
	 */
	public boolean updateType(Type type);
	
	/**
	 * Delete type from a data storage by the unique typeId
	 * @param typeId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteType(int typeId);
}
