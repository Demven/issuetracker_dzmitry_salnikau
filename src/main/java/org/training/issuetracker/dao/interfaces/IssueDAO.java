package org.training.issuetracker.dao.interfaces;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Issue;

/**
 * Interface, providing access to the issues and their data
 * @author Dzmitry Salnikau
 * @since 03.01.2013
 */
public interface IssueDAO {
	
	/**
	 * @return ArrayList of all issues
	 */
	public ArrayList<Issue> getIssues();
	
	/**
	 * Returns Issue object with stated issueId
	 * @param issueId
	 * @return Issue 
	 */
	public Issue getIssueById(Integer issueId);
	
	/**
	 * Adds new issue in a data storage
	 * @param Issue
	 * @return boolean - true, if it was successful
	 */
	public boolean createIssue(Issue issue);
	
	/**
	 * Update the issue's data 
	 * @param Issue
	 * @return boolean - true, if it was successful
	 */
	public boolean updateIssue(Issue issue);
	
	/**
	 * Delete issue from a data storage by the unique issueId
	 * @param issueId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteIssue(Integer issueId);
}
