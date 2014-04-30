package org.training.issuetracker.pages;

/**
 * Class that contains necessary info about Issue Page:
 *  -page name;
 *  -all parameters that are available from this page;
 *  -all attributes that are provided for this page;
 *  
 * @author Dzmitry_Salnikau
 * @since 10.04.2014
 */
public class IssuePage extends AbstractPage{

	public static final String NAME = "issue";
	
	// Parameters
	public static final String PARAM_SUMMARY = "summary";
	public static final String PARAM_DESCRIPTION = "description";
	public static final String PARAM_STATUS_INDEX = "statusIndex";
	public static final String PARAM_RESOLUTION_ID = "resolutionId";
	public static final String PARAM_TYPE_ID = "typeId";
	public static final String PARAM_PRIORITY_ID = "priorityId";
	public static final String PARAM_PROJECT_ID = "projectId";
	public static final String PARAM_BUILD_ID = "buildId";
	public static final String PARAM_ASSIGNEE_ID = "assigneeId";
	public static final String PARAM_COMMENT = "comment";
	public static final String PARAM_USER_ID = "userId";
	public static final String PARAM_FILE = "file";
	
	// Attributes
	
	public static final String ATTR_EDIT_ISSUE = "editIssue";
	
	public static final String ATTR_TYPES = "types";
	public static final String ATTR_PRIORITIES = "priorities";
	public static final String ATTR_PROJECTS = "projects";
	public static final String ATTR_BUILDS = "builds";
	public static final String ATTR_USERS = "users";
	public static final String ATTR_STATUSES = "statuses";
	public static final String ATTR_RESOLUTIONS = "resolutions";
	public static final String ATTR_COMMENTS = "comments";
	public static final String ATTR_ATTACHMENTS = "attachments";
	
	// Messages
	// titles
	public static final String MSG_TTL_NEW_ISSUE = "label.issue.title.new_issue";
	public static final String MSG_TTL_EDIT_ISSUE = "label.issue.title.edit_issue";
	// success
	public static final String MSG_SCS_ISSUE_CREATED = "label.issue.success.issue_created";
	public static final String MSG_SCS_CHANGES_SAVED = "label.issue.success.changes_saved";
	public static final String MSG_SCS_COMMENT_SAVED = "label.issue.success.comment_saved";
	public static final String MSG_SCS_ATTACHMENT_SAVED = "label.issue.success.attachment_saved";
	// errors
	public static final String MSG_ERR_ISSUE_FAILED = "label.issue.error.issue_failed";
	public static final String MSG_ERR_FILL_SUMMARY = "label.issue.error.fill_summary";
	public static final String MSG_ERR_NO_ISSUE = "label.issue.error.no_issue";
	public static final String MSG_ERR_CHANGES_FAILED = "label.issue.error.changes_failed";
	public static final String MSG_ERR_COMMENT_FAILED = "label.issue.error.comment_failed";
	public static final String MSG_ERR_ENTER_COMMENT = "label.issue.error.enter_comment";
	public static final String MSG_ERR_FILE_FAILED = "label.issue.error.file_failed";
	public static final String MSG_ERR_FILE_EMPTY = "label.issue.error.file_empty";

}










