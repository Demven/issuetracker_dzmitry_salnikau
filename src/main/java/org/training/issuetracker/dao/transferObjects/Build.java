package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a project's build
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Build implements java.io.Serializable {

	private static final long serialVersionUID = 1064428683909095808L;
	
	String buildId;
	String name;
	String projectId;
	String createdDate;
	
	public Build(String buildId, String name, String projectId,
			String createdDate) {
		this.buildId = buildId;
		this.name = name;
		this.projectId = projectId;
		this.createdDate = createdDate;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
