package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.entities.Priority;

public interface PriorityService {

	/**
	 * @return ArrayList of all issues' priorities
	 */
	public List<Priority> getPriorities();
	
	/**
	 * Returns Priority object with stated priorityId
	 * @param priorityId
	 * @return Priority 
	 */
	public Priority getPriorityById(int priorityId);
	
	/**
	 * Adds new priority in a data storage
	 * @param Priority
	 * @return boolean - true, if it was successful
	 */
	public boolean createPriority(Priority priority);
	
	/**
	 * Update the priority's data 
	 * @param Priority
	 * @return boolean - true, if it was successful
	 */
	public boolean updatePriority(Priority priority);
	
	/**
	 * Delete priority from a data storage by the unique priorityId
	 * @param priorityId
	 * @return boolean - true, if it was successful
	 */
	public boolean deletePriority(int priorityId);
}
