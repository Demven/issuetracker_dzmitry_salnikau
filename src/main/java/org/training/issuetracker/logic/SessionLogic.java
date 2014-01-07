package org.training.issuetracker.logic;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession;  
 
/**
 * Class that encapsulates the logic with session
 * @author Dmitry Dementor Salnikov
 * @since 20.09.2013
 */
public final class SessionLogic { 
	
	public final static String TYPE_USER = "user";
	
	/**
	 * Returns the value of variable from the session
	 * @param request
	 * @param name - name of a variable
	 * @return the specified variable stored in session
	 */
	public Object getSessionValue(HttpServletRequest request, String name) { 
		HttpSession session = request.getSession();
		Object sessionValue = session.getAttribute(name);
		return sessionValue;
	}

	/**
	 * Sets the value of the new variable in the session
	 * @param request
	 * @param name 
	 * @param value
	 */
	public void setSessionValue(HttpServletRequest request, String name, Object value) { 
		HttpSession session = request.getSession(true);
		session.setAttribute(name, value); 
	}
}

