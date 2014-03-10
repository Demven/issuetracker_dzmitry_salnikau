package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue's status
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 * @deprecated
 */
public class Status implements java.io.Serializable{

	private static final long serialVersionUID = -8015754443156545020L;

	// For database
	public final static String TABLE_NAME = "statuses";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_NAME = 2;
	
	// For entity
	private int statusId;
	private String name;
	
	public Status(int statusId, String name) {
		this.statusId = statusId;
		this.name = name;
	}
	
	public Status(){
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
