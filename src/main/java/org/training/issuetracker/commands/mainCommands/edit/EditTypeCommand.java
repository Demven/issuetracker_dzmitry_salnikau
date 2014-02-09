package org.training.issuetracker.commands.mainCommands.edit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.mainCommands.view.ViewTypesCommand;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.transferObjects.Type;
import org.training.issuetracker.managers.ConfigurationManager;

public class EditTypeCommand implements Command{
	
	private static final String PARAM_TYPE_ID = "typeId";
	private static final String PARAM_NAME = "name";
	
	private static final Logger logger = Logger.getLogger(EditTypeCommand.class);
	
	private DAOFactory mysqlFactory;
	private TypeDAO typeDAO;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page;
		mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		typeDAO = mysqlFactory.getTypeDAO();
		
		Type editType = getEditType(request.getParameter(PARAM_TYPE_ID));
		if(editType != null){
			// We can show page to edit this type
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.TYPE_PAGE_PATH);

			request.setAttribute("pageTitle", "Edit type");
			request.setAttribute("editType", editType);
			
			String name = request.getParameter(PARAM_NAME);
			
			if(name != null && !name.equals("")){
				boolean typeSuccess = false;
				// It is the request to save changes in a type

				editType.setName(name);
				typeSuccess = typeDAO.updateType(editType);
				
				if(typeSuccess){
					// type saved succesfully - show user message about success
					request.setAttribute("successMessage", "Type updated successfully!");
				} else{
					// There was some error - tell this to a user
					request.setAttribute("errorMessage", "Failed to update the type!");
				}
			}
			
		} else{
			// typeId is not valid, so we should return user to page with types
			page = new ViewTypesCommand().execute(request, response);
		}

		return page;
	}
	
	/**
	 * Returns a Type to edit with a specified typeId
	 * @param typeId - String from request
	 * @return Type
	 */
	private Type getEditType(String typeId){
		Type editType = null;
		Integer id = null;
		if(typeId != null && !typeId.equals("")){
			try{
				id = Integer.valueOf(typeId);
			} catch(NumberFormatException e){
				logger.warn("Attempt to edit type with not valid Id", e);
			}
		}
		
		if(id != null){
			Type type = typeDAO.getTypeById(id);
			if(type != null){
				editType = type;
			}
		}
		
		return editType;
	}
}
