package org.training.issuetracker.managers;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession;  

import org.apache.log4j.Logger;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.beans.converters.BeanConverter;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
 
/**
 * Class that manages sessions
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public final class SessionManager { 
	
	private static final Logger logger = Logger.getLogger(SessionManager.class);
	
	public final static int TYPE_LOGIN_USER = 1;
	
	public final static String NAME_LOGIN_USER = "loginUser";
	
	/**
	 * Returns the value of variable from the session
	 * @param request
	 * @param name - name of a variable
	 * @return the specified variable stored in session or null
	 */
	public Object getSessionValue(HttpServletRequest request, String name) { 
		HttpSession session = request.getSession(false);
		Object sessionValue = null;
		if(session != null){
			sessionValue = session.getAttribute(name);
		}
		return sessionValue;
	}

	/**
	 * Sets the value of the new variable in the session
	 * @param request
	 * @param name - key for an attribute
	 * @param value - value for an attribute
	 */
	public void setSessionValue(HttpServletRequest request, String name, Object value) { 
		HttpSession session = request.getSession(true);
		session.setAttribute(name, value); 
	}
	
	/**
	 * Removes the value with a specified name
	 * @param request - HttpServletRequest
	 * @param name - key for an attribute
	 */
	public void removeSessionValue(HttpServletRequest request, String name){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.removeAttribute(name);
		}
	}
	
	/**
	 * Invalidate the current session with client
	 * @param request - HttpServletRequest
	 */
	public void invalidateSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
	}
	
	/**
	 * Returns true if session exists
	 * @return boolean
	 */
	public boolean isSessionExists(HttpServletRequest request){
		boolean isSessionExists = false;
		HttpSession session = request.getSession(false);
		if(session != null){
			isSessionExists = true;
		}
		return isSessionExists;
	}
	
	public void setSessionFromCookies(HttpServletRequest request, int type){
		switch(type){
			case TYPE_LOGIN_USER:
				CookieManager cookieManager = new CookieManager();
				String login = cookieManager.getCookieValue(request, CookieManager.NAME_LOGIN);
		        if(login != null){
		        	if(getSessionValue(request, NAME_LOGIN_USER) == null){
		        		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		        		UserDAO userDAO = mysqlFactory.getUserDAO();
		        		UserBean loginUser = BeanConverter.convertToUserBean(
		        				userDAO.getUserByEmail(login));
		        		// set this object of the authorized user into the session
		        		setSessionValue(request, NAME_LOGIN_USER, loginUser);
		        	}
		        }
		        break;
			default:
				logger.error("Invalid parameter 'type' in the method setSessionFromCookies");
				break;
		}
	}
}

