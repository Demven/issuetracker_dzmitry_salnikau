package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.Comment;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.CommentDAO;

@Repository("commentDAO") 
@Transactional 
public class HibernateCommentDAO implements CommentDAO{

	private static final Logger logger = Logger.getLogger(HibernateCommentDAO.class);
	private static final String TAG = HibernateCommentDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments() {
		List<Comment> comments = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Comment.class);
	        criteria.addOrder(Order.asc(Comment.COLUMN_ID));
	        comments = (List<Comment>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all comments failed!", e);
		    throw e;
		}
        
        return comments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentsForIssue(int issueId) {
		List<Comment> commentsForIssue = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Comment.class);
	        criteria.addOrder(Order.asc(Comment.COLUMN_ID));
	        
	        Issue issue = new Issue();
	        issue.setIssueId(issueId);
	        
	        commentsForIssue = (List<Comment>) criteria.add(Restrictions.like(Comment.COLUMN_ISSUE, issue)).list();
		} catch (Exception e) {
			logger.error(TAG + " Getting comments for issue " + issueId + "failed!", e);
		    throw e;
		}
		
        return commentsForIssue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentsForUser(int userId) {
		List<Comment> commentsForUser = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Comment.class);
	        criteria.addOrder(Order.asc(Comment.COLUMN_ID));
	        
	        User user = new User();
	        user.setUserId(userId);
	        
	        commentsForUser = (List<Comment>) criteria.add(Restrictions.like(Comment.COLUMN_USER, user)).list();
		} catch (Exception e) {
			logger.error(TAG + " Getting comments for user " + userId + "failed!", e);
		    throw e;
		}
		
        return commentsForUser;
	}

	@Override
	public Comment getCommentById(int commentId) {
		Comment commentById = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
			commentById= (Comment) session.get(Comment.class, commentId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Comment-object " + commentId + "failed!", e);
		    throw e;
		}
        
        return commentById;
	}

	@Override
	public boolean createComment(Comment comment) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(comment);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Creating Comment-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateComment(Comment comment) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(comment);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Comment-object with id=" + comment.getCommentId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteComment(int commentId) {
		boolean success = false;
		
		Comment deletingComment = new Comment();
		deletingComment.setCommentId(commentId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingComment);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Comment-object with id=" + commentId + " failed!", e);
		}
		
		return success;
	}

}
