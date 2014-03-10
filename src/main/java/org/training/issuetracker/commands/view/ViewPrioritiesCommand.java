package org.training.issuetracker.commands.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewPrioritiesCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.PRIORITIES_PAGE_PATH);
		request.setAttribute("pageTitle", "Priorities");

		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		PriorityDAO priorityDAO = hibernateFactory.getPriorityDAO();
		List<Priority> priorities = priorityDAO.getPriorities();
		
		if(priorities != null){
			request.setAttribute("priorities", priorities);
		} else{
			// There was some error, so as we can not show blank page
			// - simply show main page
			page = new NoCommand().execute(request, response);
		}
		
		return page;
	}
}
