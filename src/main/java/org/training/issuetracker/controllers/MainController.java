package org.training.issuetracker.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.helpers.MainRequestHelper;
import org.training.issuetracker.managers.ConfigurationManager;

public class MainController extends HttpServlet  implements  javax.servlet.Servlet { 

	private static final Logger logger = Logger.getLogger(MainController.class);
	
	private static final long serialVersionUID = 1325729119279553468L;
	// Object, containing list of all possible commands for this servlet
	MainRequestHelper requestHelper = MainRequestHelper.getInstance();
	
	public MainController() { 
		super(); 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ 
		processRequest(request, response); 
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{ 
		processRequest(request, response); 
	} 
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String page = null;  
		try { 
	    	// Defining command from request and execution (receive page for response)
	        Command command = requestHelper.getCommand(request); 
	        page = command.execute(request, response); 
		} catch (ServletException e) {
			logger.warn(e.toString());
			request.setAttribute("errorMessage", "ServletException<br/>" + e.getStackTrace()); 
			// Page with error description
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH); 
		} catch (IOException e) {
			logger.warn(e.toString());
			request.setAttribute("errorMessage", "IOException<br/>" + e.getStackTrace()); 
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH); 
		} 
		// Page for response
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page); 
		dispatcher.forward(request, response);
	} 
}
