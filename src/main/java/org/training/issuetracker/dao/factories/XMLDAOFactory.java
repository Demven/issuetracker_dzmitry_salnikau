package org.training.issuetracker.dao.factories;

import org.training.issuetracker.dao.XML.XMLBuildDAO;
import org.training.issuetracker.dao.XML.XMLIssueDAO;
import org.training.issuetracker.dao.XML.XMLProjectDAO;
import org.training.issuetracker.dao.XML.XMLUserDAO;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;

public class XMLDAOFactory extends DAOFactory {
	
	// TODO: Here will be method to xml access 

	public UserDAO getUserDAO(){
	    return new XMLUserDAO();
	}
	
	public IssueDAO getIssueDAO(){
	    return new XMLIssueDAO();
	}
	
	public ProjectDAO getProjectDAO(){
	    return new XMLProjectDAO();
	}
	
	public BuildDAO getBuildDAO(){
	    return new XMLBuildDAO();
	}
}
