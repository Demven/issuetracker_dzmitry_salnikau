package org.training.issuetracker.commands.mainCommands.create;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.Build;
import org.training.issuetracker.dao.transferObjects.Project;
import org.training.issuetracker.dao.transferObjects.User;
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
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		UserDAO userDAO = mysqlFactory.getUserDAO();
		
		name = request.getParameter(PARAM_NAME);
		if(name != null && !name.equals("")){
			// It is request to save new project
			ProjectDAO projectDAO = mysqlFactory.getProjectDAO();
			BuildDAO buildDAO = mysqlFactory.getBuildDAO();
			
			description = request.getParameter(PARAM_DESCRIPTION);
			managerId = Integer.valueOf(request.getParameter(PARAM_MANAGER_ID));
			buildName = request.getParameter(PARAM_BUILD_NAME);
			
			if(buildName != null && !buildName.equals("") 
					&& description != null && !description.equals("") 
					&& managerId != null){
				// at first - save new project
				boolean projectSuccess = projectDAO.createProject(new Project(0, name, description, managerId));
				
				if(projectSuccess){
					// now save new build
					Integer projectId = projectDAO.getProjectIdByName(name);
					boolean buildSuccess = buildDAO.createBuild(new Build(0, projectId, buildName));
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
		
		ArrayList<User> managers = userDAO.getUsers();
		if(managers != null){
			request.setAttribute("managers", managers);
		}

		return page;
	}
}