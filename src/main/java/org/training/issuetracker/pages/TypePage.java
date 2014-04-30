package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Type Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 17.04.2014
 */
public class TypePage extends AbstractPage{

	public static final String NAME = "type";
	
	// Parameters
	public static final String PARAM_NAME = "name";
	
	// Attributes
	public static final String ATTR_EDIT_TYPE = "editType";
	
	// Messages
	// titles
	public static final String MSG_TTL_NEW_TYPE = "label.type.title.new_type";
	public static final String MSG_TTL_EDIT_TYPE = "label.type.title.edit_type";
	// success
	public static final String MSG_SCS_TYPE_CREATED = "label.type.success.type_created";
	public static final String MSG_SCS_TYPE_UPDATED = "label.type.success.type_updated";
	// errors
	public static final String MSG_ERR_NO_TYPE = "label.type.error.no_type";
	public static final String MSG_ERR_NO_ACCESS = "label.type.error.no_access";
	public static final String MSG_ERR_FAILED_TO_SAVE = "label.type.error.failed_to_save";
	public static final String MSG_ERR_FAILED_TO_UPDATE = "label.type.error.failed_to_update";
	public static final String MSG_ERR_INVALID_VALUES = "label.type.error.invalid_values";

}
