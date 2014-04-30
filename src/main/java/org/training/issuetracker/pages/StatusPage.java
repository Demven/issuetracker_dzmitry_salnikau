package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Status Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 17.04.2014
 */
public class StatusPage extends AbstractPage{

	public static final String NAME = "status";
	
	// Parameters
	public static final String PARAM_NAME = "name";
	
	// Attributes
	public static final String ATTR_EDIT_STATUS = "editStatus";
	
	// Messages
	// titles
	public static final String MSG_TTL_EDIT_STATUS = "label.status.title.edit_status";
	// success
	public static final String MSG_SCS_STATUS_UPDATED = "label.status.success.status_updated";
	// errors
	public static final String MSG_ERR_NO_STATUS = "label.status.error.no_status";
	public static final String MSG_ERR_NO_ACCESS = "label.status.error.no_access";
	public static final String MSG_ERR_FAILED_TO_UPDATE = "label.status.error.failed_to_update";
	public static final String MSG_ERR_INVALID_VALUES = "label.status.error.invalid_values";
}
