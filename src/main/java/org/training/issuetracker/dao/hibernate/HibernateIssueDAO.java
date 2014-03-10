package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.IssueDAO;

public class HibernateIssueDAO implements IssueDAO{

	private static final Logger logger = Logger.getLogger(HibernateIssueDAO.class);
	private static final String TAG = HibernateIssueDAO.class.getSimpleName();
	
	@Override
	public List<Issue> getIssues() {
		List<Issue> issues = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Issue.class);
	        criteria.addOrder(Order.asc(Issue.COLUMN_ID));
	        issues = (List<Issue>) criteria.list();
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all issues failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
        return issues;
	}

	@Override
	public Issue getIssueById(Integer issueId) {
		Issue issue = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        issue = (Issue) session.get(Issue.class, issueId);
	        session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting Issue-object " + issueId + "failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return issue;
	}

	@Override
	public boolean createIssue(Issue issue) {
		boolean success = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.save(issue);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Creating Issue-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateIssue(Issue issue) {
		boolean success = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.update(issue);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.warn(TAG + " Updating Issue-object with id=" + issue.getIssueId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteIssue(Integer issueId) {
		boolean success = false;
		
		Issue deletingIssue = new Issue();
		deletingIssue.setIssueId(issueId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.delete(deletingIssue);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Deleting Issue-object with id=" + issueId + " failed!", e);
		}
		
		return success;
	}

}
