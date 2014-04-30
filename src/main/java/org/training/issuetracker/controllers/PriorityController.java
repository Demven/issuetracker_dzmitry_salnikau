package org.training.issuetracker.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.entities.Priority;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.PriorityPage;
import org.training.issuetracker.services.PriorityService;

@Controller
@RequestMapping("/priority")
public class PriorityController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private PriorityService priorityService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createPriority(Model model, Locale locale, HttpServletRequest request) {
		
		String page = PriorityPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreatePriority(model, locale);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, 
					PriorityPage.getMessage(PriorityPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String savePriority(
			Model model,
			HttpServletRequest request,
			Locale locale,
			@RequestParam(PriorityPage.PARAM_NAME) String name) {
		
		String page = PriorityPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			if(name != null && !name.equals("")){
				
				Priority newPriority = new Priority();
				newPriority.setPriorityId(0);
				newPriority.setName(name);
				boolean prioritySuccess = priorityService.createPriority(newPriority);
				
				if(prioritySuccess){
					// Data saved succesfully
					// Show user popup-window with this message
					model.addAttribute(PriorityPage.ATTR_SUCCESS_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_SCS_PRIORITY_CREATED, messageSource, locale));
				} else{
					// Show user popup-window with error
					model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_FAILED_TO_SAVE, messageSource, locale));
				}
			} else{
				// There is some error in the values
				model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_INVALID_VALUES, messageSource, locale));
			}
			
			// show page
			page = showCreatePriority(model, locale);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editPriority(
				Model model,
				Locale locale,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = PriorityPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Priority editPriority = priorityService.getPriorityById(id);
			
			if(editPriority != null){
				page = showEditPriority(model, locale, editPriority);
			} else{
				// There was illegal priority id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_NO_PRIORITY, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
			
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public String saveEditedPriority(
			Model model,
			HttpServletRequest request,
			Locale locale,
			@PathVariable("id") Integer id,
			@RequestParam(PriorityPage.PARAM_NAME) String name) {
		
		String page = PriorityPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			Priority editPriority = priorityService.getPriorityById(id);
			
			if(editPriority != null){
				// try to save priority
				
				if(name != null && !name.equals("")){
					
					boolean prioritySuccess = false;

					editPriority.setName(name);
					prioritySuccess = priorityService.updatePriority(editPriority);
					
					if(prioritySuccess){
						// priority saved succesfully - show user message about success
						model.addAttribute(PriorityPage.ATTR_SUCCESS_MESSAGE, 
								PriorityPage.getMessage(PriorityPage.MSG_SCS_PRIORITY_UPDATED, messageSource, locale));
					} else{
						// There was some error - tell this to a user
						model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_FAILED_TO_UPDATE, messageSource, locale));
					}
				} else{
					// There is some error in the values
					model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_INVALID_VALUES, messageSource, locale));
				} 
				
				page = showEditPriority(model, locale, editPriority);
				
			} else{
				// There was illegal priority id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_NO_PRIORITY, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, PriorityPage.getMessage(PriorityPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	/**
	 * Shows the page to create a priority
	 */
	private String showCreatePriority(Model model, Locale locale){
		model.addAttribute(PriorityPage.ATTR_PAGE_TITLE, 
				PriorityPage.getMessage(PriorityPage.MSG_TTL_NEW_ISSUE, messageSource, locale));
		
		return PriorityPage.NAME;
	}
	
	/**
	 * Shows the page to edit a priority
	 */
	private String showEditPriority(Model model, Locale locale, Priority editPriority){
		model.addAttribute(PriorityPage.ATTR_PAGE_TITLE, 
				PriorityPage.getMessage(PriorityPage.MSG_TTL_EDIT_ISSUE, messageSource, locale));
		
		model.addAttribute(PriorityPage.ATTR_EDIT_PRIORITY, editPriority);
		
		return PriorityPage.NAME;
	}

}
