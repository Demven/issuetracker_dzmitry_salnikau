package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue's priority
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public class Priority implements java.io.Serializable{

	private static final long serialVersionUID = 6647901390633791961L;

	// For database
	public final static String TABLE_NAME = "priorities";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	
	// For entity
	private int statusId;
	private String name;
	
	public Priority(int statusId, String name) {
		this.statusId = statusId;
		this.name = name;
	}
	
	public Priority(){
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
