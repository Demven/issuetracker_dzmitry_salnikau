package org.training.issuetracker.beans;

import org.training.issuetracker.dao.transferObjects.Role;

/**
 * Class, describing a user. Used in JSP.
 * @author Dzmitry Salnikau
 * @since 10.02.2014
 */
public class UserBean implements java.io.Serializable {

	private static final long serialVersionUID = 1294628951776461982L;
	
	// For entity
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
	private String password;
	
	public UserBean(){
	}
	
	public UserBean(int userId, String firstName, String lastName,
			String email, Role role, String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
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
