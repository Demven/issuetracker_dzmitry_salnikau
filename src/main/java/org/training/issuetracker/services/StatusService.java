package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.entities.Status;

public interface StatusService {

	/**
	 * @return List of all issues' statuses
	 */
	public List<Status> getStatuses();
	
	/**
	 * Returns Status object with stated statusId
	 * @param statusId
	 * @return Status 
	 */
	public Status getStatusById(int statusId);
	
	/**
	 * Adds new status in a data storage
	 * @param Status
	 * @return boolean - true, if it was successful
	 */
	public boolean createStatus(Status status);
	
	/**
	 * Update the status' data 
	 * @param Status
	 * @return boolean - true, if it was successful
	 */
	public boolean updateStatus(Status status);
	
	/**
	 * Delete status from a data storage by the unique statusId
	 * @param statusId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteStatus(int statusId);
}
