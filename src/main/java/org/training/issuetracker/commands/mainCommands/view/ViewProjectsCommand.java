package org.training.issuetracker.commands.mainCommands.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.beans.ProjectBean;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.Project;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewProjectsCommand implements Command{
	
	private final String PARAM_PAGE = "page";
	
	private final int FIRST_PAGE = 1;
	
	private DAOFactory mysqlFactory;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.PROJECTS_PAGE_PATH);
		request.setAttribute("pageTitle", "Projects");
		
		Integer pageNumber = FIRST_PAGE;
		if(request.getParameter(PARAM_PAGE) != null){
			pageNumber = Integer.valueOf(request.getParameter(PARAM_PAGE));
		}
		
		mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		ProjectDAO projectDAO = mysqlFactory.getProjectDAO();
		ArrayList<Project> allProjects = projectDAO.getProjects();
		int totalPagesNumber = (int) Math.ceil((double)allProjects.size() / (double)ProjectBean.MAX_SHOWN_NUMBER);
		
		ArrayList<ProjectBean> projects = getProjectsPortion(pageNumber, totalPagesNumber, allProjects);
		
		if(projects != null){
			request.setAttribute("projects", projects);
		}
		
		if(totalPagesNumber > 1){
			request.setAttribute("pages", new Integer[totalPagesNumber]);
			request.setAttribute("currentPage", pageNumber);
		}
		
		return page;
	}
	
	/**
	 * Returns the list, containing a portion of project-beans to show on received page
	 * @param pageNumber - page to show portion of projects
	 * @param totalPagesNumber - total number of pages with projects
	 * @param allProjects - ArrayList<Project>
	 * @return ArrayList<ProjectBean> - list, containing a portion of project-beans
	 * to show on received page
	 */
	private ArrayList<ProjectBean> getProjectsPortion(int pageNumber, int totalPagesNumber, ArrayList<Project> allProjects){
		ArrayList<ProjectBean> projectsPortion = new ArrayList<ProjectBean>();

		int from = (pageNumber - 1) * ProjectBean.MAX_SHOWN_NUMBER;
		int to = allProjects.size();
		if(pageNumber < totalPagesNumber){
			to = from + ProjectBean.MAX_SHOWN_NUMBER;
		}
		
		UserDAO userDAO = mysqlFactory.getUserDAO();
		
		for(int i=from; i < to; i++){
			Project project = allProjects.get(i);
			User manager = userDAO.getUserById(project.getManager());
			projectsPortion.add(new ProjectBean(
					project.getProjectId(),
					project.getName(),
					cutDescription(project.getDescription()),
					manager)
			);
		}
		return projectsPortion;
	}
	
	/**
	 * Returns a string with length less or equal to MAX_SHOWN_DESCRIPTION_LENGTH
	 * If a description was shortened - then on the end of the string will be added placeholder-'...'
	 * @param description - String
	 * @return cuttedDescription - String with length less or equal to MAX_SHOWN_DESCRIPTION_LENGTH
	 */
	private String cutDescription(String description){
		String cuttedDescription = null;
		
		if(description.length() > ProjectBean.MAX_SHOWN_DESCRIPTION_LENGTH){
			// need to cut and add placeholder
			cuttedDescription = description.substring(0, ProjectBean.MAX_SHOWN_DESCRIPTION_LENGTH)
					+ ProjectBean.PLACEHOLDER;
			
		} else{
			cuttedDescription = description;
		}
		
		return cuttedDescription;
	}
}
