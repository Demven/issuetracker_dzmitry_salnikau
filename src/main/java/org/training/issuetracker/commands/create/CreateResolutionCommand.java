package org.training.issuetracker.commands.create;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.managers.ConfigurationManager;

public class CreateResolutionCommand implements Command{
	
	private static final String PARAM_NAME = "name";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.RESOLUTION_PAGE_PATH);
		request.setAttribute("pageTitle", "New resolution");
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HYBERNATE);
		ResolutionDAO resolutionDAO = hibernateFactory.getResolutionDAO();
		
		String name = request.getParameter(PARAM_NAME);
		if(name != null && !name.equals("")){
			// It is request to save new resolution
			Resolution newResolution = new Resolution();
			newResolution.setResolutionId(0);
			newResolution.setName(name);
			boolean resolutionSuccess = resolutionDAO.createResolution(newResolution);
			
			if(resolutionSuccess){
				// Data saved succesfully
				// Show user popup-window with this message
				request.setAttribute("successMessage", "Resolution created successfully!");
			} else{
				// Show user popup-window with error
				request.setAttribute("errorMessage", "Failed to save new resolution!");
			}
		}

		return page;
	}
}