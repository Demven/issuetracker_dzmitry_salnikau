package org.training.issuetracker.dao.factories;

import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;

public abstract class DAOFactory {

	  // Supported DAO types
	  public static final int MYSQL = 1;

	  // Methods for each DAO, that can be created
	  public abstract UserDAO getUserDAO();
	  public abstract IssueDAO getIssueDAO();
	  public abstract ProjectDAO getProjectDAO();
	  public abstract BuildDAO getBuildDAO();
	  public abstract StatusDAO getStatusDAO();
	  public abstract ResolutionDAO getResolutionDAO();
	  public abstract PriorityDAO getPriorityDAO();
	  public abstract TypeDAO getTypeDAO();

	  public static DAOFactory getDAOFactory(int whichFactory) {
	    switch (whichFactory){
	      case MYSQL: 
	          return new MySQLDAOFactory();
	      default: 
	          return null;
	    }
	  }
}
