package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a user
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 4249146829438242768L;
	
	String userId;
	String firstName;
	String lastName;
	String email;
	String role;
	String password;
	
	public User(String userId, String firstName, String lastName, String email,
			String role, String password) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
