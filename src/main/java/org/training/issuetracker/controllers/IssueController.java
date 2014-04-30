package org.training.issuetracker.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.training.issuetracker.dao.entities.*;
import org.training.issuetracker.listeners.ContextListener;
import org.training.issuetracker.managers.DateManager;
import org.training.issuetracker.managers.SessionManager;
import org.training.issuetracker.pages.IssuePage;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.services.*;

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
	private CommentService commentService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private SessionManager sessionManager; 
	
	private String saveDirectory = ContextListener.getServerPath() + "data\\attachments\\issues\\";
	
	@RequestMapping(method=RequestMethod.GET)
	public String createIssue(Model model, Locale locale) {
		
		model.addAttribute(IssuePage.ATTR_PAGE_TITLE, IssuePage.getMessage(IssuePage.MSG_TTL_NEW_ISSUE, messageSource, locale));
		
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
			Locale locale,
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
				model.addAttribute(IssuePage.ATTR_SUCCESS_MESSAGE, IssuePage.getMessage(IssuePage.MSG_SCS_ISSUE_CREATED, messageSource, locale));
			} else{
				// Show user popup-window with error
				model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_ISSUE_FAILED, messageSource, locale));
			}
		} else{
			model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_FILL_SUMMARY, messageSource, locale));
		}
		
		return createIssue(model, locale);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editIssue(
				Model model,
				Locale locale,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = IssuePage.NAME;
			
		Issue editIssue = issueService.getIssueById(id);
		
		if(editIssue != null){
			model.addAttribute(IssuePage.ATTR_PAGE_TITLE, IssuePage.getMessage(IssuePage.MSG_TTL_EDIT_ISSUE, messageSource, locale));
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
			
			List<Comment> comments = commentService.getCommentsForIssue(id);
			if(comments != null){
				model.addAttribute(IssuePage.ATTR_COMMENTS, comments);
			}
			
			List<Attachment> attachments = attachmentService.getAttachmentsForIssue(id);
			if(attachments != null){
				model.addAttribute(IssuePage.ATTR_ATTACHMENTS, attachments);
			}
			
		} else{
			// id is not valid, tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_NO_ISSUE, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST, params=IssuePage.PARAM_SUMMARY)
	public String saveEditedIssue(
			Model model,
			@PathVariable("id") Integer id,
			HttpServletRequest request,
			Locale locale,
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
					model.addAttribute(IssuePage.ATTR_SUCCESS_MESSAGE, IssuePage.getMessage(IssuePage.MSG_SCS_CHANGES_SAVED, messageSource, locale));
				} else{
					// Show user popup-window with error
					model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_CHANGES_FAILED, messageSource, locale));
				}
				
			} else{
				model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_FILL_SUMMARY, messageSource, locale));
			}
			
			page = editIssue(model, locale, id, request);
			
		} else{
			// id is not valid, tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_NO_ISSUE, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST, params=IssuePage.PARAM_COMMENT)
	public String addCommentForIssue(
			Model model,
			@PathVariable("id") Integer issueId,
			HttpServletRequest request,
			Locale locale,
			@RequestParam(IssuePage.PARAM_USER_ID) Integer userId,
			@RequestParam(IssuePage.PARAM_COMMENT) String text) {
		
		String page = IssuePage.NAME;
		
		Issue editIssue = issueService.getIssueById(issueId);
		
		if(editIssue != null){
			
			if(userId != null && !text.equals("") && text != null){
				
				boolean isSuccess = false;
				
				Comment newComment = new Comment();
				Issue commentIssue = new Issue();
				commentIssue.setIssueId(issueId);
				
				User commentUser = new User();
				commentUser.setUserId(userId);
				
				newComment.setIssue(commentIssue);
				newComment.setUser(commentUser);
				newComment.setTime(DateManager.getCurrentTime());
				newComment.setDate(DateManager.getCurrentDate());
				newComment.setText(text);
				
				// save comment
				if(commentService.createComment(newComment)){
					isSuccess = true;
				}
					
				if(isSuccess){
					// All data saved succesfully
					// Show user popup-window with this message
					model.addAttribute(IssuePage.ATTR_SUCCESS_MESSAGE, IssuePage.getMessage(IssuePage.MSG_SCS_COMMENT_SAVED, messageSource, locale));
				} else{
					// Show user popup-window with error
					model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_COMMENT_FAILED, messageSource, locale));
				}
				
			} else{
				model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_ENTER_COMMENT, messageSource, locale));
			}
			
			page = editIssue(model, locale, issueId, request);
			
		} else{
			// id is not valid, tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_NO_ISSUE, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}/upload", method=RequestMethod.POST)
	public String addAttachment(
			Model model,
			HttpServletRequest request,
			Locale locale,
			@PathVariable("id") Integer issueId,
			@RequestParam(IssuePage.PARAM_USER_ID) Integer userId,
			@RequestParam(IssuePage.PARAM_FILE) MultipartFile file){
        
		String page = IssuePage.NAME;

		Issue editIssue = issueService.getIssueById(issueId);
		
		if(editIssue != null){
			boolean success = false;
			
			if (!file.isEmpty()) {
				
				Attachment attachment = new Attachment();
				attachment.setReference(file.getOriginalFilename());
				
				User uploadingUser = new User();
				uploadingUser.setUserId(userId);
				attachment.setUser(uploadingUser);
				
				Issue issue = new Issue();
				issue.setIssueId(issueId);
				attachment.setIssue(issue);
				
				attachment.setTime(DateManager.getCurrentTime());
				attachment.setDate(DateManager.getCurrentDate());
				
				// try to save in database
				success = attachmentService.createAttachment(attachment);
				
				if(success){
					// try to save file in the data directory
		            try {
		            	// Create directory for that issue
		        		File saveIssueDirectory = new File(saveDirectory + issueId);
		        		
		        		if (!saveIssueDirectory.exists()) {
		        			saveIssueDirectory.mkdirs();
		        		}
		        		
		        		File savedFile = new File(saveIssueDirectory.getAbsolutePath() + "\\" + file.getOriginalFilename());
		        		
		                byte[] bytes = file.getBytes();
		                BufferedOutputStream stream = new BufferedOutputStream(
		                		new FileOutputStream(savedFile));
		                stream.write(bytes);
		                stream.close();
		                model.addAttribute(IssuePage.ATTR_SUCCESS_MESSAGE, IssuePage.getMessage(IssuePage.MSG_SCS_ATTACHMENT_SAVED, messageSource, locale));
		            } catch (Exception e) {
		            	success = false;
		            	model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_FILE_FAILED, messageSource, locale));
		            }
		            
				} else{
					model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_FILE_FAILED, messageSource, locale));
				}
				
	        } else {
	        	model.addAttribute(IssuePage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_FILE_EMPTY, messageSource, locale));
	        }
			
			page = editIssue(model, locale, issueId, request);
		
		} else{
			// id is not valid, tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, IssuePage.getMessage(IssuePage.MSG_ERR_NO_ISSUE, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}/download/{fileId}", method=RequestMethod.GET)
	public void downloadAttachment(HttpServletRequest request,
				HttpServletResponse response,
				@PathVariable("id") Integer issueId,
				@PathVariable("fileId") Integer fileId){
		
		Attachment attachment = attachmentService.getAttachmentById(fileId);
		
		File downloadFile = new File(saveDirectory + issueId + "\\" + attachment.getReference());
		
		int bufferSize = 1024 * 1024 * 10;
		response.reset();
		response.setBufferSize(bufferSize);
		response.setContentType("application/download");
		response.setHeader("Content-Length", String.valueOf(downloadFile.length()));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(new FileInputStream(downloadFile), bufferSize);
			output = new BufferedOutputStream(response.getOutputStream(), bufferSize);

			byte[] buffer = new byte[bufferSize];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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
