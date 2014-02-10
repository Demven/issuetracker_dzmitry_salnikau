package org.training.issuetracker.beans.converters;

import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.transferObjects.Role;
import org.training.issuetracker.dao.transferObjects.User;

/**
 * Convenient class to convert transfer objects into beans used in JSP
 * @author Dzmitry Salnikau
 * @since 10.02.2014
 */
public class BeanConverter {
	
	public static UserBean convertToUserBean(User user){
		UserBean userBean = new UserBean();
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		RoleDAO roleDAO = mysqlFactory.getRoleDAO();
		Role role = roleDAO.getRoleById(user.getRoleId());
		
		userBean.setUserId(user.getUserId());
		userBean.setFirstName(user.getFirstName());
		userBean.setLastName(user.getLastName());
		userBean.setEmail(user.getEmail());
		userBean.setPassword(user.getPassword());
		userBean.setRole(role);
		
		return userBean;
	}
}
