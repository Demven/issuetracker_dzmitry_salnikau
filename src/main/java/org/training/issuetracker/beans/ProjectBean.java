package org.training.issuetracker.beans;

import org.training.issuetracker.dao.hibernate.entities.User;

/**
 * Class, describing a project. Used in JSP.
 * @author Dzmitry Salnikau
 * @since 08.02.2014
 */
public class ProjectBean implements java.io.Serializable {

	private static final long serialVersionUID = 2229997641918352565L;

	public final static int MAX_SHOWN_NUMBER = 10;
	public final static int MAX_SHOWN_DESCRIPTION_LENGTH = 100;
	public final static String PLACEHOLDER = "...";
	
	// For entity
	private int projectId;
	private String name;
	private String description;
	private User manager;
	
	public ProjectBean(int projectId, String name, String description, User manager) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.manager = manager;
	}
	
	public ProjectBean(){
	}
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
}