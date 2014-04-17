package org.training.issuetracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Role;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.ProfilePage;
import org.training.issuetracker.pages.UserPage;
import org.training.issuetracker.services.RoleService;
import org.training.issuetracker.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@Autowired
	private ValidationLogic validation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String createUser(Model model, HttpServletRequest request) {
		
		String page = UserPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			page = showCreateUser(model);
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveUser(
			Model model,
			HttpServletRequest request,
			@RequestParam(UserPage.PARAM_FIRST_NAME) String firstName,
			@RequestParam(UserPage.PARAM_LAST_NAME) String lastName,
			@RequestParam(UserPage.PARAM_EMAIL) String email,
			@RequestParam(UserPage.PARAM_ROLE_ID) Integer roleId,
			@RequestParam(UserPage.PARAM_PASSWORD) String password,
			@RequestParam(UserPage.PARAM_REPEAT_PASSWORD) String repeatPassword) {
		
		String page = UserPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			if(validation.isFirstNameValid(firstName)
					&& validation.isLastNameValid(lastName)
					&& validation.isEmailValid(email)
					&& isRoleValid(roleId)
					&& validation.isPasswordValid(password)
					&& password.equals(repeatPassword)){
				
				// all is ok - save new user
				User user = new User();
				user.setUserId(0);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
	
				Role userRole = new Role();
				userRole.setRoleId(Integer.valueOf(roleId));
	
				user.setRole(userRole);
				user.setPassword(password);
				
				boolean userSuccess = userService.createUser(user);
				
				if(userSuccess){
					// All data saved succesfully
					// Show user popup-window with this message
					model.addAttribute(UserPage.ATTR_SUCCESS_MESSAGE, "User created successfully!");
				} else{
					// Show user popup-window with error
					model.addAttribute(UserPage.ATTR_ERROR_MESSAGE, "Failed to create new user!");
				}
			} else{
				// There is some error in the values
				model.addAttribute(UserPage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to create new user.");
			}
			
			// show page
			page = showCreateUser(model);
		
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String editUser(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = ProfilePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			User editUser = userService.getUserById(id);
			
			if(editUser != null){
				page = showEditUser(model, editUser);
			} else{
				// There was illegal user id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a user doesn't exist!");
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
	public String saveEditedUser(
			Model model,
			HttpServletRequest request,
			@PathVariable("id") Integer id,
			@RequestParam(UserPage.PARAM_FIRST_NAME) String firstName,
			@RequestParam(UserPage.PARAM_LAST_NAME) String lastName,
			@RequestParam(UserPage.PARAM_EMAIL) String email,
			@RequestParam(UserPage.PARAM_ROLE_ID) Integer roleId,
			@RequestParam(UserPage.PARAM_PASSWORD) String password,
			@RequestParam(UserPage.PARAM_REPEAT_PASSWORD) String repeatPassword) {
		
		String page = ProfilePage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			// we have access
			
			User editUser = userService.getUserById(id);
			
			if(editUser != null){
				// try to save user
				
				if(validation.isFirstNameValid(firstName)
						&& validation.isLastNameValid(lastName)
						&& validation.isEmailValid(email)
						&& isRoleValid(roleId)){
					
					editUser.setFirstName(firstName);
					editUser.setLastName(lastName);
					editUser.setEmail(email);
					
					Role userRole = new Role();
					userRole.setRoleId(roleId);
					
					editUser.setRole(userRole);
					
					if(validation.isPasswordValid(password) && password.equals(repeatPassword)){
						// Also save new password
						editUser.setPassword(password);
					}
					
					boolean userSuccess = userService.updateUser(editUser);
					
					if(userSuccess){
						// All data saved succesfully
						// Show user popup-window with this message
						model.addAttribute(UserPage.ATTR_SUCCESS_MESSAGE, "Changes saved successfully!");
					} else{
						// Show user popup-window with error
						model.addAttribute(UserPage.ATTR_ERROR_MESSAGE, "Failed to save changes!");
					}
				} else{
					// There is some error in the values
					request.setAttribute("errorMessage", "Invalid values! Failed to save changes.");
				}
				
				page = showEditUser(model, editUser);
			} else{
				// There was illegal user id
				// tell this to a user and forward him to the main page
				model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Such a user doesn't exist!");
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
	 * Shows the page to create a user
	 */
	private String showCreateUser(Model model){
		model.addAttribute(UserPage.ATTR_PAGE_TITLE, "New user");
		
		List<Role> roles = roleService.getRoles();
		if(roles != null){
			model.addAttribute(UserPage.ATTR_ROLES, roles);
		}
		
		return UserPage.NAME;
	}
	
	/**
	 * Shows the page to edit a user
	 */
	private String showEditUser(Model model, User editUser){
		model.addAttribute(UserPage.ATTR_PAGE_TITLE, "Edit user");
		
		model.addAttribute(UserPage.ATTR_EDIT_USER, editUser);
		
		List<Role> roles = roleService.getRoles();
		if(roles != null){
			model.addAttribute(UserPage.ATTR_ROLES, roles);
		}
		
		return ProfilePage.NAME;
	}
	
	/**
	 * Checks the role is correct
	 * @param roleId
	 * @return boolean - true if role is valid
	 */
	private boolean isRoleValid(Integer roleId){
		boolean isRoleValid = false;
			
		if(roleId != null){
			if(roleService.getRoleById(roleId) != null){
				isRoleValid = true;
			}
		}
		
		return isRoleValid;
	}

}
