package org.training.issuetracker.commands.edit;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.beans.converters.BeanConverter;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Role;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditUserCommand implements Command{
	
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_FIRST_NAME = "firstName";
	private static final String PARAM_LAST_NAME = "lastName";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_ROLE_ID= "roleId";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_REPEAT_PASSWORD = "repeatPassword";
	
	private static final Logger logger = Logger.getLogger(EditUserCommand.class);
	
	private String firstName;
	private String lastName;
	private String email;
	private String roleId;
	private String password;
	private String repeatPassword;
	
	private UserDAO userDAO;
	private RoleDAO roleDAO;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		userDAO = hibernateFactory.getUserDAO();
		roleDAO = hibernateFactory.getRoleDAO();
		
		User editUser = getEditUser(request.getParameter(PARAM_USER_ID));
		
		String page;
		
		if(editUser != null){
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.PROFILE_PAGE_PATH);
			request.setAttribute("pageTitle", "Edit user");
			
			firstName = request.getParameter(PARAM_FIRST_NAME);
			lastName = request.getParameter(PARAM_LAST_NAME);
			email = request.getParameter(PARAM_EMAIL);
			roleId = request.getParameter(PARAM_ROLE_ID);
			password = request.getParameter(PARAM_PASSWORD);
			repeatPassword = request.getParameter(PARAM_REPEAT_PASSWORD);
				
			if(firstName != null){
				// It is attempt to save changes
				ValidationLogic validation = new ValidationLogic();
				if(validation.isFirstNameValid(firstName)
						&& validation.isLastNameValid(lastName)
						&& validation.isEmailValid(email)
						&& isRoleValid(roleId)){
					
					editUser.setFirstName(firstName);
					editUser.setLastName(lastName);
					editUser.setEmail(email);
					
					Role userRole = new Role();
					userRole.setRoleId(Integer.valueOf(roleId));
					
					editUser.setRole(userRole);
					
					if(!password.equals("") && !repeatPassword.equals("")){
						if(validation.isPasswordValid(password) && password.equals(repeatPassword)){
							// Also save new password
							editUser.setPassword(password);
						}
					}
					
					boolean userSuccess = userDAO.updateUser(editUser);
					
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
			
			request.setAttribute("editUser", BeanConverter.convertToUserBean(editUser));
			
			List<Role> roles = roleDAO.getRoles();
			if(roles != null){
				request.setAttribute("roles", roles);
			}
		} else{
			// userId is not valid, tell this to a user and forward him to the main page
			page = new NoCommand().execute(request, response);
			request.setAttribute("errorMessage", "Such a user doesn't exist!");
		}

		return page;
	}
	
	/**
	 * Checks the role is correct
	 * @param roleId
	 * @return boolean - true if role is valid
	 */
	private boolean isRoleValid(String roleId){
		boolean isRoleValid = false;
		
		if(roleId != null && !roleId.equals("")){
			Integer id = null;
			try{
				id = Integer.valueOf(roleId);
			} catch(NumberFormatException e){}
			
			if(id != null){
				if(roleDAO.getRoleById(id) != null){
					isRoleValid = true;
				}
			}
		}
		
		return isRoleValid;
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
