package org.training.issuetracker.dao.xml;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.transferObjects.Build;
import org.training.issuetracker.dao.xml.handlers.BuildsHandler;
import org.training.issuetracker.managers.ConfigurationManager;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLBuildDAO implements BuildDAO{

	private static final Logger logger = Logger.getLogger(XMLBuildDAO.class);
	private static final String XML_FILENAME = ConfigurationManager.getInstance().getProperty(ConfigurationManager.XML_BUILDS_PATH);
	
	private String xmlFullPath;
	
	public XMLBuildDAO(String rootPath){
		xmlFullPath = rootPath + XML_FILENAME;
	}
	
	@Override
	public ArrayList<Build> getBuilds() {
		ArrayList<Build> builds = null;
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();  
			BuildsHandler handler = new BuildsHandler(); 
			reader.setContentHandler(handler); 
			reader.parse(xmlFullPath); 
			builds = handler.getBuilds();
		} catch (SAXException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		} catch (IOException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		}
		return builds;
	}
	
	@Override
	public Build getBuildById(String buildId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createBuild(Build build) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBuild(String buildId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBuild(Build build) {
		// TODO Auto-generated method stub
		return false;
	}

}
