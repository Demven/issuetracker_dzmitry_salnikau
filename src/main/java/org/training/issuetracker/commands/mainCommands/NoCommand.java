package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.logic.SessionLogic;
import org.training.issuetracker.managers.ConfigurationManager;

public class NoCommand implements Command { 

	private static final String PARAM_NAME_LOGIN = "login"; 
	
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException { 
		
		// In case of direct address to the servlet - show main page
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		UserDAO userDAO = mysqlFactory.getUserDAO();
		IssueDAO issueDAO = mysqlFactory.getIssueDAO();
		
		SessionLogic sessionLogic = new SessionLogic();
		String login = (String) sessionLogic.getSessionValue(request, PARAM_NAME_LOGIN);
		ArrayList<Issue> allIssues = issueDAO.getIssues();
		
		if(login != null && login != ""){
			// if a user is authorized
			User loginUser = userDAO.getUserByEmail(login);
			request.setAttribute("loginUser", loginUser);
			// show him assigned issues
			ArrayList<Issue> assignedIssues = getAssignedIssues(allIssues, loginUser.getUserId());
			request.setAttribute("assignedIssues", assignedIssues);		
		} else{
			// show the latest issues
			ArrayList<Issue> latestIssues = getLatestIssues(allIssues);
			ArrayList<User> assignees = userDAO.getUsers();
			request.setAttribute("assignees", assignees);
			request.setAttribute("latestIssues", latestIssues);
		}
		
		RequestDispatcher dispatcher = context.getRequestDispatcher(page); 
		dispatcher.forward(request, response);
	} 

	/**
	 * Search in the received list of issues the latest of them and form new list, which then returns.
	 * @param allIssues - list of issues
	 * @return ArrayList<Issue> - list of N latest issues
	 */
	private ArrayList<Issue> getLatestIssues(ArrayList<Issue> allIssues){
		Collections.sort(allIssues);
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
	private ArrayList<Issue> getAssignedIssues(ArrayList<Issue> allIssues, int userId){
		Collections.sort(allIssues);
		ArrayList<Issue> assignedIssues = new ArrayList<Issue>();
		
		int number = 0;
		for(Issue issue: allIssues){
			if(number <= Issue.MAX_SHOWN_NUMBER){
				if(issue.getAssignee() == userId){
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
