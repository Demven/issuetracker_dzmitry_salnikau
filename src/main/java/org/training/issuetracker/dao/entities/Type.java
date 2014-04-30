package org.training.issuetracker.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class, describing an issue's type, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Type.TABLE_NAME)
public class Type implements Serializable{

	private static final long serialVersionUID = -7927715264942954726L;

	public static final String TABLE_NAME = "types";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer typeId;
	
	@Column(name=COLUMN_NAME)
	private String name;
	
	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
