package org.training.issuetracker.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class, describing an issue, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Issue.TABLE_NAME)
public class Issue implements Serializable{

	private static final long serialVersionUID = 6068007459467779588L;

	public final static int MAX_SHOWN_NUMBER = 10;
	
	public static final String TABLE_NAME = "issues";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CREATE_DATE = "createdate";
	public static final String COLUMN_CREATED_BY= "createdby";
	public static final String COLUMN_MODIFY_DATE= "modifydate";
	public static final String COLUMN_MODIFIED_BY= "modifiedby";
	public static final String COLUMN_SUMMARY= "summary";
	public static final String COLUMN_DESCRIPTION= "description";
	public static final String COLUMN_STATUS= "status";
	public static final String COLUMN_RESOLUTION= "resolution";
	public static final String COLUMN_TYPE= "issuetype";
	public static final String COLUMN_PRIORITY = "priority";
	public static final String COLUMN_PROJECT = "project";
	public static final String COLUMN_BUILD_FOUND = "buildfound";
	public static final String COLUMN_ASSIGNEE = "assignee";
	
	public static final String PROPERTY_TYPE = "type";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer issueId;
	
	@Column(name=COLUMN_CREATE_DATE)
	private String createDate;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_CREATED_BY)
	private User createdBy;
	
	@Column(name=COLUMN_MODIFY_DATE)
	private String modifyDate;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_MODIFIED_BY)
	private User modifiedBy;
	
	@Column(name=COLUMN_SUMMARY)
	private String summary;
	
	@Column(name=COLUMN_DESCRIPTION)
	private String description;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_STATUS)
	private Status status;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_RESOLUTION)
	private Resolution resolution;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_TYPE)
	private Type type;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_PRIORITY)
	private Priority priority;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_PROJECT)
	private Project project;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_BUILD_FOUND)
	private Build buildFound;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_ASSIGNEE)
	private User assignee;
	

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
	

	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	

	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	

	public User getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(User modifiedBy) {
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
	

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	

	public Resolution getResolution() {
		return resolution;
	}
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	

	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	

	public Build getBuildFound() {
		return buildFound;
	}
	public void setBuildFound(Build buildFound) {
		this.buildFound = buildFound;
	}

	
	public User getAssignee() {
		return assignee;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
}
