package org.training.issuetracker.dao.xml;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.transferObjects.Project;
import org.training.issuetracker.dao.xml.handlers.ProjectsHandler;
import org.training.issuetracker.managers.ConfigurationManager;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLProjectDAO implements ProjectDAO {

	private static final Logger logger = Logger.getLogger(XMLProjectDAO.class);
	private static final String XML_FILENAME = ConfigurationManager.getInstance().getProperty(ConfigurationManager.XML_PROJECTS_PATH);
	
	private String xmlFullPath;
	
	public XMLProjectDAO(String rootPath){
		xmlFullPath = rootPath + XML_FILENAME;
	}
	
	@Override
	public ArrayList<Project> getProjects() {
		ArrayList<Project> projects = null;
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();  
			ProjectsHandler handler = new ProjectsHandler(); 
			reader.setContentHandler(handler); 
			reader.parse(xmlFullPath); 
			projects = handler.getProjects();
		} catch (SAXException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		} catch (IOException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		}
		return projects;
	}
	
	@Override
	public Project getProjectById(int projectId) {
		Project searchedProject = null;
		ArrayList<Project> projects = getProjects();
		for(Project project: projects){
			if(project.getProjectId() == projectId){
				searchedProject = project;
			}
		}
		return searchedProject;
	}

	@Override
	public boolean createProject(Project project) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProject(int projectId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProject(Project project) {
		// TODO Auto-generated method stub
		return false;
	}

}
