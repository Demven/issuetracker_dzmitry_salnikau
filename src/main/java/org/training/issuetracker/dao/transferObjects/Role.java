package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a user's role
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public class Role implements java.io.Serializable{

	private static final long serialVersionUID = 945706268109004177L;

	// For database
	public final static String TABLE_NAME = "roles";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_NAME = 2;
	
	// For entity
	private int roleId;
	private String name;
	
	public Role(int roleId, String name) {
		this.roleId = roleId;
		this.name = name;
	}

	public Role(){
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
