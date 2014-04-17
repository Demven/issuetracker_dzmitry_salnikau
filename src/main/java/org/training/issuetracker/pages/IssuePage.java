package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Issue Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 10.04.2014
 */
public class IssuePage extends AbstractPage{

	public static final String NAME = "issue";
	
	// Parameters
	public static final String PARAM_SUMMARY = "summary";
	public static final String PARAM_DESCRIPTION = "description";
	public static final String PARAM_STATUS_INDEX = "statusIndex";
	public static final String PARAM_RESOLUTION_ID = "resolutionId";
	public static final String PARAM_TYPE_ID = "typeId";
	public static final String PARAM_PRIORITY_ID = "priorityId";
	public static final String PARAM_PROJECT_ID = "projectId";
	public static final String PARAM_BUILD_ID = "buildId";
	public static final String PARAM_ASSIGNEE_ID = "assigneeId";
	
	// Attributes
	
	public static final String ATTR_EDIT_ISSUE = "editIssue";
	
	public static final String ATTR_TYPES = "types";
	public static final String ATTR_PRIORITIES = "priorities";
	public static final String ATTR_PROJECTS = "projects";
	public static final String ATTR_BUILDS = "builds";
	public static final String ATTR_USERS = "users";
	public static final String ATTR_STATUSES = "statuses";
	public static final String ATTR_RESOLUTIONS = "resolutions";
	
}
