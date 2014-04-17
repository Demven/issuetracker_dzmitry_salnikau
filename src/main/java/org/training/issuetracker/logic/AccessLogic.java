package org.training.issuetracker.logic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.training.issuetracker.dao.hibernate.entities.Role;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.managers.SessionManager;

/**
 * Class that provides convenient methods to verify access rights
 * @author Dzmitry Salnikau
 * @since 16.04.2014
 */
@Component
public class AccessLogic {
	
	@Autowired
	private SessionManager sessionManager;

	public boolean isHaveAdminAccess(HttpServletRequest request){
		boolean isHaveAccess = false;
		
		User loginUser = (User) sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER);
		if(loginUser != null && Role.ROLE_ADMIN.equals(loginUser.getRole().getName())){
			// Only administrator has access to this controller
			isHaveAccess = true;
		}
		
		return isHaveAccess;
	}
}
