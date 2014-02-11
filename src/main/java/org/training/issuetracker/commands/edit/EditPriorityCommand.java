package org.training.issuetracker.commands.edit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.view.ViewPrioritiesCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.transferObjects.Priority;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditPriorityCommand implements Command{
	
	private static final String PARAM_PRIORITY_ID = "priorityId";
	private static final String PARAM_NAME = "name";
	
	private static final Logger logger = Logger.getLogger(EditPriorityCommand.class);
	
	private DAOFactory mysqlFactory;
	private PriorityDAO priorityDAO;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page;
		mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		priorityDAO = mysqlFactory.getPriorityDAO();
		
		Priority editPriority = getEditPriority(request.getParameter(PARAM_PRIORITY_ID));
		if(editPriority != null){
			// We can show page to edit this priority
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.PRIORITY_PAGE_PATH);

			request.setAttribute("pageTitle", "Edit priority");
			request.setAttribute("editPriority", editPriority);
			
			String name = request.getParameter(PARAM_NAME);
			
			if(name != null && !name.equals("")){
				boolean prioritySuccess = false;
				// It is the request to save changes in a priority

				editPriority.setName(name);
				prioritySuccess = priorityDAO.updatePriority(editPriority);
				
				if(prioritySuccess){
					// priority saved succesfully - show user message about success
					request.setAttribute("successMessage", "Priority updated successfully!");
				} else{
					// There was some error - tell this to a user
					request.setAttribute("errorMessage", "Failed to update the priority!");
				}
			}
			
		} else{
			// priorityId is not valid, so we should return user to page with priorities
			page = new ViewPrioritiesCommand().execute(request, response);
		}

		return page;
	}
	
	/**
	 * Returns a Priority to edit with a specified priorityId
	 * @param priorityId - String from request
	 * @return Priority
	 */
	private Priority getEditPriority(String priorityId){
		Priority editPriority = null;
		Integer id = null;
		if(priorityId != null && !priorityId.equals("")){
			try{
				id = Integer.valueOf(priorityId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit type with not valid Id", e);
			}
		}
		
		if(id != null){
			Priority priority = priorityDAO.getPriorityById(id);
			if(priority != null){
				editPriority = priority;
			}
		}
		
		return editPriority;
	}
}