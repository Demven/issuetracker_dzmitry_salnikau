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
 * Class, describing a project, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Project.TABLE_NAME)
public class Project implements Serializable{

	private static final long serialVersionUID = 6840939637387816377L;

	public static final String TABLE_NAME = "projects";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_MANAGER = "manager";
	
	public final static int MAX_SHOWN_NUMBER = 10;
	public final static int MAX_SHOWN_DESCRIPTION_LENGTH = 100;
	public final static String PLACEHOLDER = "...";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer projectId;
	
	@Column(name=COLUMN_NAME)
	private String name;
	
	@Column(name=COLUMN_DESCRIPTION)
	private String description;
	
	@ManyToOne
	@JoinColumn(name=COLUMN_MANAGER)
	private User manager;
	
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
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
