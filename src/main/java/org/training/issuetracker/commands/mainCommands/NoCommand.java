package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.managers.ConfigurationManager;


public class NoCommand implements Command { 
	
	PrintWriter out;
	 
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException { 
		// In case of direct address to the servlet - show main page
		response.setContentType("text/html");
		out = response.getWriter();
		
		String header = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HEADER_PATH);
		context.getRequestDispatcher(header).include(request, response);
		
		DAOFactory xmlFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
		IssueDAO issueDAO = xmlFactory.getIssueDAO(context.getRealPath("/"));
		ArrayList<Issue> issues = issueDAO.getIssues();
		printIssuesData(issues);
		
		String body = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_BODY_PATH);
		context.getRequestDispatcher(body).include(request, response);
		
		String footer = ConfigurationManager.getInstance().getProperty(ConfigurationManager.FOOTER_PATH);
		context.getRequestDispatcher(footer).include(request, response);
	} 
	
	/**
	 * Prints into the response all data from List of issues
	 * Prints in a such way, so that JS on the user's page can process this data and show in the best way.
	 * @param issues
	 */
	private void printIssuesData(ArrayList<Issue> issues){
		out.println("<div id='issues_data' style='visibility:hidden; height:0px; width:0px; overflow:hidden;'>");
		
		for(Issue issue : issues){
			out.println("<div id ='issueObject'>");
				out.println("<div id ='issueId'>" + issue.getIssueId() + "</div>");
				out.println("<div id ='createDate'>" + issue.getCreateDate() + "</div>");
				out.println("<div id ='createdBy'>" + issue.getCreatedBy() + "</div>");
				out.println("<div id ='modifyDate'>" + issue.getModifyDate() + "</div>");
				out.println("<div id ='modifiedBy'>" + issue.getModifiedBy() + "</div>");
				out.println("<div id ='summary'>" + issue.getSummary() + "</div>");
				out.println("<div id ='description'>" + issue.getDescription() + "</div>");
				out.println("<div id ='status'>" + issue.getStatus() + "</div>");
				out.println("<div id ='resolution'>" + issue.getResolution() + "</div>");
				out.println("<div id ='type'>" + issue.getType() + "</div>");
				out.println("<div id ='priority'>" + issue.getPriority() + "</div>");
				out.println("<div id ='project'>" + issue.getProjectId() + "</div>");
				out.println("<div id ='buildFound'>" + issue.getBuildFound() + "</div>");
				out.println("<div id ='assignee'>" + issue.getAssignee() + "</div>");
			out.println("</div>");
		}
		
		out.println("</div>");
	}
	
}
