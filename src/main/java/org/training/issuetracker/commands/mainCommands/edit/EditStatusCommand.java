package org.training.issuetracker.commands.mainCommands.edit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.mainCommands.view.ViewStatusesCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.transferObjects.Status;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditStatusCommand implements Command{
	
	private static final String PARAM_STATUS_ID = "statusId";
	private static final String PARAM_NAME = "name";
	
	private static final Logger logger = Logger.getLogger(EditStatusCommand.class);
	
	private DAOFactory mysqlFactory;
	private StatusDAO statusDAO;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page;
		mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		statusDAO = mysqlFactory.getStatusDAO();
		
		Status editStatus = getEditStatus(request.getParameter(PARAM_STATUS_ID));
		if(editStatus != null){
			// We can show page to edit this status
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.STATUS_PAGE_PATH);

			request.setAttribute("pageTitle", "Edit status");
			request.setAttribute("editStatus", editStatus);
			
			String name = request.getParameter(PARAM_NAME);
			
			if(name != null && !name.equals("")){
				boolean statusSuccess = false;
				// It is the request to save changes in a status

				editStatus.setName(name);
				statusSuccess = statusDAO.updateStatus(editStatus);
				
				if(statusSuccess){
					// status saved succesfully - show user message about success
					request.setAttribute("successMessage", "Status updated successfully!");
				} else{
					// There was some error - tell this to a user
					request.setAttribute("errorMessage", "Failed to update the status!");
				}
			}
			
		} else{
			// statusId is not valid, so we should return user to page with statuses
			page = new ViewStatusesCommand().execute(request, response);
		}

		return page;
	}
	
	/**
	 * Returns a Status to edit with a specified statusId
	 * @param statusId - String from request
	 * @return Status
	 */
	private Status getEditStatus(String statusId){
		Status editStatus = null;
		Integer id = null;
		if(statusId != null && !statusId.equals("")){
			try{
				id = Integer.valueOf(statusId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit type with not valid Id", e);
			}
		}
		
		if(id != null){
			Status status = statusDAO.getStatusById(id);
			if(status != null){
				editStatus = status;
			}
		}
		
		return editStatus;
	}
}
