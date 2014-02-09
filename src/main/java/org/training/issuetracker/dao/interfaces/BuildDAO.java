package org.training.issuetracker.dao.interfaces;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Build;

/**
 * Interface, providing access to the builds and their data
 * @author Dzmitry Salnikau
 * @since 03.01.2013
 */
public interface BuildDAO {
	
	/**
	 * @return ArrayList of all builds for all projects
	 */
	public ArrayList<Build> getBuilds();
	
	/**
	 * Returns ArrayList of all builds for a specified project
	 * @param projectId - unique Id of a project
	 * @return ArrayList<Build> 
	 */
	public ArrayList<Build> getBuildsForProject(int projectId);
	
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
