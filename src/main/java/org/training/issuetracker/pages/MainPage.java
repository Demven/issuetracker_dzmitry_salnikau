package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Main Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 04.04.2014
 */
public class MainPage extends AbstractPage{

	public static final String NAME = "index";
	public static final String REDIRECT_NAME = "redirect:/";
	
	// Parameters
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_PASSWORD = "password";
	
	// Attributes
	public static final String ATTR_ASSIGNED_ISSUES = "assignedIssues";
	public static final String ATTR_LATEST_ISSUES = "latestIssues";
	public static final String ATTR_SEARCHED_ISSUES = "searchedIssues";
	public static final String ATTR_PAGES = "pages";
	public static final String ATTR_CURRENT_PAGE = "currentPage";
	public static final String ATTR_SEARCH_FILTER = "searchFilter";
}
