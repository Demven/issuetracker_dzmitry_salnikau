package org.training.issuetracker.beans.converters;

import org.training.issuetracker.beans.IssueBean;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.dao.factories.DAOFactory;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.Build;
import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.dao.transferObjects.Priority;
import org.training.issuetracker.dao.transferObjects.Project;
import org.training.issuetracker.dao.transferObjects.Resolution;
import org.training.issuetracker.dao.transferObjects.Role;
import org.training.issuetracker.dao.transferObjects.Status;
import org.training.issuetracker.dao.transferObjects.Type;
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
	
	public static IssueBean convertToIssueBean(Issue issue){
		IssueBean issueBean = new IssueBean();
		
		DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		TypeDAO typeDAO = mysqlFactory.getTypeDAO();
		StatusDAO statusDAO = mysqlFactory.getStatusDAO();
		PriorityDAO priorityDAO = mysqlFactory.getPriorityDAO();
		ProjectDAO projectDAO = mysqlFactory.getProjectDAO();
		ResolutionDAO resolutionDAO = mysqlFactory.getResolutionDAO();
		BuildDAO buildDAO = mysqlFactory.getBuildDAO();
		UserDAO userDAO = mysqlFactory.getUserDAO();
		
		User createdBy = userDAO.getUserById(issue.getCreatedBy());
		User modifiedBy = userDAO.getUserById(issue.getModifiedBy()); 
		Status status = statusDAO.getStatusById(issue.getStatus());
		Resolution resolution = resolutionDAO.getResolutionById(issue.getResolution());
		Type type = typeDAO.getTypeById(issue.getType());
		Priority priority = priorityDAO.getPriorityById(issue.getPriority());
		Project project = projectDAO.getProjectById(issue.getProject());
		Build buildFound = buildDAO.getBuildById(issue.getBuildFound());
		User assignee = userDAO.getUserById(issue.getAssignee());
		
		issueBean.setIssueId(issue.getIssueId());
		issueBean.setCreateDate(issue.getCreateDate());
		issueBean.setCreatedBy(createdBy);
		issueBean.setModifyDate(issue.getModifyDate());
		issueBean.setModifiedBy(modifiedBy);
		issueBean.setSummary(issue.getSummary());
		issueBean.setDescription(issue.getDescription());
		issueBean.setStatus(status);
		issueBean.setResolution(resolution);
		issueBean.setType(type);
		issueBean.setPriority(priority);
		issueBean.setProject(project);
		issueBean.setBuildFound(buildFound);
		issueBean.setAssignee(assignee);
		
		return issueBean;
	}
}
