package org.training.issuetracker.dao.factories;

import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.xml.XMLBuildDAO;
import org.training.issuetracker.dao.xml.XMLIssueDAO;
import org.training.issuetracker.dao.xml.XMLProjectDAO;
import org.training.issuetracker.dao.xml.XMLUserDAO;

public class XMLDAOFactory extends DAOFactory {

	public UserDAO getUserDAO(String rootPath){
	    return new XMLUserDAO(rootPath);
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
