package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue's resolution
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public class Resolution implements java.io.Serializable{

	private static final long serialVersionUID = 6972916862054479476L;

	// For database
	public final static String TABLE_NAME = "resolutions";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_NAME = 2;
	
	// For entity
	private int resolutionId;
	private String name;
	
	public Resolution(int resolutionId, String name) {
		this.resolutionId = resolutionId;
		this.name = name;
	}

	public Resolution(){
	}

	public int getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(int resolutionId) {
		this.resolutionId = resolutionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
