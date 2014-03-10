package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a project's build
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 * @deprecated
 */
public class Build implements java.io.Serializable {

	private static final long serialVersionUID = 1064428683909095808L;
	
	// For database
	public final static String TABLE_NAME = "builds";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_PROJECT = "project";
	public final static String COLUMN_NAME_VERSION = "version";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_PROJECT = 2;
	public final static int COLUMN_ID_VERSION = 3;
	
	// For entity
	private Integer buildId;
	private Integer project;
	private String version;
	
	public Build(Integer buildId, Integer project,
			String version) {
		this.buildId = buildId;
		this.project = project;
		this.version = version;
	}

	public Build(){
	}
	
	public Integer getBuildId() {
		return buildId;
	}
	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}

	
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}

	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}


}
