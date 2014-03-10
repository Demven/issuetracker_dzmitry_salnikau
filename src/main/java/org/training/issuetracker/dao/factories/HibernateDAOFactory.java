package org.training.issuetracker.dao.factories;

import org.training.issuetracker.dao.hibernate.HibernateBuildDAO;
import org.training.issuetracker.dao.hibernate.HibernateIssueDAO;
import org.training.issuetracker.dao.hibernate.HibernatePriorityDAO;
import org.training.issuetracker.dao.hibernate.HibernateProjectDAO;
import org.training.issuetracker.dao.hibernate.HibernateResolutionDAO;
import org.training.issuetracker.dao.hibernate.HibernateRoleDAO;
import org.training.issuetracker.dao.hibernate.HibernateStatusDAO;
import org.training.issuetracker.dao.hibernate.HibernateTypeDAO;
import org.training.issuetracker.dao.hibernate.HibernateUserDAO;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new HibernateUserDAO();
	}

	@Override
	public IssueDAO getIssueDAO() {
		return new HibernateIssueDAO();
	}

	@Override
	public ProjectDAO getProjectDAO() {
		return new HibernateProjectDAO();
	}

	@Override
	public BuildDAO getBuildDAO() {
		return new HibernateBuildDAO();
	}

	@Override
	public StatusDAO getStatusDAO() {
		return new HibernateStatusDAO();
	}

	@Override
	public ResolutionDAO getResolutionDAO() {
		return new HibernateResolutionDAO();
	}

	@Override
	public PriorityDAO getPriorityDAO() {
		return new HibernatePriorityDAO();
	}

	@Override
	public TypeDAO getTypeDAO() {
		return new HibernateTypeDAO();
	}

	@Override
	public RoleDAO getRoleDAO() {
		return new HibernateRoleDAO();
	}
	
}

