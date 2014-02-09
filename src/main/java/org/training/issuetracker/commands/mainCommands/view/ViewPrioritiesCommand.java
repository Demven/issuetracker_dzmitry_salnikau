package org.training.issuetracker.commands.mainCommands.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.transferObjects.Priority;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewPrioritiesCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.STATUSES_PAGE_PATH);
		request.setAttribute("pageTitle", "Priorities");
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		PriorityDAO priorityDAO = mysqlFactory.getPriorityDAO();
		ArrayList<Priority> priorities = priorityDAO.getPriorities();
		
		if(priorities != null){
			request.setAttribute("priorities", priorities);
		}
		
		return page;
	}
}
