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
	
	
	//Cookies
	public static final String COOKIE_LIFE_TIME = "COOKIE_LIFE_TIME";
	
	
	// Pages
	public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
	
	public static final String PROJECT_PAGE_PATH = "PROJECT_PAGE_PATH";
	public static final String PROJECTS_PAGE_PATH = "PROJECTS_PAGE_PATH";
	
	public static final String STATUS_PAGE_PATH = "STATUS_PAGE_PATH";
	public static final String STATUSES_PAGE_PATH = "STATUSES_PAGE_PATH";
	
	public static final String PRIORITY_PAGE_PATH = "PRIORITY_PAGE_PATH";
	public static final String PRIORITIES_PAGE_PATH = "PRIORITIES_PAGE_PATH";
	
	public static final String RESOLUTION_PAGE_PATH = "RESOLUTION_PAGE_PATH";
	public static final String RESOLUTIONS_PAGE_PATH = "RESOLUTIONS_PAGE_PATH";
	
	public static final String TYPE_PAGE_PATH = "TYPE_PAGE_PATH";
	public static final String TYPES_PAGE_PATH = "TYPES_PAGE_PATH";
	
	public static final String USER_PAGE_PATH = "USER_PAGE_PATH";
	public static final String USERS_PAGE_PATH = "USERS_PAGE_PATH";
	
	public static final String PROFILE_PAGE_PATH = "PROFILE_PAGE_PATH";
	
	
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
