package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Priorities Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 16.04.2014
 */
public class PrioritiesPage extends AbstractPage{

	public static final String NAME = "priorities";
	
	// Attributes
	public static final String ATTR_PRIORITIES = "priorities";
	
	// Messages
	// titles
	public static final String MSG_TTL_PRIORITIES = "label.priorities.title.priorities";
	// errors
	public static final String MSG_ERR_NO_PRIORITIES = "label.priorities.error.no_priorities";
	public static final String MSG_ERR_NO_ACCESS = "label.priorities.error.no_access";
}
