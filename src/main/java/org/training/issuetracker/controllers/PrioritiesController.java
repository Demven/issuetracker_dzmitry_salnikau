package org.training.issuetracker.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.entities.Priority;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.PrioritiesPage;
import org.training.issuetracker.services.PriorityService;

@Controller
@RequestMapping("/priorities")
public class PrioritiesController {
	
	@Autowired
	private MainController mainController;
	
	@Autowired
	private PriorityService priorityService;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showPriorities(Model model, Locale locale, HttpServletRequest request) {

		String page = PrioritiesPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(PrioritiesPage.ATTR_PAGE_TITLE, 
					PrioritiesPage.getMessage(PrioritiesPage.MSG_TTL_PRIORITIES, messageSource, locale));
			
			List<Priority> priorities = priorityService.getPriorities();
			
			if(priorities != null){
				model.addAttribute(PrioritiesPage.ATTR_PRIORITIES, priorities);
			} else{
				model.addAttribute(PrioritiesPage.ATTR_ERROR_MESSAGE, 
						PrioritiesPage.getMessage(PrioritiesPage.MSG_ERR_NO_PRIORITIES, messageSource, locale));
			}
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, PrioritiesPage.getMessage(PrioritiesPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
}
