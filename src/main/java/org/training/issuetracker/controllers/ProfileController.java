package org.training.issuetracker.controllers;

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
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.managers.SessionManager;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.ProfilePage;
import org.training.issuetracker.services.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionManager sessionManager; 
	
	@Autowired
	private MainController mainController;
	
	@Autowired
	private ValidationLogic validation;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String showProfile(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request) {

		String page = ProfilePage.NAME;
		
		if(isHaveAccess(request, id)){
			// we have access to this page
			
			User editProfile = userService.getUserById(id);
			
			if(editProfile != null){
				model.addAttribute(ProfilePage.ATTR_PAGE_TITLE, "Profile");
				model.addAttribute(ProfilePage.ATTR_EDIT_PROFILE, editProfile);
			} else{
				// id is not valid, tell this to a user and forward him to the main page
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
	public String saveProfile(
				Model model,
				@PathVariable("id") Integer id,
				HttpServletRequest request,
				@RequestParam(ProfilePage.PARAM_FIRST_NAME) String firstName,
				@RequestParam(ProfilePage.PARAM_LAST_NAME) String lastName,
				@RequestParam(ProfilePage.PARAM_EMAIL) String email,
				@RequestParam(ProfilePage.PARAM_PASSWORD) String password,
				@RequestParam(ProfilePage.PARAM_REPEAT_PASSWORD) String repeatPassword) {
		
		String page = ProfilePage.NAME;
		
		if(isHaveAccess(request, id)){
			// we have access to this page
		
			User editProfile = userService.getUserById(id);
			
			if(editProfile != null){
				model.addAttribute(ProfilePage.ATTR_PAGE_TITLE, "Profile");
				
				if(validation.isFirstNameValid(firstName)
						&& validation.isLastNameValid(lastName)
						&& validation.isEmailValid(email)){
					
					editProfile.setFirstName(firstName);
					editProfile.setLastName(lastName);
					editProfile.setEmail(email);
					
					if(validation.isPasswordValid(password) && password.equals(repeatPassword)){
						// Also save new password
						editProfile.setPassword(password);
					}
					
					boolean userSuccess = userService.updateUser(editProfile);
					
					if(userSuccess){
						// All data saved succesfully
						// Show user popup-window with this message
						model.addAttribute(ProfilePage.ATTR_SUCCESS_MESSAGE, "Changes saved successfully!");
					} else{
						// Show user popup-window with error
						model.addAttribute(ProfilePage.ATTR_ERROR_MESSAGE, "Failed to save changes!");
					}
				} else{
					// There is some error in the values
					model.addAttribute(ProfilePage.ATTR_ERROR_MESSAGE, "Invalid values! Failed to save changes.");
				}
				
				model.addAttribute(ProfilePage.ATTR_EDIT_PROFILE, editProfile);
			} else{
				// id is not valid, tell this to a user and forward him to the main page
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
	 * Checks whether this user have access to a profile-page
	 * @param request - HttpServletRequest
	 * @param id - id of the requested profile
	 * @return boolean isHaveAccess
	 */
	private boolean isHaveAccess(HttpServletRequest request, Integer id){
		boolean isHaveAccess = false;
		User loginUser = (User) sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER);
		
		if(loginUser != null){
			if(Role.ROLE_ADMIN.equals(loginUser.getRole().getName())){
				// If ADMINISTRATOR - we have access to all profiles
				isHaveAccess = true;
			} else if(Role.ROLE_USER.equals(loginUser.getRole().getName())
						&& id.equals(loginUser.getUserId())){
				// User have access only to his profile
				isHaveAccess = true;
			}
		}
		
		return isHaveAccess;
	}
}

