 package org.training.issuetracker.commands.create;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.managers.ConfigurationManager;

public class CreateProjectCommand implements Command{
	
	private static final String PARAM_NAME = "name";
	private static final String PARAM_DESCRIPTION = "description";
	private static final String PARAM_BUILD_NAME= "buildName";
	private static final String PARAM_MANAGER_ID = "managerId";
	
	private String name;
	private String description;
	private String buildName;
	private Integer managerId;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.PROJECT_PAGE_PATH);
		request.setAttribute("pageTitle", "New project");
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		UserDAO userDAO = hibernateFactory.getUserDAO();
		
		name = request.getParameter(PARAM_NAME);
		if(name != null && !name.equals("")){
			// It is request to save new project
			ProjectDAO projectDAO = hibernateFactory.getProjectDAO();
			BuildDAO buildDAO = hibernateFactory.getBuildDAO();
			
			description = request.getParameter(PARAM_DESCRIPTION);
			managerId = Integer.valueOf(request.getParameter(PARAM_MANAGER_ID));
			buildName = request.getParameter(PARAM_BUILD_NAME);
			
			if(buildName != null && !buildName.equals("") 
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
				
				boolean projectSuccess = projectDAO.createProject(newProject);
				
				if(projectSuccess){
					// now save new build
					Integer projectId = projectDAO.getProjectIdByName(name);
					
					Project buildProject = new Project();
					buildProject.setProjectId(projectId);
					
					Build newBuild = new Build();
					newBuild.setBuildId(0);
					newBuild.setProject(buildProject);
					newBuild.setVersion(buildName);
					
					boolean buildSuccess = buildDAO.createBuild(newBuild);
					if(buildSuccess){
						// All data saved succesfully
						// Show user popup-window with this message
						request.setAttribute("successMessage", "Project created successfully!");
					} else{
						// As build failed to save - delete project
						projectDAO.deleteProject(projectId);
						// Show user popup-window with error
						request.setAttribute("errorMessage", "Failed to save new project!");
					}
				} else{
					// Show user popup-window with error
					request.setAttribute("errorMessage", "Failed to save new project!");
				}
			} 
		}
		
		List<User> managers = userDAO.getUsers();
		if(managers != null){
			request.setAttribute("managers", managers);
		}

		return page;
	}
}