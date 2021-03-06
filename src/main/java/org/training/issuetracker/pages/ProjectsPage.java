package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Projects Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class ProjectsPage extends AbstractPage{
	
	public static final String NAME = "projects";
	
	// Parameters
	public static final String PARAM_PAGE = "page";
	
	// Attributes
	public static final String ATTR_PROJECTS = "projects";
	public static final String ATTR_PAGES = "pages";
	public static final String ATTR_CURRENT_PAGE = "currentPage";
	
	// Messages
	// titles
	public static final String MSG_TTL_PROJECTS = "label.projects.title.projects";
	// errors
	public static final String MSG_ERR_NO_ACCESS = "label.projects.error.no_access";
	public static final String MSG_ERR_NO_PAGE = "label.projects.error.no_page";
	public static final String MSG_ERR_NO_PROJECTS = "label.projects.error.no_projects";

}
