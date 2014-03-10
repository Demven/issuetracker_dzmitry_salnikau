package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue's priority
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 * @deprecated
 */
public class Priority implements java.io.Serializable{

	private static final long serialVersionUID = 6647901390633791961L;

	// For database
	public final static String TABLE_NAME = "priorities";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_NAME = 2;
	
	// For entity
	private int priorityId;
	private String name;
	
	public Priority(int priorityId, String name) {
		this.priorityId = priorityId;
		this.name = name;
	}
	
	public Priority(){
	}

	public int getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
