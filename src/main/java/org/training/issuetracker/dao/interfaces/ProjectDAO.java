package org.training.issuetracker.dao.interfaces;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Project;

/**
 * Interface, providing access to the projects and their data
 * @author Dzmitry Salnikau
 * @since 03.01.2013
 */
public interface ProjectDAO {
	
	/**
	 * @return ArrayList of all projects
	 */
	public ArrayList<Project> getProjects();
	
	/**
	 * Returns Project object with stated projectId
	 * @param projectId
	 * @return Project 
	 */
	public Project getProjectById(int projectId);
	
	/**
	 * Defines the unique project's Id by the name
	 * @param firstName
	 * @return String projectId
	 */
	public Integer getProjectIdByName(String name);
	
	/**
	 * Adds new project in a data storage
	 * @param Project
	 * @return boolean - true, if it was successful
	 */
	public boolean createProject(Project project);
	
	/**
	 * Update the project's data 
	 * @param Project
	 * @return boolean - true, if it was successful
	 */
	public boolean updateProject(Project project);
	
	/**
	 * Delete project from a data storage by the unique projectId
	 * @param projectId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteProject(int projectId);
}
