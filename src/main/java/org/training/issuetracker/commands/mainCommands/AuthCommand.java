package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.logic.SessionLogic;
import org.training.issuetracker.logic.ValidationLogic;

public class AuthCommand implements Command{

	private static final String PARAM_NAME_EMAIL = "email"; 
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws ServletException, IOException {
		
		String email = request.getParameter(PARAM_NAME_EMAIL);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		
		ValidationLogic validation = new ValidationLogic();
		if(validation.isEmailValid(email) && validation.isPasswordValid(password)){
			// if input data is valid - search in database
			DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			UserDAO userDAO = mysqlFactory.getUserDAO();
			
			if(userDAO.checkAuth(email, password)){
				// If such a user is really registered in the system - process authorization
				SessionLogic sessionLogic = new SessionLogic();
				sessionLogic.setSessionValue(request, "login", email);
				
				// Forward to the main page
				new NoCommand().execute(request, response, context);
			}
		}
	}

}
