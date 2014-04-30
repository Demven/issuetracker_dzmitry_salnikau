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
import org.training.issuetracker.dao.entities.Attachment;
import org.training.issuetracker.dao.entities.Issue;
import org.training.issuetracker.dao.entities.User;
import org.training.issuetracker.dao.interfaces.AttachmentDAO;

@Repository("attachmentDAO") 
@Transactional 
public class HibernateAttachmentDAO implements AttachmentDAO{

	private static final Logger logger = Logger.getLogger(HibernateAttachmentDAO.class);
	private static final String TAG = HibernateAttachmentDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> getAttachments() {
		List<Attachment> attachments = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Attachment.class);
	        criteria.addOrder(Order.asc(Attachment.COLUMN_ID));
	        attachments = (List<Attachment>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all attachments failed!", e);
		    throw e;
		}
        
        return attachments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> getAttachmentsForIssue(int issueId) {
		List<Attachment> attachmentsForIssue = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Attachment.class);
	        criteria.addOrder(Order.asc(Attachment.COLUMN_ID));
	        
	        Issue issue = new Issue();
	        issue.setIssueId(issueId);
	        
	        attachmentsForIssue = (List<Attachment>) criteria.add(Restrictions.like(Attachment.COLUMN_ISSUE, issue)).list();
		} catch (Exception e) {
			logger.error(TAG + " Getting attachments for issue " + issueId + "failed!", e);
		    throw e;
		}
		
        return attachmentsForIssue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> getAttachmentsForUser(int userId) {
		List<Attachment> attachmentsForUser = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Attachment.class);
	        criteria.addOrder(Order.asc(Attachment.COLUMN_ID));
	        
	        User user = new User();
	        user.setUserId(userId);
	        
	        attachmentsForUser = (List<Attachment>) criteria.add(Restrictions.like(Attachment.COLUMN_USER, user)).list();
		} catch (Exception e) {
			logger.error(TAG + " Getting attachments for user " + userId + "failed!", e);
		    throw e;
		}
		
        return attachmentsForUser;
	}

	@Override
	public Attachment getAttachmentById(int attachmentId) {
		Attachment attachmentById = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
			attachmentById= (Attachment) session.get(Attachment.class, attachmentId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Attachment-object " + attachmentId + "failed!", e);
		    throw e;
		}
        
        return attachmentById;
	}

	@Override
	public boolean createAttachment(Attachment attachment) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(attachment);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Creating Attachment-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateAttachment(Attachment attachment) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(attachment);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Attachment-object with id=" + attachment.getAttachmentId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteAttachment(int attachmentId) {
		boolean success = false;
		
		Attachment deletingAttachment = new Attachment();
		deletingAttachment.setAttachmentId(attachmentId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingAttachment);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Attachment-object with id=" + attachmentId + " failed!", e);
		}
		
		return success;
	}
}
