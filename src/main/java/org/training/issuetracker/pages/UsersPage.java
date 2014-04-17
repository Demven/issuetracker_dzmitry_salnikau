package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Users Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class UsersPage extends AbstractPage{
	
	public static final String NAME = "users";
	
	// Parameters
	public static final String PARAM_FILTER = "filter";
	
	// Attributes
	public static final String ATTR_USERS = "users";
	public static final String ATTR_FILTER = "filter";

}
