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
import org.training.issuetracker.dao.entities.Resolution;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.ResolutionPage;
import org.training.issuetracker.services.ResolutionService;

@Controller
@RequestMapping("/resolution")
public class ResolutionController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private ResolutionService resolutionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createResolution(Model model, Locale locale, HttpServletRequest request) {
		
		String page = ResolutionPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreateResolution(model, locale);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, 
					ResolutionPage.getMessage(ResolutionPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveResolution(
			Model model,
			HttpServletRequest request,
			Locale locale,
			@RequestParam(ResolutionPage.PARAM_NAME) String name) {
		
		String page = ResolutionPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			if(name != null && !name.equals("")){
				
				Resolution newResolution = new Resolution();
				newResolution.setResolutionId(0);
				newResolution.setName(name);
				boolean resolutionSuccess = resolutionService.createResolution(newResolution);
				
				if(resolutionSuccess){
					// Data saved succesfully
					// Show user popup-window with this message
					model.addAttribute(ResolutionPage.ATTR_SUCCESS_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_SCS_RESOLUTION_CREATED, messageSource, locale));
				} else{
					// Show user popup-window with error
					model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_FAILED_TO_SAVE, messageSource, locale));
				}
			} else{
				// There is some error in the values
				model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_INVALID_VALUES, messageSource, locale));
			}
			
			// show page
			page = showCreateResolution(model, locale);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editResolution(
				Model model,
				Locale locale,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = ResolutionPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Resolution editResolution = resolutionService.getResolutionById(id);
			
			if(editResolution != null){
				page = showEditResolution(model, locale, editResolution);
			} else{
				// There was illegal resolution id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_NO_RESOLUTION, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
			
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public String saveEditedResolution(
			Model model,
			HttpServletRequest request,
			Locale locale,
			@PathVariable("id") Integer id,
			@RequestParam(ResolutionPage.PARAM_NAME) String name) {
		
		String page = ResolutionPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			
			Resolution editResolution = resolutionService.getResolutionById(id);
			
			if(editResolution != null){
				// try to save resolution
				
				if(name != null && !name.equals("")){
					
					boolean resolutionSuccess = false;
					
					editResolution.setName(name);
					resolutionSuccess = resolutionService.updateResolution(editResolution);
					
					if(resolutionSuccess){
						// resolution saved succesfully - show user message about success
						model.addAttribute(ResolutionPage.ATTR_SUCCESS_MESSAGE,ResolutionPage.getMessage(ResolutionPage.MSG_SCS_RESOLUTION_UPDATED, messageSource, locale));
					} else{
						// There was some error - tell this to a user
						model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_FAILED_TO_UPDATE, messageSource, locale));
					}
				} else{
					// There is some error in the values
					model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_INVALID_VALUES, messageSource, locale));
				} 
				
				page = showEditResolution(model, locale, editResolution);
				
			} else{
				// There was illegal resolution id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_NO_RESOLUTION, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ResolutionPage.getMessage(ResolutionPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	/**
	 * Shows the page to create a resolution
	 */
	private String showCreateResolution(Model model, Locale locale){
		model.addAttribute(ResolutionPage.ATTR_PAGE_TITLE, ResolutionPage.getMessage(ResolutionPage.MSG_TTL_NEW_RESOLUTION, messageSource, locale));
		
		return ResolutionPage.NAME;
	}
	
	/**
	 * Shows the page to edit a resolution
	 */
	private String showEditResolution(Model model, Locale locale, Resolution editResolution){
		model.addAttribute(ResolutionPage.ATTR_PAGE_TITLE, ResolutionPage.getMessage(ResolutionPage.MSG_TTL_EDIT_RESOLUTION, messageSource, locale));
		
		model.addAttribute(ResolutionPage.ATTR_EDIT_RESOLUTION, editResolution);
		
		return ResolutionPage.NAME;
	}

}
