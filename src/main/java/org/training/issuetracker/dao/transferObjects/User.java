package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a user
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 4249146829438242768L;
	
	public final static String ELEMENT_ROOT = "user";
	public final static String SUBELEMENT_FIRST_NAME = "firstname";
	public final static String SUBELEMENT_LAST_NAME = "lastname";
	public final static String SUBELEMENT_EMAIL = "email";
	public final static String SUBELEMENT_ROLE = "role";
	public final static String SUBELEMENT_PASSWORD = "password";
	
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
	private String password;
	
	public User(int userId, String firstName, String lastName, String email,
			Role role, String password) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
	}
	
	public User(){
	}
	
	/**
	 * Enumeration of user's roles
	 * @author Dzmitry Salnikau
	 * @since 04.01.2014
	 */
	public static enum Role {

	    ADMINISTRATOR("administrator"),
	    USER("user");
	    
	    private final String value;

	    Role(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static Role fromValue(String v) {
	    	Role role = null;
	        for (Role r: Role.values()) {
	            if (r.value.equals(v)) {
	                role = r;
	            }
	        }
	        return role;
	    }
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
