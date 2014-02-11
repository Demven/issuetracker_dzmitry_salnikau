package org.training.issuetracker.commands.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.logic.SearchLogic;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewUsersCommand implements Command{
	
	private final String PARAM_FILTER = "filter";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.USERS_PAGE_PATH);
		request.setAttribute("pageTitle", "Find user");
		
		String filter = request.getParameter(PARAM_FILTER);
		request.setAttribute("filter", filter);

		ArrayList<User> users = getUsers(filter);
		if(users != null){
			request.setAttribute("users", users);
		}
		
		return page;
	}
	
	/**
	 * Returns the filtered list of User-objects
	 * @param filter - some String, that a user typed in the form
	 * @return ArrayList<User>- filtered list
	 */
	private ArrayList<User> getUsers(String filter){
		ArrayList<User> users = new ArrayList<User>();
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		UserDAO userDAO = mysqlFactory.getUserDAO();
		ArrayList<User> allUsers = userDAO.getUsers();

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