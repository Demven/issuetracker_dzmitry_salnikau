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
import org.training.issuetracker.dao.entities.Status;
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
    private MessageSource messageSource;
	
	@Autowired
	private StatusService statusService;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editStatus(
				Model model,
				Locale locale,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = StatusPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Status editStatus = statusService.getStatusById(id);
			
			if(editStatus != null){
				page = showEditStatus(model, locale, editStatus);
			} else{
				// There was illegal status id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, StatusPage.getMessage(StatusPage.MSG_ERR_NO_STATUS, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
			
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, StatusPage.getMessage(StatusPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public String saveEditedStatus(
			Model model,
			HttpServletRequest request,
			Locale locale,
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
						model.addAttribute(StatusPage.ATTR_SUCCESS_MESSAGE, StatusPage.getMessage(StatusPage.MSG_SCS_STATUS_UPDATED, messageSource, locale));
					} else{
						// There was some error - tell this to a user
						model.addAttribute(StatusPage.ATTR_ERROR_MESSAGE, StatusPage.getMessage(StatusPage.MSG_ERR_FAILED_TO_UPDATE, messageSource, locale));
					}
				} else{
					// There is some error in the values
					model.addAttribute(StatusPage.ATTR_ERROR_MESSAGE, StatusPage.getMessage(StatusPage.MSG_ERR_INVALID_VALUES, messageSource, locale));
				} 
				
				page = showEditStatus(model, locale, editStatus);
				
			} else{
				// There was illegal status id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, StatusPage.getMessage(StatusPage.MSG_ERR_NO_STATUS, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, StatusPage.getMessage(StatusPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	/**
	 * Shows the page to edit a status
	 */
	private String showEditStatus(Model model, Locale locale, Status editStatus){
		model.addAttribute(StatusPage.ATTR_PAGE_TITLE, StatusPage.getMessage(StatusPage.MSG_TTL_EDIT_STATUS, messageSource, locale));
		
		model.addAttribute(StatusPage.ATTR_EDIT_STATUS, editStatus);
		
		return StatusPage.NAME;
	}

}
