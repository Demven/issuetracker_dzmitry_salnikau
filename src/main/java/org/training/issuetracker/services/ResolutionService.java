package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.hibernate.entities.Resolution;

public interface ResolutionService {

	/**
	 * @return List of all issues' resolutions
	 */
	public List<Resolution> getResolutions();
	
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
