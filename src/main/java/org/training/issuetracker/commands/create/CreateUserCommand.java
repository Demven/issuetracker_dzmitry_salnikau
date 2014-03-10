package org.training.issuetracker.commands.create;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Role;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.managers.ConfigurationManager;

public class CreateUserCommand implements Command{
	
	private static final String PARAM_FIRST_NAME = "firstName";
	private static final String PARAM_LAST_NAME = "lastName";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_ROLE_ID= "roleId";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_REPEAT_PASSWORD = "repeatPassword";
	
	private String firstName;
	private String lastName;
	private String email;
	private String roleId;
	private String password;
	private String repeatPassword;
	
	private RoleDAO roleDAO;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.USER_PAGE_PATH);
		request.setAttribute("pageTitle", "New user");
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		
		UserDAO userDAO = hibernateFactory.getUserDAO();
		roleDAO = hibernateFactory.getRoleDAO();
		
		firstName = request.getParameter(PARAM_FIRST_NAME);
		if(firstName != null && !firstName.equals("")){
			// It is the request to save a new user
			
			lastName = request.getParameter(PARAM_LAST_NAME);
			email = request.getParameter(PARAM_EMAIL);
			roleId = request.getParameter(PARAM_ROLE_ID);
			password = request.getParameter(PARAM_PASSWORD);
			repeatPassword = request.getParameter(PARAM_REPEAT_PASSWORD);
			
			ValidationLogic validation = new ValidationLogic();
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
				
				boolean userSuccess = userDAO.createUser(user);
				
				if(userSuccess){
					// All data saved succesfully
					// Show user popup-window with this message
					request.setAttribute("successMessage", "User created successfully!");
				} else{
					// Show user popup-window with error
					request.setAttribute("errorMessage", "Failed to create new user!");
				}
			} else{
				// There is some error in the values
				request.setAttribute("errorMessage", "Invalid values! Failed to create new user.");
			}
		}
		
		List<Role> roles = roleDAO.getRoles();
		if(roles != null){
			request.setAttribute("roles", roles);
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
}