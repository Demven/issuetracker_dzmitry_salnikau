package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.managers.ConfigurationManager;


public class NoCommand implements Command { 
	 
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		/*в случае прямого обращения к контроллеру - переадресация на главную страницу*/ 
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH); 
		return page;
	} 
}
