package org.training.issuetracker.commands.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.beans.converters.BeanConverter;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.managers.CookieManager;
import org.training.issuetracker.managers.SessionManager;

public class AuthCommand implements Command{

	private static final String PARAM_NAME_EMAIL = "email"; 
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter(PARAM_NAME_EMAIL);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		
		String page = "";
		
		ValidationLogic validation = new ValidationLogic();
		if(validation.isEmailValid(email) && validation.isPasswordValid(password)){
			// if input data is valid - search in database
			DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			UserDAO userDAO = mysqlFactory.getUserDAO();
			
			if(userDAO.checkAuth(email, password)){
				// If such a user is really registered in the system - process authorization
				UserBean loginUser = BeanConverter.convertToUserBean(userDAO.getUserByEmail(email));
        		
        		// Set this object of the authorized user into the session
				SessionManager sessionManager = new SessionManager();
				sessionManager.setSessionValue(request,
						SessionManager.NAME_LOGIN_USER, loginUser);
				
				// Set cookie
				CookieManager cookieManager = new CookieManager();
				cookieManager.setCookieValue(response, CookieManager.NAME_LOGIN, email);
				
				// Forward to the main page
				page = new NoCommand().execute(request, response);
			}
		}
		return page;
	}

}
