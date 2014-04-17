package org.training.issuetracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.managers.DateManager;
import org.training.issuetracker.managers.SessionManager;
import org.training.issuetracker.pages.IssuePage;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.services.BuildService;
import org.training.issuetracker.services.IssueService;
import org.training.issuetracker.services.PriorityService;
import org.training.issuetracker.services.ProjectService;
import org.training.issuetracker.services.ResolutionService;
import org.training.issuetracker.services.StatusService;
import org.training.issuetracker.services.TypeService;
import org.training.issuetracker.services.UserService;

@Controller
@RequestMapping("/issue")
public class IssueController {
	
	@Autowired
	private MainController mainController;

	@Autowired
	private UserService userService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private PriorityService priorityService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BuildService buildService;
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ResolutionService resolutionService;
	
	@Autowired
	private SessionManager sessionManager; 
	
	@RequestMapping(method=RequestMethod.GET)
	public String createIssue(Model model) {
		
		model.addAttribute(IssuePage.ATTR_PAGE_TITLE, "New issue");
		
		List<Type> types = typeService.getTypes();
		if(types != null){
			model.addAttribute(IssuePage.ATTR_TYPES, types);
		}
		
		List<Priority> priorities = priorityService.getPriorities();
		if(priorities != null){
			model.addAttribute(IssuePage.ATTR_PRIORITIES, priorities);
		}
		
		List<Project> projects = projectService.getProjects();
		if(projects != null){
			model.addAttribute(IssuePage.ATTR_PROJECTS, projects);
		}
		
		// generate JSON-string for builds
		String builds = getJSONBuilds(projects, buildService.getBuilds());
		if(builds != null){
			model.addAttribute(IssuePage.ATTR_BUILDS, builds);
		}
		
		List<User> users = userService.getUsers();
		if(users != null){
			model.addAttribute(IssuePage.ATTR_USERS, users);
		}
		
		return IssuePage.NAME;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveIssue(
			Model model,
			HttpServletRequest request,
			@RequestParam(IssuePage.PARAM_SUMMARY) String summary,
			@RequestParam(IssuePage.PARAM_DESCRIPTION) String description,
			@RequestParam(IssuePage.PARAM_STATUS_INDEX) Integer statusIndex,
			@RequestParam(IssuePage.PARAM_TYPE_ID) Integer typeId,
			@RequestParam(IssuePage.PARAM_PRIORITY_ID) Integer priorityId,
			@RequestParam(IssuePage.PARAM_PROJECT_ID) Integer projectId,
			@RequestParam(IssuePage.PARAM_BUILD_ID) Integer buildId,
			@RequestParam(value=IssuePage.PARAM_ASSIGNEE_ID, required = false) Integer assigneeId) {
		
		if(summary != null && !summary.equals("")
				&& description != null && !description.equals("")){
			
			boolean isAssigned = false;
			boolean isSuccess = false;
			
			if(statusIndex != null){
				switch(statusIndex){
					case Status.INDEX_ASSIGNED:
						isAssigned = true;
						break;
					default:
						statusIndex = Status.INDEX_NEW;
						break;
				}
				
				if(typeId != null && priorityId != null && projectId != null && buildId != null){
					// Try to save new issue
					Issue newIssue = new Issue();
					
					User createdBy = new User();
					Integer createdById = ((User) sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER)).getUserId();
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
					
					if(issueService.createIssue(newIssue)){
						isSuccess = true;
					}
				}
			}	
				
			if(isSuccess){
				// All data saved succesfully
				// Show user popup-window with this message
				model.addAttribute(IssuePage.ATTR_SUCCESS_MESSAGE, "Issue created successfully!");
			} else{
				// Show user popup-window with error
				model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, "Failed to save new issue!");
			}
		} else{
			model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, "Please fill summary and description!");
		}
		
		return createIssue(model);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editIssue(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = IssuePage.NAME;
			
		Issue editIssue = issueService.getIssueById(id);
		
		if(editIssue != null){
			model.addAttribute(IssuePage.ATTR_PAGE_TITLE, "Edit issue");
			model.addAttribute(IssuePage.ATTR_EDIT_ISSUE, editIssue);
			
			List<Status> statuses = statusService.getStatuses();
			if(statuses != null){
				model.addAttribute(IssuePage.ATTR_STATUSES, statuses);
			}
			
			List<Resolution> resolutions = resolutionService.getResolutions();
			if(resolutions != null){
				model.addAttribute(IssuePage.ATTR_RESOLUTIONS, resolutions);
			}
			
			List<Type> types = typeService.getTypes();
			if(types != null){
				model.addAttribute(IssuePage.ATTR_TYPES, types);
			}
			
			List<Priority> priorities = priorityService.getPriorities();
			if(priorities != null){
				model.addAttribute(IssuePage.ATTR_PRIORITIES, priorities);
			}
			
			List<Project> projects = projectService.getProjects();
			if(projects != null){
				model.addAttribute(IssuePage.ATTR_PROJECTS, projects);
			}
			
			// generate JSON-string for builds
			String builds = getJSONBuilds(projects, buildService.getBuilds());
			if(builds != null){
				model.addAttribute(IssuePage.ATTR_BUILDS, builds);
			}
			
			List<User> users = userService.getUsers();
			if(users != null){
				model.addAttribute(IssuePage.ATTR_USERS, users);
			}
			
		} else{
			// id is not valid, tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such issue doesn't exist!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public String saveEditedIssue(
			Model model,
			@PathVariable("id") Integer id,
			HttpServletRequest request,
			@RequestParam(IssuePage.PARAM_SUMMARY) String summary,
			@RequestParam(IssuePage.PARAM_DESCRIPTION) String description,
			@RequestParam(IssuePage.PARAM_STATUS_INDEX) Integer statusIndex,
			@RequestParam(value=IssuePage.PARAM_RESOLUTION_ID, required = false) Integer resolutionId,
			@RequestParam(IssuePage.PARAM_TYPE_ID) Integer typeId,
			@RequestParam(IssuePage.PARAM_PRIORITY_ID) Integer priorityId,
			@RequestParam(IssuePage.PARAM_PROJECT_ID) Integer projectId,
			@RequestParam(IssuePage.PARAM_BUILD_ID) Integer buildId,
			@RequestParam(value=IssuePage.PARAM_ASSIGNEE_ID, required = false) Integer assigneeId) {
		
		String page = IssuePage.NAME;
		
		Issue editIssue = issueService.getIssueById(id);
		
		if(editIssue != null){
			
			if(summary != null && !summary.equals("")
					&& description != null && !description.equals("")){
				
				boolean isAssigned = false;
				boolean isClosed = false;
				boolean isSuccess = false;
				
				if(statusIndex != null){
					
					// Check the status and set flags, that depend on it
					if(statusIndex == Status.INDEX_NEW){
						isAssigned = false;
						isClosed = false;
					} else if(statusIndex == Status.INDEX_CLOSED){
						isClosed = true;
						isAssigned = true;
					} else{
						isAssigned = true;
					}
					
					if(typeId != null && priorityId != null && projectId != null && buildId != null){
						
						editIssue.setModifyDate(DateManager.getCurrentDate());
						
						User modifiedBy = new User();
						Integer modifiedById = ((User) new SessionManager().getSessionValue(request, SessionManager.NAME_LOGIN_USER)).getUserId();
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
						if(issueService.updateIssue(editIssue)){
							isSuccess = true;
						}
					}
				}	
					
				if(isSuccess){
					// All data saved succesfully
					// Show user popup-window with this message
					model.addAttribute(IssuePage.ATTR_SUCCESS_MESSAGE, "Changes saved successfully!");
				} else{
					// Show user popup-window with error
					model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, "Failed to save changes!");
				}
				
			} else{
				model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, "Please fill summary and description!");
			}
			
			page = editIssue(model, id, request);
			
		} else{
			// id is not valid, tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such issue doesn't exist!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	/**
	 * Generate a JSOn-string with projects' ids and all builds for all projects
	 * @param projects - List<Project> with all projects
	 * @param builds - List<Build> - with all builds
	 * @return String in JSON format
	 */
	@SuppressWarnings("unchecked")
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
