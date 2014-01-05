package org.training.issuetracker.dao.transferObjects;

/**
 * Class, describing an issue
 * @author Dzmitry Salnikau
 * @since 02.01.2014
 */
public class Issue implements java.io.Serializable {

	private static final long serialVersionUID = 4377407675405841235L;
	
	public final static String ELEMENT_ROOT = "issue";
	public final static String SUBELEMENT_CREATE_DATE = "createdate";
	public final static String SUBELEMENT_CREATED_BY = "createdby";
	public final static String SUBELEMENT_MODIFY_DATE = "modifydate";
	public final static String SUBELEMENT_MODIFIED_BY = "modifiedby";
	public final static String SUBELEMENT_SUMMARY = "summary";
	public final static String SUBELEMENT_DESCRIPTION = "description";
	public final static String SUBELEMENT_STATUS = "status";
	public final static String SUBELEMENT_RESOLUTION = "resolution";
	public final static String SUBELEMENT_TYPE = "type";
	public final static String SUBELEMENT_PRIORITY = "priority";
	public final static String SUBELEMENT_PROJECT_ID = "projectid";
	public final static String SUBELEMENT_BUILD_FOUND = "buildfound";
	public final static String SUBELEMENT_ASSIGNEE = "assignee";
	
	private int issueId;
	private String createDate;
	private int createdBy;
	private String modifyDate;
	private int modifiedBy;
	private String summary;
	private String description;
	private Status status;
	private Resolution resolution;
	private Type type;
	private Priority priority;
	private int projectId;
	private int buildFound;
	private int assignee;
	
	public Issue(int issueId, String createDate, int createdBy,
			String modifyDate, int modifiedBy, String summary,
			String description, Status status, Resolution resolution, Type type,
			Priority priority, int projectId, int buildFound,
			int assignee) {
		this.issueId = issueId;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifiedBy = modifiedBy;
		this.summary = summary;
		this.description = description;
		this.status = status;
		this.resolution = resolution;
		this.type = type;
		this.priority = priority;
		this.projectId = projectId;
		this.buildFound = buildFound;
		this.assignee = assignee;
	}
	
	public Issue(){
	}
	
	/**
	 * Enumeration of issue's statuses
	 * @author Dzmitry Salnikau
	 * @since 05.01.2014
	 */
	public static enum Status {

	    NEW("new"),
	    ASSIGNED("assigned"),
	    IN_PROGRESS("in_progress"),
	    RESOLVED("resolved"),
	    CLOSED("closed"),
	    REOPENED("reopened");
	    
	    private final String value;

	    Status(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static Status fromValue(String v) {
	    	Status status = null;
	        for (Status s: Status.values()) {
	            if (s.value.equals(v)) {
	            	status = s;
	            }
	        }
	        return status;
	    }
	}
	
	/**
	 * Enumeration of issue's resolutions
	 * @author Dzmitry Salnikau
	 * @since 05.01.2014
	 */
	public static enum Resolution {

	    FIXED("fixed"),
	    INVALID("invalid"),
	    WONTFIX("wontfix"),
	    WORKSFORME("worksforme");
	    
	    private final String value;

	    Resolution(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static Resolution fromValue(String v) {
	    	Resolution resolution = null;
	        for (Resolution r: Resolution.values()) {
	            if (r.value.equals(v)) {
	            	resolution = r;
	            }
	        }
	        return resolution;
	    }
	}
	
	/**
	 * Enumeration of issue's types
	 * @author Dzmitry Salnikau
	 * @since 05.01.2014
	 */
	public static enum Type {

	    COSMETIC("cosmetic"),
	    BUG("bug"),
	    FEATURE("feature"),
	    PERFORMANCE("performance");
	    
	    private final String value;

	    Type(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static Type fromValue(String v) {
	    	Type type = null;
	        for (Type t: Type.values()) {
	            if (t.value.equals(v)) {
	            	type = t;
	            }
	        }
	        return type;
	    }
	}
	
	/**
	 * Enumeration of issue's priorities
	 * @author Dzmitry Salnikau
	 * @since 05.01.2014
	 */
	public static enum Priority {

	    CRITICAL("critical"),
	    MAJOR("major"),
	    IMPORTANT("important"),
	    MINOR("minor");
	    
	    private final String value;

	    Priority(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static Priority fromValue(String v) {
	    	Priority priority = null;
	        for (Priority p: Priority.values()) {
	            if (p.value.equals(v)) {
	            	priority = p;
	            }
	        }
	        return priority;
	    }
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getBuildFound() {
		return buildFound;
	}

	public void setBuildFound(int buildFound) {
		this.buildFound = buildFound;
	}

	public int getAssignee() {
		return assignee;
	}

	public void setAssignee(int assignee) {
		this.assignee = assignee;
	}
	
	
}
