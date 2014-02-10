package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a user
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 4249146829438242768L;
	
	public final static String NAME_PLACEHOLDER = "-"; 
	
	// For database
	public final static String TABLE_NAME = "users";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_FIRST_NAME = "firstName";
	public final static String COLUMN_NAME_LAST_NAME = "lastName";
	public final static String COLUMN_NAME_EMAIL = "email";
	public final static String COLUMN_NAME_ROLE = "role";
	public final static String COLUMN_NAME_PASSWORD = "password";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_FIRST_NAME = 2;
	public final static int COLUMN_ID_LAST_NAME = 3;
	public final static int COLUMN_ID_EMAIL = 4;
	public final static int COLUMN_ID_ROLE = 5;
	public final static int COLUMN_ID_PASSWORD = 6;
	
	// For entity
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private int roleId;
	private String password;
	
	public User(int userId, String firstName, String lastName, String email,
			int roleId, String password) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleId = roleId;
		this.password = password;
	}
	
	public User(){
		this.firstName = NAME_PLACEHOLDER;
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

	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
