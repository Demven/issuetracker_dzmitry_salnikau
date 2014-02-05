package org.training.issuetracker.managers;

import java.util.ResourceBundle;

public class ConfigurationManager { 
	private static ConfigurationManager instance; 
	private ResourceBundle resourceBundle; 
 
	// Class gets data from config.properties 
	private static final String BUNDLE_NAME = "config"; 
	
	// Database
	public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME"; 
	public static final String DATABASE_URL = "DATABASE_URL"; 
	public static final String DATABASE_USER = "DATABASE_USER"; 
	public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD"; 
	
	// Pages for users
	public static final String HEADER_PATH = "HEADER_PATH";
	public static final String FOOTER_PATH = "FOOTER_PATH";
	
	public static final String MAIN_PAGE_BODY_PATH = "MAIN_PAGE_BODY_PATH";
	
	// XML
	public static final String XML_USERS_PATH = "XML_USERS_PATH";
	public static final String XML_ISSUES_PATH = "XML_ISSUES_PATH";
	public static final String XML_PROJECTS_PATH = "XML_PROJECTS_PATH";
	public static final String XML_BUILDS_PATH = "XML_BUILDS_PATH";
	
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
