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
 * Class, describing a project's build, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Build.TABLE_NAME)
public class Build implements Serializable{

	private static final long serialVersionUID = 8515875636655677413L;

	public static final String TABLE_NAME = "builds";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROJECT= "project";
	public static final String COLUMN_VERSION= "version";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer buildId;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_PROJECT)
	private Project project;
	
	@Column(name=COLUMN_VERSION)
	private String version;
	
	
	public Integer getBuildId() {
		return buildId;
	}
	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}

	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
