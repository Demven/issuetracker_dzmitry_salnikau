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
	
	// Messages
	// titles
	public static final String MSG_TTL_NEW_PROJECT = "label.project.title.new_project";
	public static final String MSG_TTL_EDIT_PROJECT = "label.project.title.edit_project";
	// success
	public static final String MSG_SCS_PROJECT_CREATED = "label.project.success.project_created";
	public static final String MSG_SCS_PROJECT_UPDATED = "label.project.success.project_updated";
	// errors
	public static final String MSG_ERR_NO_ACCESS = "label.project.error.no_access";
	public static final String MSG_ERR_FAILED_TO_SAVE = "label.project.error.failed_to_save";
	public static final String MSG_ERR_NO_PROJECT = "label.project.error.no_project";
	public static final String MSG_ERR_FAILED_TO_UPDATE = "label.project.error.failed_to_update";

}
