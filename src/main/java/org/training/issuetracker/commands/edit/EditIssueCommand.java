package org.training.issuetracker.commands.edit;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.beans.converters.BeanConverter;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.managers.ConfigurationManager;
import org.training.issuetracker.managers.DateManager;
import org.training.issuetracker.managers.SessionManager;

public class EditIssueCommand implements Command{
	
	private static final String PARAM_ISSUE_ID = "issueId";
	private static final String PARAM_SUMMARY = "summary";
	private static final String PARAM_DESCRIPTION = "description";
	private static final String PARAM_STATUS_INDEX = "statusIndex";
	private static final String PARAM_RESOLUTION_ID = "resolutionId";
	private static final String PARAM_TYPE_ID = "typeId";
	private static final String PARAM_PRIORITY_ID = "priorityId";
	private static final String PARAM_PROJECT_ID = "projectId";
	private static final String PARAM_BUILD_ID = "buildId";
	private static final String PARAM_ASSIGNEE_ID = "assigneeId";
	
	private static final int STATUS_INDEX_NEW = 1;
	private static final int STATUS_INDEX_CLOSED = 5;
	
	private static final Logger logger = Logger.getLogger(EditIssueCommand.class);
	
	private IssueDAO issueDAO;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page;
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		issueDAO = hibernateFactory.getIssueDAO();
		
		Issue editIssue = getEditIssue(request.getParameter(PARAM_ISSUE_ID));
		
		if(editIssue != null){
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ISSUE_PAGE_PATH);
			request.setAttribute("pageTitle", "Edit issue");
			
			StatusDAO statusDAO = hibernateFactory.getStatusDAO();
			TypeDAO typeDAO = hibernateFactory.getTypeDAO();
			PriorityDAO priorityDAO = hibernateFactory.getPriorityDAO();
			ProjectDAO projectDAO = hibernateFactory.getProjectDAO();
			ResolutionDAO resolutionDAO = hibernateFactory.getResolutionDAO();
			BuildDAO buildDAO = hibernateFactory.getBuildDAO();
			UserDAO userDAO = hibernateFactory.getUserDAO();
			
			String summary = request.getParameter(PARAM_SUMMARY);
			String description = request.getParameter(PARAM_DESCRIPTION);
			if(summary != null && !summary.equals("")
					&& description != null && !description.equals("")){
				// It is a request to save all changes
				Integer statusIndex = getIntValue(request.getParameter(PARAM_STATUS_INDEX));
				Integer typeId = getIntValue(request.getParameter(PARAM_TYPE_ID));
				Integer resolutionId = getIntValue(request.getParameter(PARAM_RESOLUTION_ID));
				Integer priorityId = getIntValue(request.getParameter(PARAM_PRIORITY_ID));
				Integer projectId = getIntValue(request.getParameter(PARAM_PROJECT_ID));
				Integer buildId = getIntValue(request.getParameter(PARAM_BUILD_ID));
				Integer assigneeId = getIntValue(request.getParameter(PARAM_ASSIGNEE_ID));
				
				boolean isAssigned = false;
				boolean isClosed = false;
				boolean isSuccess = false;
				
				if(statusIndex != null){
					
					// Check the status and set flags, that depend on it
					if(statusIndex == STATUS_INDEX_NEW){
						isAssigned = false;
						isClosed = false;
					} else if(statusIndex == STATUS_INDEX_CLOSED){
						isClosed = true;
						isAssigned = true;
					} else{
						isAssigned = true;
					}
					
					if(typeId != null && priorityId != null && projectId != null && buildId != null){
						
						editIssue.setModifyDate(DateManager.getCurrentDate());
						
						User modifiedBy = new User();
						Integer modifiedById = ((UserBean) new SessionManager().getSessionValue(
								request, SessionManager.NAME_LOGIN_USER)).getUserId();
						modifiedBy.setUserId(modifiedById);
						
						Status status = new Status();
						status.setStatusId(statusIndex);
						
						Type type = new Type();
						type.setTypeId(typeId);
						
						Priority priority = new Priority();
						priority.setPriorityId(priorityId);
						
						Project project = new Project();
						project.setProjectId(projectId);
						
						Build build = new Build();
						build.setBuildId(buildId);
						
						editIssue.setModifiedBy(modifiedBy);
						editIssue.setSummary(summary);
						editIssue.setDescription(description);
						editIssue.setStatus(status);
						editIssue.setResolution(null);
						editIssue.setType(type);
						editIssue.setPriority(priority);
						editIssue.setProject(project);
						editIssue.setBuildFound(build);
						editIssue.setAssignee(null);
						
						if(isAssigned && assigneeId != null){
							// we should save with an assignee
							User assignee = new User();
							assignee.setUserId(assigneeId);
							
							editIssue.setAssignee(assignee);
						}
						
						if(isClosed && resolutionId != null){
							// we should save with a resolution
							Resolution resolution = new Resolution();
							resolution.setResolutionId(resolutionId);
							
							editIssue.setResolution(resolution);
						}

						// update issue
						if(issueDAO.updateIssue(editIssue)){
							isSuccess = true;
						}
					}
				}	
					
				if(isSuccess){
					// All data saved succesfully
					// Show user popup-window with this message
					request.setAttribute("successMessage", "Changes saved successfully!");
				} else{
					// Show user popup-window with error
					request.setAttribute("errorMessage", "Failed to save changes!");
				}
			}
			
			request.setAttribute("editIssue", BeanConverter.convertToIssueBean(editIssue));
			
			List<Status> statuses = statusDAO.getStatuses();
			if(statuses != null){
				request.setAttribute("statuses", statuses);
			}
			
			List<Resolution> resolutions = resolutionDAO.getResolutions();
			if(resolutions != null){
				request.setAttribute("resolutions", resolutions);
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
			
		} else{
			// issueId is not valid, tell this to a user and forward him to the main page
			page = new NoCommand().execute(request, response);
			request.setAttribute("errorMessage", "Such issue doesn't exist!");
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
	 * Returns an Issue to edit with a specified issueId
	 * @param issueId - String from request
	 * @return Issue
	 */
	private Issue getEditIssue(String issueId){
		Issue editIssue = null;
		Integer id = null;
		if(issueId != null){
			try{
				id = Integer.valueOf(issueId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit issue with not a valid Id", e);
			}
		}
		
		if(id != null){
			Issue issue = issueDAO.getIssueById(id);
			if(issue != null){
				editIssue = issue;
			}
		}
		
		return editIssue;
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