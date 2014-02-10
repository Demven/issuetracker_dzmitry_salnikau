package org.training.issuetracker.commands.edit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.view.ViewResolutionsCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.dao.transferObjects.Resolution;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditResolutionCommand implements Command{
	
	private static final String PARAM_RESOLUTION_ID = "resolutionId";
	private static final String PARAM_NAME = "name";
	
	private static final Logger logger = Logger.getLogger(EditResolutionCommand.class);
	
	private DAOFactory mysqlFactory;
	private ResolutionDAO resolutionDAO;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page;
		mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		resolutionDAO = mysqlFactory.getResolutionDAO();
		
		Resolution editResolution = getEditResolution(request.getParameter(PARAM_RESOLUTION_ID));
		if(editResolution != null){
			// We can show page to edit this resolution
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.RESOLUTION_PAGE_PATH);

			request.setAttribute("pageTitle", "Edit resolution");
			request.setAttribute("editResolution", editResolution);
			
			String name = request.getParameter(PARAM_NAME);
			
			if(name != null && !name.equals("")){
				boolean resolutionSuccess = false;
				// It is the request to save changes in a resolution

				editResolution.setName(name);
				resolutionSuccess = resolutionDAO.updateResolution(editResolution);
				
				if(resolutionSuccess){
					// resolution saved succesfully - show user message about success
					request.setAttribute("successMessage", "Resolution updated successfully!");
				} else{
					// There was some error - tell this to a user
					request.setAttribute("errorMessage", "Failed to update the resolution!");
				}
			}
			
		} else{
			// resolutionId is not valid, so we should return user to page with resolutions
			page = new ViewResolutionsCommand().execute(request, response);
		}

		return page;
	}
	
	/**
	 * Returns a Resolution to edit with a specified resolutionId
	 * @param resolutionId - String from request
	 * @return Type
	 */
	private Resolution getEditResolution(String resolutionId){
		Resolution editResolution = null;
		Integer id = null;
		if(resolutionId != null && !resolutionId.equals("")){
			try{
				id = Integer.valueOf(resolutionId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit type with not valid Id", e);
			}
		}
		
		if(id != null){
			Resolution resolution = resolutionDAO.getResolutionById(id);
			if(resolution != null){
				editResolution = resolution;
			}
		}
		
		return editResolution;
	}
}
