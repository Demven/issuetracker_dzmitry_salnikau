package org.training.issuetracker.commands.mainCommands.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.transferObjects.Status;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewStatusesCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.STATUSES_PAGE_PATH);
		request.setAttribute("pageTitle", "Statuses");
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		StatusDAO statusDAO = mysqlFactory.getStatusDAO();
		ArrayList<Status> statuses = statusDAO.getStatuses();
		
		if(statuses != null){
			request.setAttribute("statuses", statuses);
		}
		
		return page;
	}
}
