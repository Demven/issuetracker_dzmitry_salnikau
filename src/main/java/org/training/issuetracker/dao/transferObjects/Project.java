package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing a project
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Project implements java.io.Serializable {

	private static final long serialVersionUID = -6718949855046807801L;
	
	// For database
	public final static String TABLE_NAME = "projects";
	
	public final static String COLUMN_NAME_ID = "id";
	public final static String COLUMN_NAME_NAME = "name";
	public final static String COLUMN_NAME_DESCRIPTION = "description";
	public final static String COLUMN_NAME_MANAGER = "manager";
	
	public final static int COLUMN_ID_ID = 1;
	public final static int COLUMN_ID_NAME = 2;
	public final static int COLUMN_ID_DESCRIPTION = 3;
	public final static int COLUMN_ID_MANAGER = 4;
	
	
	// For entity
	private int projectId;
	private String name;
	private String description;
	private int manager;
	
	public Project(int projectId, String name, String description, int manager) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.manager = manager;
	}
	
	public Project(){
	}
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public int getManager() {
		return manager;
	}
	public void setManager(int manager) {
		this.manager = manager;
	}
}
