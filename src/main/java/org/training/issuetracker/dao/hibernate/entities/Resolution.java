package org.training.issuetracker.dao.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class, describing an issue's resolution, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Resolution.TABLE_NAME)
public class Resolution implements Serializable {

	private static final long serialVersionUID = 8094411727630078270L;

	public static final String TABLE_NAME = "resolutions";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private int resolutionId;
	
	@Column(name="name")
	private String name;

	
	public int getResolutionId() {
		return resolutionId;
	}
	public void setResolutionId(int resolutionId) {
		this.resolutionId = resolutionId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
