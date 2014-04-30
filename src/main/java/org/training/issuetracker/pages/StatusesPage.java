package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Statuses Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class StatusesPage extends AbstractPage{
	
	public static final String NAME = "statuses";
	
	// Attributes
	public static final String ATTR_STATUSES = "statuses";

	// Messages
	// titles
	public static final String MSG_TTL_STATUSES = "label.statuses.title.statuses";
	// errors
	public static final String MSG_ERR_NO_STATUSES = "label.statuses.error.no_statuses";
	public static final String MSG_ERR_NO_ACCESS = "label.statuses.error.no_access";
}
