package org.training.issuetracker.dao.xml.handlers;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Project;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ProjectsHandler implements ContentHandler { 
	private ArrayList<Project> projects;
	private Project currentProject = null; 
	private String currentElement = null; 
	 
	public ArrayList<Project> getProjects() { 
		return projects; 
	} 
	
	@Override
	public void startDocument() {
		projects = new ArrayList<Project>(); 
	} 
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs){ 
		if(qName.equals(Project.ELEMENT_ROOT)){ 
			currentProject = new Project();
			currentProject.setProjectId(Integer.valueOf(attrs.getValue(0))); 
		}
		currentElement = qName.toLowerCase(); 
	} 
	
	@Override
	public void endElement(String uri, String localName, String qName) { 
		if(qName.equals(Project.ELEMENT_ROOT)){
			projects.add(currentProject); 
		}
		currentElement = null;
	} 
	
	@Override
	public void characters(char[] ch, int start, int length) { 
		String innerXML = new String(ch, start, length).trim(); 
		if(currentElement != null){
			switch (currentElement) { 
			case Project.SUBELEMENT_NAME: 
				currentProject.setName(innerXML); 
				break; 
			case Project.SUBELEMENT_DESCRIPTION: 
				currentProject.setDescription(innerXML); 
				break;
			case Project.SUBELEMENT_MANAGER: 
				currentProject.setManager(Integer.valueOf(innerXML)); 
				break;
			} 
		}
		
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}
}
