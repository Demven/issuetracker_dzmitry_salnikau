package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Issue implements java.io.Serializable {

	private static final long serialVersionUID = 4377407675405841235L;
	
	private String issueId;
	private String createDate;
	private String createdBy;
	private String modifyDate;
	private String modifiedBy;
	private String summary;
	private String description;
	private String status;
	private String resolution;
	private String type;
	private String priority;
	private String projectId;
	private String buildFound;
	private String assignee;
	
	public Issue(String issueId, String createDate, String createdBy,
			String modifyDate, String modifiedBy, String summary,
			String description, String status, String resolution, String type,
			String priority, String projectId, String buildFound,
			String assignee) {
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
		this.projectId = projectId;
		this.buildFound = buildFound;
		this.assignee = assignee;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getBuildFound() {
		return buildFound;
	}

	public void setBuildFound(String buildFound) {
		this.buildFound = buildFound;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	
}
