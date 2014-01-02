package org.training.issuetracker.managers;

import java.util.ResourceBundle;

public class ConfigurationManager { 
	private static ConfigurationManager instance; 
	private ResourceBundle resourceBundle; 
 
	// Class gets data from config.properties 
	private static final String BUNDLE_NAME = "config"; 
	
	// Pages for users
	public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
	public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
	
	// Cookies
	public static final String COOKIE_LIFE_TIME = "COOKIE_LIFE_TIME";
	
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
