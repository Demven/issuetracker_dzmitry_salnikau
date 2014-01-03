package org.training.issuetracker.dao.factories;

import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;

public abstract class DAOFactory {

	  // Supported DAO types
	  public static final int XML = 1;

	  // Methods for each DAO, that can be created
	  public abstract UserDAO getUserDAO();
	  public abstract IssueDAO getIssueDAO();
	  public abstract ProjectDAO getProjectDAO();
	  public abstract BuildDAO getBuildDAO();

	  public static DAOFactory getDAOFactory(int whichFactory) {
	    switch (whichFactory){
	      case XML: 
	          return new XMLDAOFactory();
	      default: 
	          return null;
	    }
	  }
}
