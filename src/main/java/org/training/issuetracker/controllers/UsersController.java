package org.training.issuetracker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.logic.SearchLogic;
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
	private AccessLogic accessLogic;
	
	private final String DEFAULT_FILTER = null;
	
	@RequestMapping()
	public String showProjects(Model model, HttpServletRequest request) {
		return showUsers(model, request, DEFAULT_FILTER);
	}
	
	@RequestMapping(value = "filter/{filter}", method=RequestMethod.POST)
	public String showUsers(Model model,
			HttpServletRequest request,
			@PathVariable("filter") String filter) {

		String page = UsersPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(UsersPage.ATTR_PAGE_TITLE, "Find user");
			
			model.addAttribute(UsersPage.ATTR_FILTER, filter);
			
			List<User> users = getUsers(filter);
			
			if(users != null){
				model.addAttribute(UsersPage.ATTR_USERS, users);
			} else{
				model.addAttribute(UsersPage.ATTR_ERROR_MESSAGE, "There is no users or some error occured!");
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
	 * Returns the filtered list of User-objects
	 * @param filter - some String, that a user typed in the form
	 * @return List<User>- filtered list
	 */
	private List<User> getUsers(String filter){
		List<User> users = new ArrayList<User>();
		
		List<User> allUsers = userService.getUsers();

		if(filter != null){
			// use filter to fill the list of users
			SearchLogic search = new SearchLogic(filter);
			for(User user: allUsers){
				if(search.isMatchesToFilter(user.getFirstName() + " " + user.getLastName())){
					// This User is matches to the filter - add him to the list
					users.add(user);
				}
			}
		} else{
			users = allUsers;
		}
		
		if(users.size() == 0){
			users = null;
		}
		
		return users;
	}
}
