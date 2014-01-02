package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a project
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Project implements java.io.Serializable {

	private static final long serialVersionUID = -6718949855046807801L;
	
	String projectId;
	String name;
	String description;
	String buildId;
	String manager;
	
	public Project(String projectId, String name, String description,
			String buildId, String manager) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.buildId = buildId;
		this.manager = manager;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
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

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
}
