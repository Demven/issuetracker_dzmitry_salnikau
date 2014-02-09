package org.training.issuetracker.dao.interfaces;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Resolution;

/**
 * Interface, providing access to the resolutions and their data
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public interface ResolutionDAO {

	/**
	 * @return ArrayList of all issues' resolutions
	 */
	public ArrayList<Resolution> getResolutions();
	
	/**
	 * Returns Resolution object with stated resolutionId
	 * @param resolutionId
	 * @return Resolution 
	 */
	public Resolution getResolutionById(int resolutionId);
	
	/**
	 * Adds new resolution in a data storage
	 * @param Resolution
	 * @return boolean - true, if it was successful
	 */
	public boolean createResolution(Resolution resolution);
	
	/**
	 * Update the resolution's data 
	 * @param Resolution
	 * @return boolean - true, if it was successful
	 */
	public boolean updateResolution(Resolution resolution);
	
	/**
	 * Delete resolution from a data storage by the unique resolutionId
	 * @param resolutionId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteResolution(int resolutionId);
}
