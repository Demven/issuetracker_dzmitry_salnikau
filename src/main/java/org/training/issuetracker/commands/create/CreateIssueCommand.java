package org.training.issuetracker.commands.create;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.Build;
import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.dao.transferObjects.Priority;
import org.training.issuetracker.dao.transferObjects.Project;
import org.training.issuetracker.dao.transferObjects.Type;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.managers.ConfigurationManager;
import org.training.issuetracker.managers.DateManager;
import org.training.issuetracker.managers.SessionManager;

public class CreateIssueCommand implements Command{
	
	private static final String PARAM_SUMMARY = "summary";
	private static final String PARAM_DESCRIPTION = "description";
	private static final String PARAM_STATUS_INDEX = "statusIndex";
	private static final String PARAM_TYPE_ID = "typeId";
	private static final String PARAM_PRIORITY_ID = "priorityId";
	private static final String PARAM_PROJECT_ID = "projectId";
	private static final String PARAM_BUILD_ID = "buildId";
	private static final String PARAM_ASSIGNEE_ID = "assigneeId";
	
	private static final int STATUS_INDEX_NEW = 1;
	private static final int STATUS_INDEX_ASSIGNED = 2;
	
	private static final Logger logger = Logger.getLogger(CreateIssueCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.ISSUE_PAGE_PATH);
		request.setAttribute("pageTitle", "New issue");
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		TypeDAO typeDAO = mysqlFactory.getTypeDAO();
		PriorityDAO priorityDAO = mysqlFactory.getPriorityDAO();
		ProjectDAO projectDAO = mysqlFactory.getProjectDAO();
		BuildDAO buildDAO = mysqlFactory.getBuildDAO();
		UserDAO userDAO = mysqlFactory.getUserDAO();
		IssueDAO issueDAO = mysqlFactory.getIssueDAO();
		
		String summary = request.getParameter(PARAM_SUMMARY);
		String description = request.getParameter(PARAM_DESCRIPTION);
		if(summary != null && !summary.equals("")
				&& description != null && !description.equals("")){
			// It is request to save a new issue
			Integer statusIndex = getIntValue(request.getParameter(PARAM_STATUS_INDEX));
			Integer typeId = getIntValue(request.getParameter(PARAM_TYPE_ID));
			Integer priorityId = getIntValue(request.getParameter(PARAM_PRIORITY_ID));
			Integer projectId = getIntValue(request.getParameter(PARAM_PROJECT_ID));
			Integer buildId = getIntValue(request.getParameter(PARAM_BUILD_ID));
			Integer assigneeId = getIntValue(request.getParameter(PARAM_ASSIGNEE_ID));
			
			boolean isAssigned = false;
			boolean isSuccess = false;
			
			if(statusIndex != null){
				switch(statusIndex){
					case STATUS_INDEX_ASSIGNED:
						isAssigned = true;
						break;
					default:
						statusIndex = STATUS_INDEX_NEW;
						break;
				}
				
				if(typeId != null && priorityId != null && projectId != null && buildId != null){
					Issue issue = new Issue();
					
					issue.setCreateDate(DateManager.getCurrentDate());
					Integer createdBy = ((UserBean) new SessionManager().getSessionValue(
							request, SessionManager.NAME_LOGIN_USER)).getUserId();

					issue.setCreatedBy(createdBy);
					issue.setModifyDate(null);
					issue.setModifiedBy(null);
					issue.setSummary(summary);
					issue.setDescription(description);
					issue.setStatus(statusIndex);
					issue.setResolution(null);
					issue.setType(typeId);
					issue.setPriority(priorityId);
					issue.setProject(projectId);
					issue.setBuildFound(buildId);
					issue.setAssignee(null);
					
					if(isAssigned && assigneeId != null){
						// we should save with assignee
						issue.setAssignee(assigneeId);
					}
					
					if(issueDAO.createIssue(issue)){
						isSuccess = true;
					}
				}
			}	
				
			if(isSuccess){
				// All data saved succesfully
				// Show user popup-window with this message
				request.setAttribute("successMessage", "Issue created successfully!");
			} else{
				// Show user popup-window with error
				request.setAttribute("errorMessage", "Failed to save new issue!");
			}
		}
		
		ArrayList<Type> types = typeDAO.getTypes();
		if(types != null){
			request.setAttribute("types", types);
		}
		
		ArrayList<Priority> priorities = priorityDAO.getPriorities();
		if(priorities != null){
			request.setAttribute("priorities", priorities);
		}
		
		ArrayList<Project> projects = projectDAO.getProjects();
		if(projects != null){
			request.setAttribute("projects", projects);
		}
		
		// generate JSON-string for builds
		String builds = getJSONBuilds(projects, buildDAO.getBuilds());
		if(builds != null){
			request.setAttribute("builds", builds);
		}
		
		ArrayList<User> users = userDAO.getUsers();
		if(users != null){
			request.setAttribute("users", users);
		}

		return page;
	}
	
	private Integer getIntValue(String stringValue){
		Integer intValue = null;
		
		if(stringValue != null && !stringValue.equals("")){
			try{
				intValue = Integer.valueOf(stringValue);
			} catch(NumberFormatException e){
				logger.warn("Attempt to use improper value for - " + stringValue);
			}
		}
		
		return intValue;
	}
	
	/**
	 * Generate a JSOn-string with projects' ids and all builds for all projects
	 * @param projects - ArrayList<Project> with all projects
	 * @param builds - ArrayList<Build> - with all builds
	 * @return String in JSON format
	 */
	private String getJSONBuilds(ArrayList<Project> projects, ArrayList<Build> builds){
		String jsonBuilds = null;
		
		if(projects != null && builds != null){
			JSONArray resultArray = new JSONArray();
			for(Project project : projects){
				JSONObject projectJSON = new JSONObject();
				int projectId = project.getProjectId();
				projectJSON.put("id", projectId);
				
				JSONArray buildsArray = new JSONArray();
				for(Build build : builds){
					if(build.getProject() == projectId){
						JSONObject buildJSON = new JSONObject();
						buildJSON.put("id", build.getBuildId());
						buildJSON.put("name", build.getVersion());
						buildsArray.add(buildJSON);
					}
				}
				projectJSON.put("builds", buildsArray);
				resultArray.add(projectJSON);
			}
			
			jsonBuilds = resultArray.toString();
		}
		
		return jsonBuilds;
	}
}