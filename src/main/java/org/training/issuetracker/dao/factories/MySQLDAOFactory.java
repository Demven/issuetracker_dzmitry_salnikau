package org.training.issuetracker.dao.factories;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.dao.connectionPool.DBConnectionPool;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.mysql.MySQLBuildDAO;
import org.training.issuetracker.dao.mysql.MySQLIssueDAO;
import org.training.issuetracker.dao.mysql.MySQLProjectDAO;
import org.training.issuetracker.dao.mysql.MySQLUserDAO;


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
	public UserDAO getUserDAO(String rootPath) {
		return new MySQLUserDAO();
	}

	@Override
	public IssueDAO getIssueDAO(String rootPath) {
		return new MySQLIssueDAO();
	}

	@Override
	public ProjectDAO getProjectDAO(String rootPath) {
		return new MySQLProjectDAO();
	}

	@Override
	public BuildDAO getBuildDAO(String rootPath) {
		return new MySQLBuildDAO();
	}
	
}
