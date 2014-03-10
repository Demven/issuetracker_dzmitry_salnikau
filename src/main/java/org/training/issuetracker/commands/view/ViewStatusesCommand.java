package org.training.issuetracker.commands.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.managers.ConfigurationManager;

public class ViewStatusesCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.STATUSES_PAGE_PATH);
		request.setAttribute("pageTitle", "Statuses");

		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		StatusDAO statusDAO = hibernateFactory.getStatusDAO();
		List<Status> statuses = statusDAO.getStatuses();

		if(statuses != null){
			request.setAttribute("statuses", statuses);
		} else{
			// There was some error, so as we can not show blank page
			// - simply show main page
			page = new NoCommand().execute(request, response);
		}
		
		return page;
	}
}
