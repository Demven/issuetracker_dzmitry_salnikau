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
		
		// Delete loginUser from the session
		new SessionManager().removeSessionValue(request, SessionManager.NAME_LOGIN_USER);

		// Delete cookie
		new CookieManager().removeCookieValue(response, CookieManager.NAME_LOGIN);

		return null;
	}
}
