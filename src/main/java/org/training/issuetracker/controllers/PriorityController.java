package org.training.issuetracker.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Priority;
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
	private PriorityService priorityService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createPriority(Model model, HttpServletRequest request) {
		
		String page = PriorityPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreatePriority(model);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String savePriority(
			Model model,
			HttpServletRequest request,
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
					model.addAttribute(PriorityPage.ATTR_SUCCESS_MESSAGE, "Priority created successfully!");
				} else{
					// Show user popup-window with error
					model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, "Failed to save new priority!");
				}
			} else{
				// There is some error in the values
				model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new priority.");
			}
			
			// show page
			page = showCreatePriority(model);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editPriority(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = PriorityPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Priority editPriority = priorityService.getPriorityById(id);
			
			if(editPriority != null){
				page = showEditPriority(model, editPriority);
			} else{
				// There was illegal priority id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a priority doesn't exist!");
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
	public String saveEditedPriority(
			Model model,
			HttpServletRequest request,
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
						model.addAttribute(PriorityPage.ATTR_SUCCESS_MESSAGE, "Priority updated successfully!");
					} else{
						// There was some error - tell this to a user
						model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, "Failed to update the priority!");
					}
				} else{
					// There is some error in the values
					model.addAttribute(PriorityPage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new priority.");
				} 
				
				page = showEditPriority(model, editPriority);
				
			} else{
				// There was illegal priority id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a priority doesn't exist!");
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
	 * Shows the page to create a priority
	 */
	private String showCreatePriority(Model model){
		model.addAttribute(PriorityPage.ATTR_PAGE_TITLE, "New priority");
		
		return PriorityPage.NAME;
	}
	
	/**
	 * Shows the page to edit a priority
	 */
	private String showEditPriority(Model model, Priority editPriority){
		model.addAttribute(PriorityPage.ATTR_PAGE_TITLE, "Edit priority");
		
		model.addAttribute(PriorityPage.ATTR_EDIT_PRIORITY, editPriority);
		
		return PriorityPage.NAME;
	}

}
