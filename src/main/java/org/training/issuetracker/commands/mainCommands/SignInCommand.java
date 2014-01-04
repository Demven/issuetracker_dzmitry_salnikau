package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.User;


public class SignInCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		out.println("This is the list of all registered users:");
		
		DAOFactory xmlFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
		UserDAO userDAO = xmlFactory.getUserDAO(context.getRealPath("/"));
		ArrayList<User> users = userDAO.getUsers();
		
		if(users != null){
			for(User user:users){
				out.println("<br/>ID " + user.getUserId() + ". Name " + user.getFirstName());
				out.println("<br/>lastName " + user.getLastName() + ". Email " + user.getEmail());
				out.println("<br/>password " + user.getPassword() + ". Role " + user.getRole());
			}
		} else{
			out.println("Nobody");
		}
		
		out.close();
	}

}
