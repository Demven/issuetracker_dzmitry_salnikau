package org.training.issuetracker.helpers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.mainCommands.AuthCommand;
import org.training.issuetracker.commands.mainCommands.LogOutCommand;
import org.training.issuetracker.commands.mainCommands.NoCommand;
import org.training.issuetracker.commands.mainCommands.create.CreateProjectCommand;
import org.training.issuetracker.commands.mainCommands.edit.EditProjectCommand;
import org.training.issuetracker.commands.mainCommands.view.ViewPrioritiesCommand;
import org.training.issuetracker.commands.mainCommands.view.ViewProjectsCommand;
import org.training.issuetracker.commands.mainCommands.view.ViewResolutionsCommand;
import org.training.issuetracker.commands.mainCommands.view.ViewStatusesCommand;

public class MainRequestHelper { 
	private static MainRequestHelper instance = null; 
	
	HashMap<String, Command> commands = new HashMap<String, Command>(); 
 
	private MainRequestHelper() { 
		//заполнение таблицы командами 
		commands.put("auth", new AuthCommand());
		commands.put("logout", new LogOutCommand());
		
		// --> EDIT
		commands.put("editProject", new EditProjectCommand());
		
		// --> CREATE
		commands.put("createProject", new CreateProjectCommand());
		
		// --> VIEW
		commands.put("viewProjects", new ViewProjectsCommand());
		commands.put("viewStatuses", new ViewStatusesCommand());
		commands.put("viewResolutions", new ViewResolutionsCommand());
		commands.put("viewPriorities", new ViewPrioritiesCommand());
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
	public static MainRequestHelper getInstance() { 
		if (instance == null) { 
			instance = new MainRequestHelper(); 
		} 
		return instance; 
	} 
}
