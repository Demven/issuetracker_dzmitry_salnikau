package org.training.issuetracker.commands.mainCommands.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.mainCommands.NoCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.transferObjects.Type;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewTypesCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.TYPES_PAGE_PATH);
		request.setAttribute("pageTitle", "Types");

		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		TypeDAO typeDAO = mysqlFactory.getTypeDAO();
		ArrayList<Type> types = typeDAO.getTypes();

		if(types != null){
			request.setAttribute("types", types);
		} else{
			// There was some error, so as we can not show blank page
			// - simply show main page
			page = new NoCommand().execute(request, response);
		}
		
		return page;
	}
}
