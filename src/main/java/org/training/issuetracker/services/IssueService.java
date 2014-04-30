package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.entities.Issue;

public interface IssueService {

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
	 * @param filter
	 * @return List of all issues that matches search filter
	 */
	public List<Issue> getIssuesUsingFilter(String filter);
	
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
