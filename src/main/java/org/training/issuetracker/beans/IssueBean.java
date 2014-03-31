package org.training.issuetracker.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.dao.hibernate.entities.User;

/**
 * Class, describing an issue. Used in JSP.
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public class IssueBean implements java.io.Serializable, Comparable<Issue> {

	private static final long serialVersionUID = 4377407675405841235L;
	
	public final static int MAX_SHOWN_NUMBER = 10;
	
	// For entity
	private int issueId;
	private String createDate;
	private User createdBy;
	private String modifyDate;
	private User modifiedBy;
	private String summary;
	private String description;
	private Status status;
	private Resolution resolution;
	private Type type;
	private Priority priority;
	private Project project;
	private Build buildFound;
	private User assignee;
	
	public IssueBean(){
	}
	
	public IssueBean(int issueId, String createDate, User createdBy,
			String modifyDate, User modifiedBy, String summary,
			String description, Status status, Resolution resolution, Type type,
			Priority priority, Project project, Build buildFound,
			User assignee) {
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
