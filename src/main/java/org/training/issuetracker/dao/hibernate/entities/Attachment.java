package org.training.issuetracker.dao.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class, describing an issue's attachment, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 23.04.2014
 */
@Entity
@Table(name=Attachment.TABLE_NAME)
public class Attachment implements Serializable{

	private static final long serialVersionUID = -2362889607780245989L;

	public static final String TABLE_NAME = "attachments";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_ISSUE = "issue";
	public static final String COLUMN_USER = "user";
	public static final String COLUMN_TIME= "time";
	public static final String COLUMN_DATE= "date";
	public static final String COLUMN_REFERENCE= "reference";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer attachmentId;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_ISSUE)
	private Issue issue;
	
	@ManyToOne
    @JoinColumn(name=COLUMN_USER)
	private User user;
	
	@Column(name=COLUMN_TIME)
	private String time;
	
	@Column(name=COLUMN_DATE)
	private String date;
	
	@Column(name=COLUMN_REFERENCE)
	private String reference;

	
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
}
