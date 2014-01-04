package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.managers.ConfigurationManager;


public class NoCommand implements Command { 
	 
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException { 
		// In case of direct address to the servlet - show main page
		String header = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_HEADER_PATH);
		context.getRequestDispatcher(header).include(request, response);
		
		String body = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_BODY_PATH);
		context.getRequestDispatcher(body).include(request, response);
		
		String footer = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_FOOTER_PATH);
		context.getRequestDispatcher(footer).include(request, response);
	} 
}
