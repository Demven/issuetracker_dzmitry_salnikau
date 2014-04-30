package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Issue;
import org.training.issuetracker.dao.entities.Priority;
import org.training.issuetracker.dao.entities.Status;
import org.training.issuetracker.dao.entities.Type;
import org.training.issuetracker.dao.entities.User;
import org.training.issuetracker.dao.interfaces.IssueDAO;

@Repository("issueDAO") 
@Transactional
public class HibernateIssueDAO implements IssueDAO{

	private static final Logger logger = Logger.getLogger(HibernateIssueDAO.class);
	private static final String TAG = HibernateIssueDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getIssues() {
		List<Issue> issues = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Issue.class);
	        criteria.addOrder(Order.asc(Issue.COLUMN_ID));
	        issues = (List<Issue>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all issues failed!", e);
		    throw e;
		}
		
        return issues;
	}

	@Override
	public Issue getIssueById(Integer issueId) {
		Issue issue = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        issue = (Issue) session.get(Issue.class, issueId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Issue-object " + issueId + "failed!", e);
		    throw e;
		}
        
        return issue;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getIssuesUsingFilter(String filter) {
		List<Issue> issues = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Issue.class);
	        
	        Disjunction disjunction = Restrictions.disjunction();
	        
	        // priority field
	        criteria.createAlias(Issue.COLUMN_PRIORITY, Issue.COLUMN_PRIORITY);
	        disjunction.add(Restrictions.like(Issue.COLUMN_PRIORITY + "." + Priority.COLUMN_NAME, "%" + filter + "%").ignoreCase());
	        
	        // assignee field
	        criteria.createAlias(Issue.COLUMN_ASSIGNEE, Issue.COLUMN_ASSIGNEE);
	        disjunction.add(Restrictions.like(Issue.COLUMN_ASSIGNEE + "." + User.COLUMN_FIRST_NAME, "%" + filter + "%").ignoreCase());
	        disjunction.add(Restrictions.like(Issue.COLUMN_ASSIGNEE + "." + User.COLUMN_LAST_NAME, "%" + filter + "%").ignoreCase());
	        
	        // type field
	        criteria.createAlias(Issue.PROPERTY_TYPE, Issue.COLUMN_TYPE);
	        disjunction.add(Restrictions.like(Issue.COLUMN_TYPE + "." + Type.COLUMN_NAME, "%" + filter + "%").ignoreCase());
	        
	        // status field
	        criteria.createAlias(Issue.COLUMN_STATUS, Issue.COLUMN_STATUS);
	        disjunction.add(Restrictions.like(Issue.COLUMN_STATUS + "." + Status.COLUMN_NAME, "%" + filter + "%").ignoreCase());
	        
	        // summary field
	        disjunction.add(Restrictions.like(Issue.COLUMN_SUMMARY, "%" + filter + "%").ignoreCase());
	        
	        criteria.addOrder(Order.asc(Issue.COLUMN_ID));
	        criteria.add(disjunction);
	        issues = (List<Issue>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting issues using filter " + filter + " failed!", e);
		    throw e;
		}
		
        return issues;
	}

	@Override
	public boolean createIssue(Issue issue) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(issue);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Creating Issue-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateIssue(Issue issue) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(issue);
		    success = true;
		} catch(Exception e) {
		    logger.warn(TAG + " Updating Issue-object with id=" + issue.getIssueId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteIssue(Integer issueId) {
		boolean success = false;
		
		Issue deletingIssue = new Issue();
		deletingIssue.setIssueId(issueId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingIssue);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Issue-object with id=" + issueId + " failed!", e);
		}
		
		return success;
	}

}
