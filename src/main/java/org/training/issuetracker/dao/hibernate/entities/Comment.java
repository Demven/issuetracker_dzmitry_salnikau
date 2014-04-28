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
 * Class, describing an issue's comment, that used in Hibernate
 * @author Dzmitry Salnikau
 * @since 23.04.2014
 */
@Entity
@Table(name=Comment.TABLE_NAME)
public class Comment implements Serializable{

	private static final long serialVersionUID = -803844871428587049L;

	public static final String TABLE_NAME = "comments";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_ISSUE = "issue";
	public static final String COLUMN_USER = "user";
	public static final String COLUMN_TIME= "time";
	public static final String COLUMN_DATE= "date";
	public static final String COLUMN_TEXT= "text";
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=COLUMN_ID)
	private Integer commentId;
	
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
	
	@Column(name=COLUMN_TEXT)
	private String text;

	
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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

	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
