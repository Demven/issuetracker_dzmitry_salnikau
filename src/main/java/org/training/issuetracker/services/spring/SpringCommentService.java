package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.Comment;
import org.training.issuetracker.dao.interfaces.CommentDAO;
import org.training.issuetracker.services.CommentService;

@Service("commentService") 
@Repository 
@Transactional 
public class SpringCommentService implements CommentService{

	@Autowired
	private CommentDAO commentDAO;
	
	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}
	
	@Transactional
	@Override
	public List<Comment> getComments() {
		return commentDAO.getComments();
	}

	@Transactional
	@Override
	public List<Comment> getCommentsForIssue(int issueId) {
		return commentDAO.getCommentsForIssue(issueId);
	}

	@Transactional
	@Override
	public List<Comment> getCommentsForUser(int userId) {
		return commentDAO.getCommentsForUser(userId);
	}

	@Transactional
	@Override
	public Comment getCommentById(int commentId) {
		return commentDAO.getCommentById(commentId);
	}

	@Transactional
	@Override
	public boolean createComment(Comment comment) {
		return commentDAO.createComment(comment);
	}

	@Transactional
	@Override
	public boolean updateComment(Comment comment) {
		return commentDAO.updateComment(comment);
	}

	@Transactional
	@Override
	public boolean deleteComment(int commentId) {
		return commentDAO.deleteComment(commentId);
	}

}
