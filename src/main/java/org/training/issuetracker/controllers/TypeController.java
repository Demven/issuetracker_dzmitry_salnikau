package org.training.issuetracker.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.TypePage;
import org.training.issuetracker.services.TypeService;

@Controller
@RequestMapping("/type")
public class TypeController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@Autowired
	private TypeService typeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createType(Model model, HttpServletRequest request) {
		
		String page = TypePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreateType(model);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveType(
			Model model,
			HttpServletRequest request,
			@RequestParam(TypePage.PARAM_NAME) String name) {
		
		String page = TypePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			if(name != null && !name.equals("")){
				
				Type newType = new Type();
				newType.setTypeId(0);
				newType.setName(name);
				
				boolean typeSuccess = typeService.createType(newType);
				
				if(typeSuccess){
					// Data saved succesfully
					// Show user popup-window with this message
					model.addAttribute(TypePage.ATTR_SUCCESS_MESSAGE, "Type created successfully!");
				} else{
					// Show user popup-window with error
					model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, "Failed to save new type!");
				}
			} else{
				// There is some error in the values
				model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new type.");
			}
			
			// show page
			page = showCreateType(model);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editType(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = TypePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Type editType = typeService.getTypeById(id);
			
			if(editType != null){
				page = showEditType(model, editType);
			} else{
				// There was illegal type id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a type doesn't exist!");
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
	public String saveEditedType(
			Model model,
			HttpServletRequest request,
			@PathVariable("id") Integer id,
			@RequestParam(TypePage.PARAM_NAME) String name) {
		
		String page = TypePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			Type editType = typeService.getTypeById(id);
			
			if(editType != null){
				// try to save type
				
				if(name != null && !name.equals("")){
					
					boolean typeSuccess = false;

					editType.setName(name);
					typeSuccess = typeService.updateType(editType);
					
					if(typeSuccess){
						// type saved succesfully - show user message about success
						model.addAttribute(TypePage.ATTR_SUCCESS_MESSAGE, "Type updated successfully!");
					} else{
						// There was some error - tell this to a user
						model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, "Failed to update the type!");
					}
				} else{
					// There is some error in the values
					model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new type.");
				} 
				
				page = showEditType(model, editType);
				
			} else{
				// There was illegal type id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a type doesn't exist!");
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
	 * Shows the page to create a type
	 */
	private String showCreateType(Model model){
		model.addAttribute(TypePage.ATTR_PAGE_TITLE, "New type");
		
		return TypePage.NAME;
	}
	
	/**
	 * Shows the page to edit a type
	 */
	private String showEditType(Model model, Type editType){
		model.addAttribute(TypePage.ATTR_PAGE_TITLE, "Edit type");
		
		model.addAttribute(TypePage.ATTR_EDIT_TYPE, editType);
		
		return TypePage.NAME;
	}

}

