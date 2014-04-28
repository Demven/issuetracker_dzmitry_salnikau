package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.dao.hibernate.entities.Comment;

/**
 * Interface, providing access to comments and their data
 * @author Dzmitry Salnikau
 * @since 23.04.2014
 */
public interface CommentDAO {
	
	/**
	 * @return List of all comments for all issues
	 */
	public List<Comment> getComments();
	
	/**
	 * Returns List of all comments for a specified issue
	 * @param issueId - unique Id of an issue
	 * @return List<Comment> 
	 */
	public List<Comment> getCommentsForIssue(int issueId);
	
	/**
	 * Returns List of all comments for a specified user
	 * @param userId - unique Id of a user
	 * @return List<Comment> 
	 */
	public List<Comment> getCommentsForUser(int userId);
	
	/**
	 * Returns Comment object with stated commentId
	 * @param commentId
	 * @return Comment 
	 */
	public Comment getCommentById(int commentId);
	
	/**
	 * Adds new comment in a data storage
	 * @param Comment
	 * @return boolean - true, if it was successful
	 */
	public boolean createComment(Comment comment);
	
	/**
	 * Update the comment's data 
	 * @param Comment
	 * @return boolean - true, if it was successful
	 */
	public boolean updateComment(Comment comment);
	
	/**
	 * Delete comment from a data storage by the unique commentId
	 * @param commentId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteComment(int commentId);
}
