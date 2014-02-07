package org.training.issuetracker.dao.transferObjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Class, describing an issue
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Issue implements java.io.Serializable, Comparable<Issue> {

	private static final long serialVersionUID = 4377407675405841235L;
	
	public final static int MAX_SHOWN_NUMBER = 10;
	
	// For database
	public final static String TABLE_NAME = "issues";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_CREATE_DATE = "createdate";
	public final static String COLUMN_NAME_CREATED_BY = "createdby";
	public final static String COLUMN_NAME_MODIFY_DATE = "modifydate";
	public final static String COLUMN_NAME_MODIFIED_BY = "modifiedby";
	public final static String COLUMN_NAME_SUMMARY = "summary";
	public final static String COLUMN_NAME_DESCRIPTION = "description";
	public final static String COLUMN_NAME_STATUS = "status";
	public final static String COLUMN_NAME_RESOLUTION = "resolution";
	public final static String COLUMN_NAME_ISSUE_TYPE = "issuetype";
	public final static String COLUMN_NAME_PRIORITY = "priority";
	public final static String COLUMN_NAME_PROJECT = "project";
	public final static String COLUMN_NAME_BUILD_FOUND = "buildfound";
	public final static String COLUMN_NAME_ASSIGNEE = "assignee";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_CREATE_DATE = 2;
	public final static int COLUMN_ID_CREATED_BY = 3;
	public final static int COLUMN_ID_MODIFY_DATE = 4;
	public final static int COLUMN_ID_MODIFIED_BY = 5;
	public final static int COLUMN_ID_SUMMARY = 6;
	public final static int COLUMN_ID_DESCRIPTION = 7;
	public final static int COLUMN_ID_STATUS = 8;
	public final static int COLUMN_ID_RESOLUTION = 9;
	public final static int COLUMN_ID_ISSUE_TYPE = 10;
	public final static int COLUMN_ID_PRIORITY = 11;
	public final static int COLUMN_ID_PROJECT = 12;
	public final static int COLUMN_ID_BUILD_FOUND = 13;
	public final static int COLUMN_ID_ASSIGNEE = 14;
	
	
	// For entity
	private int issueId;
	private String createDate;
	private int createdBy;
	private String modifyDate;
	private int modifiedBy;
	private String summary;
	private String description;
	private int status;
	private int resolution;
	private int type;
	private int priority;
	private int project;
	private int buildFound;
	private int assignee;
	
	public Issue(int issueId, String createDate, int createdBy,
			String modifyDate, int modifiedBy, String summary,
			String description, int status, int resolution, int type,
			int priority, int project, int buildFound,
			int assignee) {
		this.issueId = issueId;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifiedBy = modifiedBy;
		this.summary = summary;
		this.description = description;
		this.status = status;
		this.resolution = resolution;
		this.type = type;
		this.priority = priority;
		this.project = project;
		this.buildFound = buildFound;
		this.assignee = assignee;
	}
	
	public Issue(){
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getProject() {
		return project;
	}

	public void setProject(int project) {
		this.project = project;
	}

	public int getBuildFound() {
		return buildFound;
	}

	public void setBuildFound(int buildFound) {
		this.buildFound = buildFound;
	}

	public int getAssignee() {
		return assignee;
	}

	public void setAssignee(int assignee) {
		this.assignee = assignee;
	}
	

	@Override
	public int compareTo(Issue two) {
		String dateOne = this.getCreateDate();
		String dateTwo = two.getCreateDate();
		
		DateFormat df = DateFormat.getDateInstance();
		
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(dateOne);
			d2 = df.parse(dateTwo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return d1.compareTo(d2);
	}
}
