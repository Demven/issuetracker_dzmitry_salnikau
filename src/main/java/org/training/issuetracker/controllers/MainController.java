package org.training.issuetracker.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.training.issuetracker.commands.Command;
import org.training.issuetracker.commands.main.NoCommand;
import org.training.issuetracker.helpers.MainRequestHelper;
import org.training.issuetracker.managers.CookieManager;
import org.training.issuetracker.managers.SessionManager;

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
		try { 
	    	// Defining command from request, execution and getting page to forward
	        Command command = requestHelper.getCommand(request); 
	        String page = command.execute(request, response); 

	        if(page != null){
	        	if(new CookieManager().getCookieValue(request, CookieManager.NAME_LOGIN) != null){
		        	// set session from cookies
		        	SessionManager sessionManager = new SessionManager();
			        if(sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER) == null){
			        	sessionManager.setSessionFromCookies(request, SessionManager.TYPE_LOGIN_USER);
			        }
		        }
	        } else{
	        	page = new NoCommand().execute(request, response);
	        }
	        
	        // forward to the received page
	        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page); 
			dispatcher.forward(request, response);
			
		} catch (ServletException e) {
			logger.warn(e.toString());
		} catch (IOException e) {
			logger.warn(e.toString());
		}
	} 
}
