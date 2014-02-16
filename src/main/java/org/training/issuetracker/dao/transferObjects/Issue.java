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
	private Integer issueId;
	private String createDate;
	private Integer createdBy;
	private String modifyDate;
	private Integer modifiedBy;
	private String summary;
	private String description;
	private Integer status;
	private Integer resolution;
	private Integer type;
	private Integer priority;
	private Integer project;
	private Integer buildFound;
	private Integer assignee;
	
	public Issue(Integer issueId, String createDate, Integer createdBy,
			String modifyDate, Integer modifiedBy, String summary,
			String description, Integer status, Integer resolution, Integer type,
			Integer priority, Integer project, Integer buildFound,
			Integer assignee) {
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

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getResolution() {
		return resolution;
	}

	public void setResolution(Integer resolution) {
		this.resolution = resolution;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public Integer getBuildFound() {
		return buildFound;
	}

	public void setBuildFound(Integer buildFound) {
		this.buildFound = buildFound;
	}

	public Integer getAssignee() {
		return assignee;
	}

	public void setAssignee(Integer assignee) {
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
