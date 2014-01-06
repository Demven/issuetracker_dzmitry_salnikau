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
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.transferObjects.Build;

public class BuildsCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		out.println("This is the list of all Builds:");
		
		DAOFactory xmlFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
		BuildDAO buildDAO = xmlFactory.getBuildDAO(context.getRealPath("/"));
		ArrayList<Build> builds = buildDAO.getBuilds();
		
		if(builds != null){
			for(Build build : builds){
				out.println("<br/>ID " + build.getBuildId() + ". Project " + build.getProject());
				out.println("<br/>version " + build.getVersion());
			}
		} else{
			out.println("No builds");
		}
		
		out.close();
	}
}
