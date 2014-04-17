package org.training.issuetracker.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.StatusPage;
import org.training.issuetracker.services.StatusService;

@Controller
@RequestMapping("/status")
public class StatusController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@Autowired
	private StatusService statusService;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editStatus(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = StatusPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Status editStatus = statusService.getStatusById(id);
			
			if(editStatus != null){
				page = showEditStatus(model, editStatus);
			} else{
				// There was illegal status id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a status doesn't exist!");
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
	public String saveEditedStatus(
			Model model,
			HttpServletRequest request,
			@PathVariable("id") Integer id,
			@RequestParam(StatusPage.PARAM_NAME) String name) {
		
		String page = StatusPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			Status editStatus = statusService.getStatusById(id);
			
			if(editStatus != null){
				// try to save status
				
				if(name != null && !name.equals("")){
					
					boolean statusSuccess = false;

					editStatus.setName(name);
					statusSuccess = statusService.updateStatus(editStatus);
					
					if(statusSuccess){
						// status saved succesfully - show user message about success
						model.addAttribute(StatusPage.ATTR_SUCCESS_MESSAGE, "Status updated successfully!");
					} else{
						// There was some error - tell this to a user
						model.addAttribute(StatusPage.ATTR_ERROR_MESSAGE, "Failed to update the status!");
					}
				} else{
					// There is some error in the values
					model.addAttribute(StatusPage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to update status.");
				} 
				
				page = showEditStatus(model, editStatus);
				
			} else{
				// There was illegal status id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a status doesn't exist!");
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
	 * Shows the page to edit a status
	 */
	private String showEditStatus(Model model, Status editStatus){
		model.addAttribute(StatusPage.ATTR_PAGE_TITLE, "Edit status");
		
		model.addAttribute(StatusPage.ATTR_EDIT_STATUS, editStatus);
		
		return StatusPage.NAME;
	}

}
