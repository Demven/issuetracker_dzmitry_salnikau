package org.training.issuetracker.helpers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.mainCommands.NoCommand;

public class MainRequestHelper { 
	private static MainRequestHelper instance = null; 
	
	HashMap<String, Command> commands = new HashMap<String, Command>(); 
 
	private MainRequestHelper() { 
		//заполнение таблицы командами 
		//commands.put("changeLocale", new ChangeLocaleCommand());
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
