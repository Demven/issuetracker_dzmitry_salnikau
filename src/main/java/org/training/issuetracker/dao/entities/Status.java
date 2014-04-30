package org.training.issuetracker.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class, describing an issue's status, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Status.TABLE_NAME)
public class Status implements Serializable{

	private static final long serialVersionUID = -9186983276134215323L;

	public static final String TABLE_NAME = "statuses";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	
	public static final int INDEX_NEW = 1;
	public static final int INDEX_ASSIGNED = 2;
	public static final int INDEX_CLOSED = 5;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer statusId;
	
	@Column(name=COLUMN_NAME)
	private String name;

	
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

