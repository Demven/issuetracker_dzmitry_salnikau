package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Resolutions Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class ResolutionsPage extends AbstractPage{

	public static final String NAME = "resolutions";
	
	// Attributes
	public static final String ATTR_RESOLUTIONS = "resolutions";
	
	// Messages
	// titles
	public static final String MSG_TTL_RESOLUTIONS = "label.resolutions.title.resolutions";
	// errors
	public static final String MSG_ERR_NO_ACCESS = "label.resolutions.error.no_access";
	public static final String MSG_ERR_NO_RESOLUTIONS = "label.resolutions.error.no_resolutions";
}
