package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Profile Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 10.04.2014
 */
public class ProfilePage extends AbstractPage{

	public static final String NAME = "profile";
	
	// Parameters
	public static final String PARAM_USER_ID = "userId";
	public static final String PARAM_FIRST_NAME = "firstName";
	public static final String PARAM_LAST_NAME = "lastName";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_REPEAT_PASSWORD = "repeatPassword";
	
	// Attributes
	public static final String ATTR_EDIT_PROFILE = "editProfile";
	
	// Messages
	// titles
	public static final String MSG_TTL_PROFILE = "label.profile.title.profile";
	// success
	public static final String MSG_SCS_CHANGES_SAVED = "label.profile.success.changes_saved";
	// errors
	public static final String MSG_ERR_NO_USER = "label.profile.error.no_user";
	public static final String MSG_ERR_NO_ACCESS = "label.profile.error.no_access";
	public static final String MSG_ERR_FAILED_TO_SAVE = "label.profile.error.failed_to_save";
	public static final String MSG_ERR_INVALID_VALUES = "label.profile.error.invalid_values";
}
