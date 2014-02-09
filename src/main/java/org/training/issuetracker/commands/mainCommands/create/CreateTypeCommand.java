package org.training.issuetracker.commands.mainCommands.create;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.transferObjects.Type;
import org.training.issuetracker.managers.ConfigurationManager;

public class CreateTypeCommand implements Command{
	
	private static final String PARAM_NAME = "name";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.TYPE_PAGE_PATH);
		request.setAttribute("pageTitle", "New type");
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		TypeDAO typeDAO = mysqlFactory.getTypeDAO();
		
		String name = request.getParameter(PARAM_NAME);
		if(name != null && !name.equals("")){
			// It is request to save new type
			boolean typeSuccess = typeDAO.createType(new Type(0, name));
			
			if(typeSuccess){
				// Data saved succesfully
				// Show user popup-window with this message
				request.setAttribute("successMessage", "Type created successfully!");
			} else{
				// Show user popup-window with error
				request.setAttribute("errorMessage", "Failed to save new type!");
			}
		}

		return page;
	}
}