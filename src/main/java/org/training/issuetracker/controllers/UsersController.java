package org.training.issuetracker.controllers;

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
import org.training.issuetracker.dao.entities.User;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.UsersPage;
import org.training.issuetracker.services.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private AccessLogic accessLogic;
	
	private final String DEFAULT_FILTER = null;
	
	@RequestMapping()
	public String showProjects(Model model, Locale locale, HttpServletRequest request) {
		return showUsers(model, request, locale, DEFAULT_FILTER);
	}
	
	@RequestMapping(value = "filter/{filter}", method=RequestMethod.POST)
	public String showUsers(Model model,
			HttpServletRequest request,
			Locale locale,
			@PathVariable("filter") String filter) {

		String page = UsersPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(UsersPage.ATTR_PAGE_TITLE, 
					UsersPage.getMessage(UsersPage.MSG_TTL_FIND_USER, messageSource, locale));
			
			model.addAttribute(UsersPage.ATTR_FILTER, filter);
			
			List<User> users = null;
			if(filter == null){
				// full list
				users = userService.getUsers();
			} else{
				// search users
				users = userService.getUsersWithFilter(filter);
			}		
			
			if(users != null){
				model.addAttribute(UsersPage.ATTR_USERS, users);
			} else{
				model.addAttribute(UsersPage.ATTR_ERROR_MESSAGE, UsersPage.getMessage(UsersPage.MSG_ERR_NO_USERS, messageSource, locale));
			}
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, UsersPage.getMessage(UsersPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
	
}
