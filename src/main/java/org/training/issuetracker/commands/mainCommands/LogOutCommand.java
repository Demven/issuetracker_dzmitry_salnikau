package org.training.issuetracker.commands.mainCommands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.commands.Command;
import org.training.issuetracker.managers.CookieManager;
import org.training.issuetracker.managers.SessionManager;

public class LogOutCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Invalidate session
		new SessionManager().invalidateSession(request);
		
		// Delete cookie
		new CookieManager().removeCookieValue(response, CookieManager.NAME_LOGIN);
		
		// Forward to the main page
		String page = new NoCommand().execute(request, response);
		return page;
	}

}
