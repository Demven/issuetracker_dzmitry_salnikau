package org.training.issuetracker.commands.create;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.transferObjects.Priority;
import org.training.issuetracker.managers.ConfigurationManager;

public class CreatePriorityCommand implements Command{
	
	private static final String PARAM_NAME = "name";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.PRIORITY_PAGE_PATH);
		request.setAttribute("pageTitle", "New priority");
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		PriorityDAO priorityDAO = mysqlFactory.getPriorityDAO();
		
		String name = request.getParameter(PARAM_NAME);
		if(name != null && !name.equals("")){
			// It is request to save new priority
			boolean prioritySuccess = priorityDAO.createPriority(new Priority(0, name));
			
			if(prioritySuccess){
				// Data saved succesfully
				// Show user popup-window with this message
				request.setAttribute("successMessage", "Priority created successfully!");
			} else{
				// Show user popup-window with error
				request.setAttribute("errorMessage", "Failed to save new priority!");
			}
		}

		return page;
	}
}