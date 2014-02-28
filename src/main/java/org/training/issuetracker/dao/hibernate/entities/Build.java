package org.training.issuetracker.dao.hibernate.entities;

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
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private int buildId;
	
	@ManyToOne
    @JoinColumn(name="project")
	private Project project;
	
	@Column(name="version")
	private String version;
	
	
	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
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
