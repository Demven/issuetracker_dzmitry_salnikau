package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about User Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 17.04.2014
 */
public class UserPage extends AbstractPage{
	
	public static final String NAME = "user";
	
	// Parameters
	public static final String PARAM_FIRST_NAME = "firstName";
	public static final String PARAM_LAST_NAME = "lastName";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_ROLE_ID= "roleId";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_REPEAT_PASSWORD = "repeatPassword";
	
	// Attributes
	public static final String ATTR_ROLES = "roles";
	public static final String ATTR_EDIT_USER = "editUser";
}
