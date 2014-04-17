package org.training.issuetracker.dao.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class, describing a user's role, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=Role.TABLE_NAME)
public class Role implements Serializable {

	private static final long serialVersionUID = 6971497664543758197L;

	public static final String TABLE_NAME = "roles";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	
	public static final String ROLE_ADMIN = "Administrator";
	public static final String ROLE_USER = "User";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer roleId;
	
	@Column(name=COLUMN_NAME)
	private String name;

	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
