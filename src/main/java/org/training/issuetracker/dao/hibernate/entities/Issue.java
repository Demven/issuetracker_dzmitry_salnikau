package org.training.issuetracker.dao.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class, describing an issue, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name="issues")
public class Issue implements Serializable{

	private static final long serialVersionUID = 6068007459467779588L;

	public final static int MAX_SHOWN_NUMBER = 10;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private Integer issueId;
	
	@Column(name="createdate")
	private String createDate;
	
	@Column(name="createdby")
	private Integer createdBy;
	
	@Column(name="modifydate")
	private String modifyDate;
	
	@Column(name="modifiedby")
	private Integer modifiedBy;
	
	@Column(name="summary")
	private String summary;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="resolution")
	private Integer resolution;
	
	@Column(name="issuetype")
	private Integer type;
	
	@Column(name="priority")
	private Integer priority;
	
	@Column(name="project")
	private Integer project;
	
	@Column(name="buildfound")
	private Integer buildFound;
	
	@Column(name="assignee")
	private Integer assignee;
	

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
}
