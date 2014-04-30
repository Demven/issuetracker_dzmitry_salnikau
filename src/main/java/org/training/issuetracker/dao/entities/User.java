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
 * Class, describing a user, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 24.02.2014
 */
@Entity
@Table(name=User.TABLE_NAME)
public class User implements Serializable{

	private static final long serialVersionUID = 7272403135269990656L;
	
	public static final String TABLE_NAME = "users";
	public final static String NAME_PLACEHOLDER = "-"; 
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_FIRST_NAME = "firstName";
	public static final String COLUMN_LAST_NAME = "lastName";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_ROLE = "role";
	public static final String COLUMN_PASSWORD = "password";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer userId;
	
	@Column(name=COLUMN_FIRST_NAME)
	private String firstName;
	
	@Column(name=COLUMN_LAST_NAME)
	private String lastName;
	
	@Column(name=COLUMN_EMAIL)
	private String email;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_ROLE)
	private Role role;
	
	@Column(name=COLUMN_PASSWORD)
	private String password;


	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public String getFirstName() {
		if(firstName == null || firstName.equals("")){
			firstName = NAME_PLACEHOLDER;
		}
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
