package org.training.issuetracker.managers;

import java.util.ResourceBundle;

public class ConfigurationManager { 
	private static ConfigurationManager instance; 
	private ResourceBundle resourceBundle; 
 
	// Class gets data from config.properties 
	private static final String BUNDLE_NAME = "config"; 
	
	// Pages for users
	public static final String MAIN_PAGE_HEADER_PATH = "MAIN_PAGE_HEADER_PATH";
	public static final String MAIN_PAGE_BODY_PATH = "MAIN_PAGE_BODY_PATH";
	public static final String MAIN_PAGE_FOOTER_PATH = "MAIN_PAGE_FOOTER_PATH";
	
	// XML
	public static final String XML_USERS_PATH = "XML_USERS_PATH";
	public static final String XML_ISSUES_PATH = "XML_ISSUES_PATH";
	
	public static ConfigurationManager getInstance() { 
		if (instance == null) { 
			instance = new ConfigurationManager(); 
			instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME); 
		} 
		return instance; 
	} 
	
	public String getProperty(String key) { 
		return (String)resourceBundle.getObject(key); 
	} 
}
