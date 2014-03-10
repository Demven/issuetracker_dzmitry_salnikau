package org.training.issuetracker.dao.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class, describing an issue's priority, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Priority.TABLE_NAME)
public class Priority implements Serializable{

	private static final long serialVersionUID = -505140688731727808L;

	public static final String TABLE_NAME = "priorities";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer priorityId;
	
	@Column(name=COLUMN_NAME)
	private String name;

	
	public Integer getPriorityId() {
		return priorityId;
	}
	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}