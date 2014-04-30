package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Types Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class TypesPage extends AbstractPage{
	
	public static final String NAME = "types";
	
	// Attributes
	public static final String ATTR_TYPES = "types";

	// Messages
	// titles
	public static final String MSG_TTL_TYPES = "label.types.title.types";
	// errors
	public static final String MSG_ERR_NO_TYPES = "label.types.error.no_types";
	public static final String MSG_ERR_NO_ACCESS = "label.types.error.no_access";
}
