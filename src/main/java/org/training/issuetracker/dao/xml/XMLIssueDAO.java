package org.training.issuetracker.dao.xml;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.dao.xml.handlers.IssuesHandler;
import org.training.issuetracker.managers.ConfigurationManager;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLIssueDAO implements IssueDAO{

	private static final Logger logger = Logger.getLogger(XMLIssueDAO.class);
	private static final String XML_FILENAME = ConfigurationManager.getInstance().getProperty(ConfigurationManager.XML_ISSUES_PATH);
	
	private String xmlFullPath;
	
	public XMLIssueDAO(String rootPath){
		xmlFullPath = rootPath + XML_FILENAME;
	}
	
	@Override
	public ArrayList<Issue> getIssues() {
		ArrayList<Issue> issues = null;
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();  
			IssuesHandler handler = new IssuesHandler(); 
			reader.setContentHandler(handler); 
			reader.parse(xmlFullPath); 
			issues = handler.getIssues();
		} catch (SAXException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		} catch (IOException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		}
		return issues;
	}
	
	@Override
	public Issue getIssueById(String issueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createIssue(Issue issue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteIssue(String issueId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateIssue(Issue issue) {
		// TODO Auto-generated method stub
		return false;
	}

}
