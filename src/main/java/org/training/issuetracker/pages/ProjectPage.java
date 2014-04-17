package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Project Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 17.04.2014
 */
public class ProjectPage extends AbstractPage{
	
	public static final String NAME = "project";
	
	// Parameters
	public static final String PARAM_NAME = "name";
	public static final String PARAM_DESCRIPTION = "description";
	public static final String PARAM_BUILD_ID= "buildId";
	public static final String PARAM_BUILD_NAME= "buildName";
	public static final String PARAM_MANAGER_ID = "managerId";
	
	// Attributes
	public static final String ATTR_MANAGERS = "managers";
	public static final String ATTR_BUILDS = "builds";
	public static final String ATTR_EDIT_PROJECT = "editProject";
}
