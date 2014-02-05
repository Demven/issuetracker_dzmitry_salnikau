package org.training.issuetracker.dao.factories;

import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;

public abstract class DAOFactory {

	  // Supported DAO types
	  public static final int XML = 1;
	  public static final int MYSQL = 2;

	  // Methods for each DAO, that can be created
	  public abstract UserDAO getUserDAO(String rootPath);
	  public abstract IssueDAO getIssueDAO(String rootPath);
	  public abstract ProjectDAO getProjectDAO(String rootPath);
	  public abstract BuildDAO getBuildDAO(String rootPath);

	  public static DAOFactory getDAOFactory(int whichFactory) {
	    switch (whichFactory){
	      case XML: 
	          return new XMLDAOFactory();
	      case MYSQL: 
	          return new MySQLDAOFactory();
	      default: 
	          return null;
	    }
	  }
}
