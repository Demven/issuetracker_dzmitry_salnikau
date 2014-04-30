package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Resolution Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class ResolutionPage extends AbstractPage{

	public static final String NAME = "resolution";
	
	// Parameters
	public static final String PARAM_NAME = "name";
	
	// Attributes
	public static final String ATTR_EDIT_RESOLUTION = "editResolution";
	
	// Messages
	// titles
	public static final String MSG_TTL_NEW_RESOLUTION = "label.resolution.title.new_resolution";
	public static final String MSG_TTL_EDIT_RESOLUTION = "label.resolution.title.edit_resolution";
	// success
	public static final String MSG_SCS_RESOLUTION_CREATED = "label.resolution.success.resolution_created";
	public static final String MSG_SCS_RESOLUTION_UPDATED = "label.resolution.success.resolution_updated";
	// errors
	public static final String MSG_ERR_NO_ACCESS = "label.resolution.error.no_access";
	public static final String MSG_ERR_FAILED_TO_SAVE = "label.resolution.error.failed_to_save";
	public static final String MSG_ERR_INVALID_VALUES = "label.resolution.error.invalid_values";
	public static final String MSG_ERR_NO_RESOLUTION = "label.resolution.error.no_resolution";
	public static final String MSG_ERR_FAILED_TO_UPDATE = "label.resolution.error.failed_to_update";
}
