package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a project's build
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Build implements java.io.Serializable {

	private static final long serialVersionUID = 1064428683909095808L;
	
	public final static String ELEMENT_ROOT = "build";
	public final static String SUBELEMENT_PROJECT = "project";
	public final static String SUBELEMENT_VERSION = "version";
	
	private int buildId;
	private int project;
	private String version;
	
	public Build(int buildId, int project,
			String version) {
		this.buildId = buildId;
		this.project = project;
		this.version = version;
	}

	public Build(){
	}
	
	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}

	
	public int getProject() {
		return project;
	}
	public void setProject(int project) {
		this.project = project;
	}

	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}


}
