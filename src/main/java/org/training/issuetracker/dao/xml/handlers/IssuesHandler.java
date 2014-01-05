package org.training.issuetracker.dao.xml.handlers;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Issue;
import org.training.issuetracker.dao.transferObjects.Issue.Priority;
import org.training.issuetracker.dao.transferObjects.Issue.Resolution;
import org.training.issuetracker.dao.transferObjects.Issue.Type;
import org.training.issuetracker.dao.transferObjects.Issue.Status;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class IssuesHandler implements ContentHandler { 
	private ArrayList<Issue> issues;
	private Issue currentIssue = null; 
	private String currentElement = null; 
	 
	public ArrayList<Issue> getIssues() { 
		return issues; 
	} 
	
	@Override
	public void startDocument() {
		issues = new ArrayList<Issue>(); 
	} 
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs){ 
		if(qName.equals(Issue.ELEMENT_ROOT)){ 
			currentIssue = new Issue();			
			currentIssue.setIssueId(Integer.valueOf(attrs.getValue(0))); 
		}
		currentElement = qName.toLowerCase(); 
	} 
	
	@Override
	public void endElement(String uri, String localName, String qName) { 
		if(qName.equals(Issue.ELEMENT_ROOT)){
			issues.add(currentIssue); 
		}
		currentElement = null;
	} 
	
	@Override
	public void characters(char[] ch, int start, int length) { 
		String innerXML = new String(ch, start, length).trim(); 
		if(currentElement != null){
			switch (currentElement) { 
			case Issue.SUBELEMENT_CREATE_DATE: 
				currentIssue.setCreateDate(innerXML); 
				break; 
			case Issue.SUBELEMENT_CREATED_BY: 
				currentIssue.setCreatedBy(Integer.valueOf(innerXML)); 
				break;
			case Issue.SUBELEMENT_MODIFY_DATE: 
				currentIssue.setModifyDate(innerXML); 
				break;
			case Issue.SUBELEMENT_MODIFIED_BY:
				currentIssue.setModifiedBy(Integer.valueOf(innerXML)); 
				break;
			case Issue.SUBELEMENT_SUMMARY: 
				currentIssue.setSummary(innerXML); 
				break;
			case Issue.SUBELEMENT_DESCRIPTION: 
				currentIssue.setDescription(innerXML); 
				break;
			case Issue.SUBELEMENT_STATUS: 
				currentIssue.setStatus(Status.valueOf(innerXML.toUpperCase())); 
				break;
			case Issue.SUBELEMENT_RESOLUTION: 
				currentIssue.setResolution(Resolution.valueOf(innerXML.toUpperCase())); 
				break;
			case Issue.SUBELEMENT_TYPE: 
				currentIssue.setType(Type.valueOf(innerXML.toUpperCase())); 
				break;
			case Issue.SUBELEMENT_PRIORITY: 
				currentIssue.setPriority(Priority.valueOf(innerXML.toUpperCase())); 
				break;
			case Issue.SUBELEMENT_PROJECT_ID: 
				currentIssue.setProjectId(Integer.valueOf(innerXML)); 
				break;
			case Issue.SUBELEMENT_BUILD_FOUND: 
				currentIssue.setBuildFound(Integer.valueOf(innerXML)); 
				break;
			case Issue.SUBELEMENT_ASSIGNEE: 
				currentIssue.setAssignee(Integer.valueOf(innerXML)); 
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

