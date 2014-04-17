package org.training.issuetracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.ProjectPage;
import org.training.issuetracker.services.BuildService;
import org.training.issuetracker.services.ProjectService;
import org.training.issuetracker.services.UserService;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BuildService buildService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createProject(Model model, HttpServletRequest request) {
		
		String page = ProjectPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreateProject(model);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveProject(
			Model model,
			HttpServletRequest request,
			@RequestParam(ProjectPage.PARAM_NAME) String name,
			@RequestParam(ProjectPage.PARAM_DESCRIPTION) String description,
			@RequestParam(ProjectPage.PARAM_BUILD_NAME) String buildName,
			@RequestParam(ProjectPage.PARAM_MANAGER_ID) Integer managerId) {
		
		String page = ProjectPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			if(name != null && !name.equals("")
					&& buildName != null && !buildName.equals("") 
					&& description != null && !description.equals("") 
					&& managerId != null){
				
				// at first - save new project
				Project newProject = new Project();
				User manager = new User();
				manager.setUserId(managerId);
				
				newProject.setProjectId(0);
				newProject.setName(name);
				newProject.setDescription(description);
				newProject.setManager(manager);
				
				boolean projectSuccess = projectService.createProject(newProject);
				
				if(projectSuccess){
					// now save new build
					Integer projectId = projectService.getProjectIdByName(name);
					
					Project buildProject = new Project();
					buildProject.setProjectId(projectId);
					
					Build newBuild = new Build();
					newBuild.setBuildId(0);
					newBuild.setProject(buildProject);
					newBuild.setVersion(buildName);
					
					boolean buildSuccess = buildService.createBuild(newBuild);
					if(buildSuccess){
						// All data saved succesfully
						// Show user popup-window with this message
						model.addAttribute(ProjectPage.ATTR_SUCCESS_MESSAGE, "Project created successfully!");
					} else{
						// As build failed to save - delete project
						projectService.deleteProject(projectId);
						// Show user popup-window with error
						model.addAttribute(ProjectPage.ATTR_ERROR_MESSAGE, "Failed to save new project!");
					}
				} else{
					// Show user popup-window with error
					model.addAttribute(ProjectPage.ATTR_ERROR_MESSAGE, "Failed to save new project!");
				}
			}
			
			// show page
			page = showCreateProject(model);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editProject(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = ProjectPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Project editProject = projectService.getProjectById(id);
			
			if(editProject != null){
				page = showEditProject(model, editProject);
			} else{
				// There was illegal project id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a project doesn't exist!");
				page = mainController.showMainPage(model, request);
			}
			
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public String saveEditedProject(
			Model model,
			HttpServletRequest request,
			@PathVariable("id") Integer id,
			@RequestParam(ProjectPage.PARAM_NAME) String name,
			@RequestParam(ProjectPage.PARAM_DESCRIPTION) String description,
			@RequestParam(value=ProjectPage.PARAM_BUILD_ID, required=false) Integer buildId,
			@RequestParam(value=ProjectPage.PARAM_BUILD_NAME, required=false) String buildName,
			@RequestParam(ProjectPage.PARAM_MANAGER_ID) Integer managerId) {
		
		String page = ProjectPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			
			Project editProject = projectService.getProjectById(id);
			
			if(editProject != null){
				// try to save project
				
				if(name != null && !name.equals("")
							&& description != null && !description.equals("")
							&& managerId != null){
					
					boolean projectSuccess = false;
					
					if(buildName != null && !buildName.equals("")){
						// New build was addded
						// At first - save new build
						
						Project buildProject = new Project();
						buildProject.setProjectId(editProject.getProjectId());
						
						Build newBuild = new Build();
						newBuild.setBuildId(0);
						newBuild.setProject(buildProject);
						newBuild.setVersion(buildName);
						
						boolean buildSuccess = buildService.createBuild(newBuild);
						if(buildSuccess){
							// build saved successfully - we can update project
							User manager = new User();
							manager.setUserId(managerId);
							
							editProject.setName(name);
							editProject.setDescription(description);
							editProject.setManager(manager);
							projectSuccess = projectService.updateProject(editProject);
						}
					} else if(buildId != null){
						// Use old build
						// update this project with a new data
						User manager = new User();
						manager.setUserId(managerId);
						
						editProject.setName(name);
						editProject.setDescription(description);
						editProject.setManager(manager);
						projectSuccess = projectService.updateProject(editProject);
					}
					
					if(projectSuccess){
						// project saved succesfully - show user message about success
						model.addAttribute(ProjectPage.ATTR_SUCCESS_MESSAGE, "Project updated successfully!");
					} else{
						// There was some error - tell this to a user
						model.addAttribute(ProjectPage.ATTR_ERROR_MESSAGE, "Failed to update the project!");
					}
				}
				
				page = showEditProject(model, editProject);
				
			} else{
				// There was illegal project id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a project doesn't exist!");
				page = mainController.showMainPage(model, request);
			}
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	/**
	 * Shows the page to create a project
	 */
	private String showCreateProject(Model model){
		model.addAttribute(ProjectPage.ATTR_PAGE_TITLE, "New project");
		
		List<User> managers = userService.getUsers();
		if(managers != null){
			model.addAttribute(ProjectPage.ATTR_MANAGERS, managers);
		}
		
		return ProjectPage.NAME;
	}
	
	/**
	 * Shows the page to edit a project
	 */
	private String showEditProject(Model model, Project editProject){
		model.addAttribute(ProjectPage.ATTR_PAGE_TITLE, "Edit project");
		
		model.addAttribute(ProjectPage.ATTR_EDIT_PROJECT, editProject);
		
		List<User> managers = userService.getUsers();
		if(managers != null){
			model.addAttribute(ProjectPage.ATTR_MANAGERS, managers);
		}
		
		List<Build> builds = buildService.getBuildsForProject(editProject.getProjectId());
		if(builds != null){
			model.addAttribute(ProjectPage.ATTR_BUILDS, builds);
		}
		
		return ProjectPage.NAME;
	}

}
