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
	
	// Messages
	// titles
	public static final String MSG_TTL_ASSIGNED_ISSUES = "label.index.title.assigned_issues";
	public static final String MSG_TTL_NO_ASSIGNED = "label.index.title.no_assigned";
	public static final String MSG_TTL_LATEST_ISSUES = "label.index.title.latest_issues";
	public static final String MSG_TTL_SEARCHED_ISSUES = "label.index.title.searched_issues";
	// errors
	public static final String MSG_ERR_NO_ISSUES = "label.index.error.no_issues";
	public static final String MSG_ERR_NO_PAGE = "label.index.error.no_page";
	public static final String MSG_ERR_NO_MATCHES = "label.index.error.no_matches";
	public static final String MSG_ERR_NOT_CORRECT_PASSWORD = "label.index.error.not_correct_password";
}
