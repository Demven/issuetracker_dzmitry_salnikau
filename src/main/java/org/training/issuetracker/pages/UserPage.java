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
	
	// Messages
	// titles
	public static final String MSG_TTL_NEW_USER = "label.user.title.new_user";
	public static final String MSG_TTL_EDIT_USER = "label.user.title.edit_user";
	// success
	public static final String MSG_SCS_USER_CREATED = "label.user.success.user_created";
	public static final String MSG_SCS_CHANGES_SAVED = "label.user.success.changes_saved";
	// errors
	public static final String MSG_ERR_NO_USER = "label.user.error.no_user";
	public static final String MSG_ERR_NO_ACCESS = "label.user.error.no_access";
	public static final String MSG_ERR_FAILED_TO_CREATE = "label.user.error.failed_to_create";
	public static final String MSG_ERR_CHANGES_FAILED = "label.user.error.changes_failed";
	public static final String MSG_ERR_INVALID_VALUES = "label.user.error.invalid_values";
}
