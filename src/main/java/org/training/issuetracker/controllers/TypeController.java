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
import org.training.issuetracker.dao.entities.Type;
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
    private MessageSource messageSource;
	
	@Autowired
	private TypeService typeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createType(Model model, Locale locale, HttpServletRequest request) {
		
		String page = TypePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreateType(model, locale);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, 
					TypePage.getMessage(TypePage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveType(
			Model model,
			Locale locale,
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
					model.addAttribute(TypePage.ATTR_SUCCESS_MESSAGE, TypePage.getMessage(TypePage.MSG_SCS_TYPE_CREATED, messageSource, locale));
				} else{
					// Show user popup-window with error
					model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_FAILED_TO_SAVE, messageSource, locale));
				}
			} else{
				// There is some error in the values
				model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_INVALID_VALUES, messageSource, locale));
			}
			
			// show page
			page = showCreateType(model, locale);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editType(
				Model model,
				Locale locale,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = TypePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			Type editType = typeService.getTypeById(id);
			
			if(editType != null){
				page = showEditType(model, locale, editType);
			} else{
				// There was illegal type id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_NO_TYPE, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
			
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public String saveEditedType(
			Model model,
			HttpServletRequest request,
			Locale locale,
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
						model.addAttribute(TypePage.ATTR_SUCCESS_MESSAGE, TypePage.getMessage(TypePage.MSG_SCS_TYPE_UPDATED, messageSource, locale));
					} else{
						// There was some error - tell this to a user
						model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_FAILED_TO_UPDATE, messageSource, locale));
					}
				} else{
					// There is some error in the values
					model.addAttribute(TypePage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_INVALID_VALUES, messageSource, locale));
				} 
				
				page = showEditType(model, locale, editType);
				
			} else{
				// There was illegal type id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_NO_TYPE, messageSource, locale));
				page = mainController.showMainPage(model, request, locale);
			}
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, TypePage.getMessage(TypePage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
	/**
	 * Shows the page to create a type
	 */
	private String showCreateType(Model model, Locale locale){
		model.addAttribute(TypePage.ATTR_PAGE_TITLE, TypePage.getMessage(TypePage.MSG_TTL_NEW_TYPE, messageSource, locale));
		
		return TypePage.NAME;
	}
	
	/**
	 * Shows the page to edit a type
	 */
	private String showEditType(Model model, Locale locale, Type editType){
		model.addAttribute(TypePage.ATTR_PAGE_TITLE, TypePage.getMessage(TypePage.MSG_TTL_EDIT_TYPE, messageSource, locale));
		
		model.addAttribute(TypePage.ATTR_EDIT_TYPE, editType);
		
		return TypePage.NAME;
	}

}

