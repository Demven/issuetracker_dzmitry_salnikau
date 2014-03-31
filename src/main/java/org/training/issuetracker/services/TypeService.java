package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.hibernate.entities.Type;

public interface TypeService {

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
