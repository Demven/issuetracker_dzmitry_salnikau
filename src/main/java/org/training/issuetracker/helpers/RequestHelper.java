package org.training.issuetracker.helpers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.create.CreatePriorityCommand;
import org.training.issuetracker.commands.create.CreateProjectCommand;
import org.training.issuetracker.commands.create.CreateResolutionCommand;
import org.training.issuetracker.commands.create.CreateTypeCommand;
import org.training.issuetracker.commands.create.CreateUserCommand;
import org.training.issuetracker.commands.edit.EditPriorityCommand;
import org.training.issuetracker.commands.edit.EditProjectCommand;
import org.training.issuetracker.commands.edit.EditResolutionCommand;
import org.training.issuetracker.commands.edit.EditStatusCommand;
import org.training.issuetracker.commands.edit.EditTypeCommand;
import org.training.issuetracker.commands.main.AuthCommand;
import org.training.issuetracker.commands.main.LogOutCommand;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.commands.view.ViewPrioritiesCommand;
import org.training.issuetracker.commands.view.ViewProjectsCommand;
import org.training.issuetracker.commands.view.ViewResolutionsCommand;
import org.training.issuetracker.commands.view.ViewStatusesCommand;
import org.training.issuetracker.commands.view.ViewTypesCommand;
import org.training.issuetracker.commands.view.ViewUsersCommand;

public class RequestHelper { 
	private static RequestHelper instance = null; 
	
	HashMap<String, Command> commands = new HashMap<String, Command>(); 
 
	private RequestHelper() { 
		//заполнение таблицы командами 
		
		// MAIN
		commands.put("auth", new AuthCommand());
		commands.put("logout", new LogOutCommand());		
		
		// --> EDIT
		commands.put("editProject", new EditProjectCommand());
		commands.put("editStatus", new EditStatusCommand());
		commands.put("editResolution", new EditResolutionCommand());
		commands.put("editPriority", new EditPriorityCommand());
		commands.put("editType", new EditTypeCommand());
		
		// --> CREATE
		commands.put("createUser", new CreateUserCommand());
		commands.put("createProject", new CreateProjectCommand());
		commands.put("createResolution", new CreateResolutionCommand());
		commands.put("createPriority", new CreatePriorityCommand());
		commands.put("createType", new CreateTypeCommand());
		
		// --> VIEW
		commands.put("viewUsers", new ViewUsersCommand());
		commands.put("viewProjects", new ViewProjectsCommand());
		commands.put("viewStatuses", new ViewStatusesCommand());
		commands.put("viewResolutions", new ViewResolutionsCommand());
		commands.put("viewPriorities", new ViewPrioritiesCommand());
		commands.put("viewTypes", new ViewTypesCommand());
	}  
 
	public Command getCommand(HttpServletRequest request) { 
		//извлечение команды из запроса 
		String action = request.getParameter("command"); 
		//получение объекта, соответствующего команде 
		Command command = commands.get(action); 
		if (command == null) { 
			//если команды не существует в текущем объекте 
			command = new NoCommand(); 
		} 
		return command; 
	}
   
	//создание единственного объекта по шаблону Singleton 
	public static RequestHelper getInstance() { 
		if (instance == null) { 
			instance = new RequestHelper(); 
		} 
		return instance; 
	} 
}
