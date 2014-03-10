package org.training.issuetracker.commands.create;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
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
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		TypeDAO typeDAO = hibernateFactory.getTypeDAO();
		PriorityDAO priorityDAO = hibernateFactory.getPriorityDAO();
		ProjectDAO projectDAO = hibernateFactory.getProjectDAO();
		BuildDAO buildDAO = hibernateFactory.getBuildDAO();
		UserDAO userDAO = hibernateFactory.getUserDAO();
		IssueDAO issueDAO = hibernateFactory.getIssueDAO();
		
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
					// Try to save new issue
					Issue newIssue = new Issue();
					
					User createdBy = new User();
					Integer createdById = ((UserBean) new SessionManager().getSessionValue(
							request, SessionManager.NAME_LOGIN_USER)).getUserId();
					createdBy.setUserId(createdById);
					
					Status status = new Status();
					status.setStatusId(statusIndex);
					
					Type type = new Type();
					type.setTypeId(typeId);
					
					Priority priority = new Priority();
					priority.setPriorityId(priorityId);
					
					Project project = new Project();
					project.setProjectId(projectId);
					
					Build buildFound = new Build();
					buildFound.setBuildId(buildId);
					
					User assignee = null;
					if(isAssigned && assigneeId != null){
						// we should save with assignee
						assignee = new User();
						assignee.setUserId(assigneeId);
					}

					newIssue.setIssueId(0);
					newIssue.setCreateDate(DateManager.getCurrentDate());
					newIssue.setCreatedBy(createdBy);
					newIssue.setModifyDate(null);
					newIssue.setModifiedBy(null);
					newIssue.setSummary(summary);
					newIssue.setDescription(description);
					newIssue.setStatus(status);
					newIssue.setResolution(null);
					newIssue.setType(type);
					newIssue.setPriority(priority);
					newIssue.setProject(project);
					newIssue.setBuildFound(buildFound);
					newIssue.setAssignee(assignee);
					
					if(issueDAO.createIssue(newIssue)){
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
		
		List<Type> types = typeDAO.getTypes();
		if(types != null){
			request.setAttribute("types", types);
		}
		
		List<Priority> priorities = priorityDAO.getPriorities();
		if(priorities != null){
			request.setAttribute("priorities", priorities);
		}
		
		List<Project> projects = projectDAO.getProjects();
		if(projects != null){
			request.setAttribute("projects", projects);
		}
		
		// generate JSON-string for builds
		String builds = getJSONBuilds(projects, buildDAO.getBuilds());
		if(builds != null){
			request.setAttribute("builds", builds);
		}
		
		List<User> users = userDAO.getUsers();
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
	 * @param projects - List<Project> with all projects
	 * @param builds - List<Build> - with all builds
	 * @return String in JSON format
	 */
	private String getJSONBuilds(List<Project> projects, List<Build> builds){
		String jsonBuilds = null;
		
		if(projects != null && builds != null){
			JSONArray resultArray = new JSONArray();
			for(Project project : projects){
				JSONObject projectJSON = new JSONObject();
				int projectId = project.getProjectId();
				projectJSON.put("id", projectId);
				
				JSONArray buildsArray = new JSONArray();
				for(Build build : builds){
					if(build.getProject().getProjectId() == projectId){
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