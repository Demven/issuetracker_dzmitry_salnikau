package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue's type
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 * @deprecated
 */
public class Type implements java.io.Serializable{

	private static final long serialVersionUID = 945706268109004177L;

	// For database
	public final static String TABLE_NAME = "types";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_NAME = 2;
	
	// For entity
	private int typeId;
	private String name;
	
	public Type(int typeId, String name) {
		this.typeId = typeId;
		this.name = name;
	}

	public Type(){
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
