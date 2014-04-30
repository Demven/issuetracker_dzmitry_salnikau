package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Priority Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 17.04.2014
 */
public class PriorityPage extends AbstractPage{

	public static final String NAME = "priority";
	
	// Parameters
	public static final String PARAM_NAME = "name";
	
	// Attributes
	public static final String ATTR_EDIT_PRIORITY = "editPriority";
	
	// Messages
	// titles
	public static final String MSG_TTL_NEW_ISSUE = "label.priority.title.new_priority";
	public static final String MSG_TTL_EDIT_ISSUE = "label.priority.title.edit_priority";
	// success
	public static final String MSG_SCS_PRIORITY_CREATED = "label.priority.success.priority_created";
	public static final String MSG_SCS_PRIORITY_UPDATED = "label.priority.success.priority_updated";
	// errors
	public static final String MSG_ERR_NO_ACCESS = "label.priority.error.no_access";
	public static final String MSG_ERR_FAILED_TO_SAVE = "label.priority.error.failed_to_save";
	public static final String MSG_ERR_INVALID_VALUES = "label.priority.error.invalid_values";
	public static final String MSG_ERR_NO_PRIORITY = "label.priority.error.no_priority";
	public static final String MSG_ERR_FAILED_TO_UPDATE = "label.priority.error.failed_to_update";
}
