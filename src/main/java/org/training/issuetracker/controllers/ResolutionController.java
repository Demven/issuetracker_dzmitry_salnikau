package org.training.issuetracker.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
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
	private ResolutionService resolutionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createResolution(Model model, HttpServletRequest request) {
		
		String page = ResolutionPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreateResolution(model);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveResolution(
			Model model,
			HttpServletRequest request,
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
					model.addAttribute(ResolutionPage.ATTR_SUCCESS_MESSAGE, "Resolution created successfully!");
				} else{
					// Show user popup-window with error
					model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, "Failed to save new resolution!");
				}
			} else{
				// There is some error in the values
				model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new resolution.");
			}
			
			// show page
			page = showCreateResolution(model);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editResolution(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = ResolutionPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Resolution editResolution = resolutionService.getResolutionById(id);
			
			if(editResolution != null){
				page = showEditResolution(model, editResolution);
			} else{
				// There was illegal resolution id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a resolution doesn't exist!");
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
	public String saveEditedResolution(
			Model model,
			HttpServletRequest request,
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
						model.addAttribute(ResolutionPage.ATTR_SUCCESS_MESSAGE, "Resolution updated successfully!");
					} else{
						// There was some error - tell this to a user
						model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, "Failed to update the resolution!");
					}
				} else{
					// There is some error in the values
					model.addAttribute(ResolutionPage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new resolution.");
				} 
				
				page = showEditResolution(model, editResolution);
				
			} else{
				// There was illegal project id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a project doesn't exist!");
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
	 * Shows the page to create a resolution
	 */
	private String showCreateResolution(Model model){
		model.addAttribute(ResolutionPage.ATTR_PAGE_TITLE, "New resolution");
		
		return ResolutionPage.NAME;
	}
	
	/**
	 * Shows the page to edit a resolution
	 */
	private String showEditResolution(Model model, Resolution editResolution){
		model.addAttribute(ResolutionPage.ATTR_PAGE_TITLE, "Edit resolution");
		
		model.addAttribute(ResolutionPage.ATTR_EDIT_RESOLUTION, editResolution);
		
		return ResolutionPage.NAME;
	}

}
