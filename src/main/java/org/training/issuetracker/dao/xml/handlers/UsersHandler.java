package org.training.issuetracker.dao.xml.handlers;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.dao.transferObjects.User.Role;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class UsersHandler implements ContentHandler { 
	private ArrayList<User> users;
	private User currentUser = null; 
	private String currentElement = null; 
	 
	public ArrayList<User> getUsers() { 
		return users; 
	} 
	
	@Override
	public void startDocument() {
		users = new ArrayList<User>(); 
	} 
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs){ 
		if(qName.equals(User.ELEMENT_ROOT)){ 
			currentUser = new User();			
			currentUser.setUserId(Integer.valueOf(attrs.getValue(0))); 
		}
		currentElement = qName.toLowerCase(); 
	} 
	
	@Override
	public void endElement(String uri, String localName, String qName) { 
		if(qName.equals(User.ELEMENT_ROOT)){
			users.add(currentUser); 
		}
		currentElement = null;
	} 
	
	@Override
	public void characters(char[] ch, int start, int length) { 
		String innerXML = new String(ch, start, length).trim(); 
		if(currentElement != null){
			switch (currentElement) { 
			case User.SUBELEMENT_FIRST_NAME: 
				currentUser.setFirstName(innerXML); 
				break; 
			case User.SUBELEMENT_LAST_NAME: 
				currentUser.setLastName(innerXML); 
				break;
			case User.SUBELEMENT_EMAIL: 
				currentUser.setEmail(innerXML); 
				break;
			case User.SUBELEMENT_ROLE:
				currentUser.setRole(Role.valueOf(innerXML.toUpperCase())); 
				break;
			case User.SUBELEMENT_PASSWORD: 
				currentUser.setPassword(innerXML); 
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
