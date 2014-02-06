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
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.transferObjects.Project;

public class ProjectsCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		out.println("This is the list of all Projects:");
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		ProjectDAO projectDAO = mysqlFactory.getProjectDAO();
		ArrayList<Project> projects = projectDAO.getProjects();
		
		if(projects != null){
			for(Project project:projects){
				out.println("<br/>ID " + project.getProjectId() + ". Name " + project.getName());
				out.println("<br/>description " + project.getDescription() + ". manager " + project.getManager());
			}
		} else{
			out.println("No projects");
		}
		
		out.close();
	}
}
