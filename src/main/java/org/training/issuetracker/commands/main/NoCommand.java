package org.training.issuetracker.commands.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.beans.IssueBean;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.dao.transferObjects.Priority;
import org.training.issuetracker.dao.transferObjects.Status;
import org.training.issuetracker.dao.transferObjects.Type;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.managers.ConfigurationManager;
import org.training.issuetracker.managers.SessionManager;

public class NoCommand implements Command { 
	
	private DAOFactory mysqlFactory;
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 

		// In case of direct address to the servlet - show main page
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
		
		mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		IssueDAO issueDAO = mysqlFactory.getIssueDAO();
		
		ArrayList<Issue> allIssues = issueDAO.getIssues();
		
		SessionManager sessionManager = new SessionManager();
		UserBean loginUser = (UserBean) sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER);
		
		if(loginUser != null){
			// if a user is authorized - show him assigned issues
			ArrayList<IssueBean> assignedIssues = getAssignedIssues(allIssues, loginUser.getUserId());
			if(assignedIssues.size() > 0){
				request.setAttribute("assignedIssues", assignedIssues);		
				request.setAttribute("pageTitle", "Assigned issues");	
			} else{	
				request.setAttribute("pageTitle", "No assigned issues");	
			}
		} else{
			// show the latest issues
			ArrayList<IssueBean> latestIssues = getLatestIssues(allIssues);
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
	private ArrayList<IssueBean> getLatestIssues(ArrayList<Issue> allIssues){
		Collections.sort(allIssues);
		ArrayList<Issue> latestIssues = new ArrayList<Issue>();
		
		int number = Issue.MAX_SHOWN_NUMBER;
		if(allIssues.size() < Issue.MAX_SHOWN_NUMBER){
			number = allIssues.size();
		}
		
		for(int i=0; i < number; i++){
			latestIssues.add(allIssues.get(i));
		}
		
		return convertIdsToObjects(latestIssues);
	}
	
	/**
	 * Search in the received list of issues of the ones, which are assigned to the user, then returns list of them.
	 * @param allIssues - list of issues
	 * @param userId - unique ID of a user
	 * @return ArrayList<Issue> - list of N assigned issues for the user
	 */
	private ArrayList<IssueBean> getAssignedIssues(ArrayList<Issue> allIssues, int userId){
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
		
		return convertIdsToObjects(assignedIssues);
	}
	
	/**
	 * Use received list of issues to create new list of IssueBean-objects
	 * for convenient use in JSP-pages
	 * @param issuesWithIds - ArrayList<Issue> - list, where all issue-objects
	 * have id-links to the according objects in the database
	 * @return ArrayList<IssueBean> - list, where all issue-objects
	 * contain dependent objects instead of ids.
	 */
	private ArrayList<IssueBean> convertIdsToObjects(ArrayList<Issue> issuesWithIds){
		ArrayList<IssueBean> issueBeans = new ArrayList<IssueBean>();
		
		UserDAO userDAO = mysqlFactory.getUserDAO();
		PriorityDAO priorityDAO = mysqlFactory.getPriorityDAO();
		TypeDAO typeDAO = mysqlFactory.getTypeDAO();
		StatusDAO statusDAO = mysqlFactory.getStatusDAO();
		
		ArrayList<User> users = userDAO.getUsers();
		ArrayList<Priority> priorities = priorityDAO.getPriorities();
		ArrayList<Type> types = typeDAO.getTypes();
		ArrayList<Status> statuses = statusDAO.getStatuses();
		
		for(Issue issue : issuesWithIds){
			// find assignee
			User assignee = new User();
			for(User user : users){
				if(issue.getAssignee() == user.getUserId()){
					assignee = user;
				}
			}
			
			// find priority
			Priority priority = null;
			for(Priority p : priorities){
				if(issue.getPriority() == p.getPriorityId()){
					priority = p;
				}
			}
			
			// find type
			Type type = null;
			for(Type t : types){
				if(issue.getType() == t.getTypeId()){
					type = t;
				}
			}
			
			// find status
			Status status = null;
			for(Status s : statuses){
				if(issue.getStatus() == s.getStatusId()){
					status = s;
				}
			}
			
			issueBeans.add(new IssueBean(
					issue.getIssueId(),
					issue.getCreateDate(),
					null,
					issue.getModifyDate(),
					null,
					issue.getSummary(),
					issue.getDescription(),
					status,
					null,
					type,
					priority,
					null,
					null,
					assignee)
			);
		}
		
		return issueBeans;
	}
}
