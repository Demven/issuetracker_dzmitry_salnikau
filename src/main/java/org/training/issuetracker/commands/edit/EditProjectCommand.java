package org.training.issuetracker.commands.edit;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.view.ViewProjectsCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditProjectCommand implements Command{
	
	private static final String PARAM_PROJECT_ID = "projectId";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_DESCRIPTION = "description";
	private static final String PARAM_BUILD_ID= "buildId";
	private static final String PARAM_BUILD_NAME= "buildName";
	private static final String PARAM_MANAGER_ID = "managerId";
	
	private static final Logger logger = Logger.getLogger(EditProjectCommand.class);

	private String name;
	private String description;
	private String buildName;
	private Integer managerId;
	
	private DAOFactory hibernateFactory;
	private ProjectDAO projectDAO;
	private BuildDAO buildDAO;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page;
		hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		projectDAO = hibernateFactory.getProjectDAO();
		buildDAO = hibernateFactory.getBuildDAO();
		
		Project editProject = getEditProject(request.getParameter(PARAM_PROJECT_ID));
		if(editProject != null){
			// We can show page to edit this project
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.PROJECT_PAGE_PATH);

			request.setAttribute("pageTitle", "Edit project");
			request.setAttribute("editProject", editProject);
			
			name = request.getParameter(PARAM_NAME);
			description = request.getParameter(PARAM_DESCRIPTION);
			
			if(name != null && !name.equals("") && description != null && request.getParameter(PARAM_MANAGER_ID) != null){
				boolean projectSuccess = false;
				// It is the request to save changes in a project
				managerId = Integer.valueOf(request.getParameter(PARAM_MANAGER_ID));
				buildName = request.getParameter(PARAM_BUILD_NAME);
				
				if(buildName != null && !buildName.equals("")){
					// New build was addded
					// At first - save new build
					
					Project buildProject = new Project();
					buildProject.setProjectId(editProject.getProjectId());
					
					Build newBuild = new Build();
					newBuild.setBuildId(0);
					newBuild.setProject(buildProject);
					newBuild.setVersion(buildName);
					
					boolean buildSuccess = buildDAO.createBuild(newBuild);
					if(buildSuccess){
						// build saved successfully - we can update project
						User manager = new User();
						manager.setUserId(managerId);
						
						editProject.setName(name);
						editProject.setDescription(description);
						editProject.setManager(manager);
						projectSuccess = projectDAO.updateProject(editProject);
					}
				} else if(request.getParameter(PARAM_BUILD_ID) != null){
					// Used old build
					// update this project with a new data
					User manager = new User();
					manager.setUserId(managerId);
					
					editProject.setName(name);
					editProject.setDescription(description);
					editProject.setManager(manager);
					projectSuccess = projectDAO.updateProject(editProject);
				}
				
				if(projectSuccess){
					// project saved succesfully - show user message about success
					request.setAttribute("successMessage", "Project updated successfully!");
				} else{
					// There was some error - tell this to a user
					request.setAttribute("errorMessage", "Failed to update the project!");
				}
			}
			
			UserDAO userDAO = hibernateFactory.getUserDAO();
			List<User> managers = userDAO.getUsers();
			if(managers != null){
				request.setAttribute("managers", managers);
			}
			
			List<Build> builds = buildDAO.getBuildsForProject(editProject.getProjectId());
			if(builds != null){
				request.setAttribute("builds", builds);
			}
		} else{
			// projectId is not valid, so we should return user to page with projects
			page = new ViewProjectsCommand().execute(request, response);
		}

		return page;
	}
	
	/**
	 * Returns a project to edit with a specified projectId
	 * @param projectId - String from request
	 * @return Project
	 */
	private Project getEditProject(String projectId){
		Project editProject = null;
		Integer id = null;
		if(projectId != null){
			try{
				id = Integer.valueOf(projectId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit project with not valid Id", e);
			}
		}
		
		if(id != null){
			Project project = projectDAO.getProjectById(id);
			if(project != null){
				editProject = project;
			}
		}
		
		return editProject;
	}
}
