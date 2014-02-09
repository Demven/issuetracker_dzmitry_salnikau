package org.training.issuetracker.dao.interfaces;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Status;

/**
 * Interface, providing access to the statuses and their data
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public interface StatusDAO {

	/**
	 * @return ArrayList of all issues' statuses
	 */
	public ArrayList<Status> getStatuses();
	
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
