package org.training.issuetracker.commands.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.managers.ConfigurationManager;
import org.training.issuetracker.managers.SessionManager;

public class NoCommand implements Command { 
	
	private DAOFactory hibernateFactory;
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 

		// In case of direct address to the servlet - show main page
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
		
		hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		IssueDAO issueDAO = hibernateFactory.getIssueDAO();
		
		List<Issue> allIssues = issueDAO.getIssues();
		
		SessionManager sessionManager = new SessionManager();
		UserBean loginUser = (UserBean) sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER);
		
		if(loginUser != null){
			// if a user is authorized - show him assigned issues
			ArrayList<Issue> assignedIssues = getAssignedIssues(allIssues, loginUser.getUserId());
			if(assignedIssues.size() > 0){
				request.setAttribute("assignedIssues", assignedIssues);		
				request.setAttribute("pageTitle", "Assigned issues");	
			} else{	
				request.setAttribute("pageTitle", "No assigned issues");	
			}
		} else{
			// show the latest issues
			ArrayList<Issue> latestIssues = getLatestIssues(allIssues);
			request.setAttribute("latestIssues", latestIssues);
			request.setAttribute("pageTitle", "Latest issues");	
		}
		
		return page;
		
	} 

	/**
	 * Search in the received list of issues the latest of them and form new list, which then returns.
	 * @param allIssues - list of issues
	 * @return ArrayList<Issue> - list of N latest issues
	 */
	private ArrayList<Issue> getLatestIssues(List<Issue> allIssues){
		ArrayList<Issue> latestIssues = new ArrayList<Issue>();
		
		int number = Issue.MAX_SHOWN_NUMBER;
		if(allIssues.size() < Issue.MAX_SHOWN_NUMBER){
			number = allIssues.size();
		}
		
		for(int i=0; i < number; i++){
			latestIssues.add(allIssues.get(i));
		}
		
		return latestIssues;
	}
	
	/**
	 * Search in the received list of issues of the ones, which are assigned to the user, then returns list of them.
	 * @param allIssues - list of issues
	 * @param userId - unique ID of a user
	 * @return ArrayList<Issue> - list of N assigned issues for the user
	 */
	private ArrayList<Issue> getAssignedIssues(List<Issue> allIssues, int userId){
		ArrayList<Issue> assignedIssues = new ArrayList<Issue>();
		
		int number = 0;
		for(Issue issue: allIssues){
			if(number <= Issue.MAX_SHOWN_NUMBER){
				if(issue.getAssignee() != null && issue.getAssignee().getUserId() == userId){
					assignedIssues.add(issue);
					number++;
				}
			} else{
				break;
			}
		}
		
		return assignedIssues;
	}
}
