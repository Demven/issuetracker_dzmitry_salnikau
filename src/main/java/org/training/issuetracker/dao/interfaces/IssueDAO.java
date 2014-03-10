package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.dao.hibernate.entities.Issue;

/**
 * Interface, providing access to the issues and their data
 * @author Dzmitry Salnikau
 * @since 03.01.2013
 */
public interface IssueDAO {
	
	/**
	 * @return List of all issues
	 */
	public List<Issue> getIssues();
	
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
