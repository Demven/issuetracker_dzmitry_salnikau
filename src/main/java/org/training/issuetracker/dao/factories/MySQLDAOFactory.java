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
	public UserDAO getUserDAO() {
		return new MySQLUserDAO();
	}

	@Override
	public IssueDAO getIssueDAO() {
		return new MySQLIssueDAO();
	}

	@Override
	public ProjectDAO getProjectDAO() {
		return new MySQLProjectDAO();
	}

	@Override
	public BuildDAO getBuildDAO() {
		return new MySQLBuildDAO();
	}

	@Override
	public StatusDAO getStatusDAO() {
		return new MySQLStatusDAO();
	}

	@Override
	public ResolutionDAO getResolutionDAO() {
		return new MySQLResolutionDAO();
	}

	@Override
	public PriorityDAO getPriorityDAO() {
		return new MySQLPriorityDAO();
	}

	@Override
	public TypeDAO getTypeDAO() {
		return new MySQLTypeDAO();
	}

	@Override
	public RoleDAO getRoleDAO() {
		return new MySQLRoleDAO();
	}
	
}
