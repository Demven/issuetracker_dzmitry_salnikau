package org.training.issuetracker.dao.factories;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.dao.connectionPool.DBConnectionPool;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.mysql.MySQLBuildDAO;
import org.training.issuetracker.dao.mysql.MySQLIssueDAO;
import org.training.issuetracker.dao.mysql.MySQLPriorityDAO;
import org.training.issuetracker.dao.mysql.MySQLProjectDAO;
import org.training.issuetracker.dao.mysql.MySQLResolutionDAO;
import org.training.issuetracker.dao.mysql.MySQLStatusDAO;
import org.training.issuetracker.dao.mysql.MySQLTypeDAO;
import org.training.issuetracker.dao.mysql.MySQLUserDAO;
import org.training.issuetracker.dao.mysql.MySQLRoleDAO;

public class MySQLDAOFactory extends DAOFactory {
	 
	/**
	 * Returns a connection to the MySQL
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		DBConnectionPool pool = new DBConnectionPool();
		return pool.getConnection(); 
	}

	@Override
	@Deprecated
	public UserDAO getUserDAO() {
		return null;
	}

	@Override
	@Deprecated
	public IssueDAO getIssueDAO() {
		return null;
	}

	@Override
	@Deprecated
	public ProjectDAO getProjectDAO() {
		return null;
	}

	@Override
	@Deprecated
	public BuildDAO getBuildDAO() {
		return null;
	}

	@Override
	@Deprecated
	public StatusDAO getStatusDAO() {
		return null;
	}

	@Override
	@Deprecated
	public ResolutionDAO getResolutionDAO() {
		return null;
	}

	@Override
	public PriorityDAO getPriorityDAO() {
		return null;
	}

	@Override
	public TypeDAO getTypeDAO() {
		return null;
	}

	@Override
	@Deprecated
	public RoleDAO getRoleDAO() {
		return null;
	}
	
}
