package org.training.issuetracker.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.entities.Project;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.ProjectsPage;
import org.training.issuetracker.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectsController {
	
	@Autowired
	private MainController mainController;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private AccessLogic accessLogic;
	
	private final int FIRST_PAGE = 1;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showProjects(Model model, Locale locale, HttpServletRequest request) {
		return showProjects(model, request, locale, FIRST_PAGE);
	}
	
	@RequestMapping(value = "page/{pageNumber}", method=RequestMethod.GET)
	public String showProjects(Model model,
				HttpServletRequest request,
				Locale locale,
				@PathVariable("pageNumber") Integer pageNumber) {

		String page = ProjectsPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(ProjectsPage.ATTR_PAGE_TITLE, ProjectsPage.getMessage(ProjectsPage.MSG_TTL_PROJECTS, messageSource, locale));
			
			List<Project> allProjects = projectService.getProjects();
			int totalPagesNumber = (int) Math.ceil((double)allProjects.size() / (double)Project.MAX_SHOWN_NUMBER);
			
			if(pageNumber > 0 && pageNumber <= totalPagesNumber){
				// all is ok - show page to a user
				
				ArrayList<Project> projects = getProjectsPortion(pageNumber, totalPagesNumber, allProjects);
				
				if(projects != null){
					model.addAttribute(ProjectsPage.ATTR_PROJECTS, projects);
				} else{
					model.addAttribute(ProjectsPage.ATTR_ERROR_MESSAGE, 
							ProjectsPage.getMessage(ProjectsPage.MSG_ERR_NO_PROJECTS, messageSource, locale));
				}
				
				if(totalPagesNumber > 1){
					model.addAttribute(ProjectsPage.ATTR_PAGES, new Integer[totalPagesNumber]);
					model.addAttribute(ProjectsPage.ATTR_CURRENT_PAGE, pageNumber);
				}
			} else{
				// such page soesn't exists
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ProjectsPage.getMessage(ProjectsPage.MSG_ERR_NO_PAGE, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ProjectsPage.getMessage(ProjectsPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	/**
	 * Returns the list, containing a portion of project-beans to show on received page
	 * @param pageNumber - page to show portion of projects
	 * @param totalPagesNumber - total number of pages with projects
	 * @param allProjects - ArrayList<Project>
	 * @return ArrayList<Project> - list, containing a portion of project-beans
	 * to show on received page
	 */
	private ArrayList<Project> getProjectsPortion(int pageNumber, int totalPagesNumber, List<Project> allProjects){
		ArrayList<Project> projectsPortion = new ArrayList<Project>();

		int from = (pageNumber - 1) * Project.MAX_SHOWN_NUMBER;
		int to = allProjects.size();
		if(pageNumber < totalPagesNumber){
			to = from + Project.MAX_SHOWN_NUMBER;
		}
		
		for(int i=from; i < to; i++){
			Project project = allProjects.get(i);
			project.setDescription(cutDescription(project.getDescription()));
			projectsPortion.add(project);
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
		
		if(description.length() > Project.MAX_SHOWN_DESCRIPTION_LENGTH){
			// need to cut and add placeholder
			cuttedDescription = description.substring(0, Project.MAX_SHOWN_DESCRIPTION_LENGTH)
					+ Project.PLACEHOLDER;
		} else{
			cuttedDescription = description;
		}
		
		return cuttedDescription;
	}
}
