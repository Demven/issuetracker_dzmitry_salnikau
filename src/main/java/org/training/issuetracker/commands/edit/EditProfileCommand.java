package org.training.issuetracker.commands.edit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditProfileCommand implements Command{
	
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_FIRST_NAME = "firstName";
	private static final String PARAM_LAST_NAME = "lastName";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_REPEAT_PASSWORD = "repeatPassword";
	
	private static final Logger logger = Logger.getLogger(EditProfileCommand.class);
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String repeatPassword;
	
	private UserDAO userDAO;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		userDAO = mysqlFactory.getUserDAO();
		
		User editProfile = getEditUser(request.getParameter(PARAM_USER_ID));
		
		String page;
		
		if(editProfile != null){
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.PROFILE_PAGE_PATH);
			request.setAttribute("pageTitle", "Profile");
			
			firstName = request.getParameter(PARAM_FIRST_NAME);
			lastName = request.getParameter(PARAM_LAST_NAME);
			email = request.getParameter(PARAM_EMAIL);
			password = request.getParameter(PARAM_PASSWORD);
			repeatPassword = request.getParameter(PARAM_REPEAT_PASSWORD);
				
			if(firstName != null){
				// It is attempt to save changes
				ValidationLogic validation = new ValidationLogic();
				if(validation.isFirstNameValid(firstName)
						&& validation.isLastNameValid(lastName)
						&& validation.isEmailValid(email)){
					
					editProfile.setFirstName(firstName);
					editProfile.setLastName(lastName);
					editProfile.setEmail(email);
					
					if(!password.equals("") && !repeatPassword.equals("")){
						if(validation.isPasswordValid(password) && password.equals(repeatPassword)){
							// Also save new password
							editProfile.setPassword(password);
						}
					}
					
					boolean userSuccess = userDAO.updateUser(editProfile);
					
					if(userSuccess){
						// All data saved succesfully
						// Show user popup-window with this message
						request.setAttribute("successMessage", "Changes saved successfully!");
					} else{
						// Show user popup-window with error
						request.setAttribute("errorMessage", "Failed to save changes!");
					}
				} else{
					// There is some error in the values
					request.setAttribute("errorMessage", "Invalid values! Failed to save changes.");
				}
			}
			
			request.setAttribute("editProfile", editProfile);

		} else{
			// userId is not valid, tell this to a user and forward him to the main page
			page = new NoCommand().execute(request, response);
			request.setAttribute("errorMessage", "Such a user doesn't exist!");
		}

		return page;
	}
	
	/**
	 * Returns a User to edit with a specified userId
	 * @param userId - String from request
	 * @return User
	 */
	private User getEditUser(String userId){
		User editUser = null;
		Integer id = null;
		if(userId != null){
			try{
				id = Integer.valueOf(userId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit user with not a valid Id", e);
			}
		}
		
		if(id != null){
			User user = userDAO.getUserById(id);
			if(user != null){
				editUser = user;
			}
		}
		
		return editUser;
	}
}
