package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.logic.SessionLogic;

public class LogOutCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws ServletException, IOException {
		
		// Invalidate session
		new SessionLogic().invalidateSession(request);
		
		// Forward to the main page
		new NoCommand().execute(request, response, context);
	}

}
