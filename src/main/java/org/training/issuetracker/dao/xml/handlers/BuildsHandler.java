package org.training.issuetracker.dao.xml.handlers;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Build;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class BuildsHandler implements ContentHandler { 
	private ArrayList<Build> builds;
	private Build currentBuild = null; 
	private String currentElement = null; 
	 
	public ArrayList<Build> getBuilds() { 
		return builds; 
	} 
	
	@Override
	public void startDocument() {
		builds = new ArrayList<Build>(); 
	} 
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs){ 
		if(qName.equals(Build.ELEMENT_ROOT)){ 
			currentBuild = new Build();
			currentBuild.setBuildId(Integer.valueOf(attrs.getValue(0))); 
		}
		currentElement = qName.toLowerCase(); 
	} 
	
	@Override
	public void endElement(String uri, String localName, String qName) { 
		if(qName.equals(Build.ELEMENT_ROOT)){
			builds.add(currentBuild); 
		}
		currentElement = null;
	} 
	
	@Override
	public void characters(char[] ch, int start, int length) { 
		String innerXML = new String(ch, start, length).trim(); 
		if(currentElement != null){
			switch (currentElement) { 
			case Build.SUBELEMENT_PROJECT: 
				currentBuild.setProject(Integer.valueOf(innerXML)); 
				break; 
			case Build.SUBELEMENT_VERSION: 
				currentBuild.setVersion(innerXML); 
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
