package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.entities.Build;

public interface BuildService {

	/**
	 * @return List of all builds for all projects
	 */
	public List<Build> getBuilds();
	
	/**
	 * Returns List of all builds for a specified project
	 * @param projectId - unique Id of a project
	 * @return List<Build> 
	 */
	public List<Build> getBuildsForProject(int projectId);
	
	/**
	 * Returns Build object with stated buildId
	 * @param buildId
	 * @return Build 
	 */
	public Build getBuildById(int buildId);
	
	/**
	 * Adds new build in a data storage
	 * @param Build
	 * @return boolean - true, if it was successful
	 */
	public boolean createBuild(Build build);
	
	/**
	 * Update the build's data 
	 * @param Build
	 * @return boolean - true, if it was successful
	 */
	public boolean updateBuild(Build build);
	
	/**
	 * Delete build from a data storage by the unique buildId
	 * @param buildId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteBuild(int buildId);
}
