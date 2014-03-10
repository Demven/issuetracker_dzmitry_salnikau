package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.PriorityDAO;

public class HibernatePriorityDAO implements PriorityDAO {

	private static final Logger logger = Logger.getLogger(HibernatePriorityDAO.class);
	private static final String TAG = HibernatePriorityDAO.class.getSimpleName();
	
	@Override
	public List<Priority> getPriorities() {
		List<Priority> priorities = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
			Criteria criteria = session.createCriteria(Priority.class);
			criteria.addOrder(Order.asc(Priority.COLUMN_ID));
			priorities = (List<Priority>) criteria.list();
			
		    transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all priorities failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return priorities;
	}

	@Override
	public Priority getPriorityById(int priorityId) {
		Priority priority = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {
	        priority = (Priority) session.get(Priority.class, priorityId);
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting Priority by id failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
        return priority;
	}

	@Override
	public boolean createPriority(Priority priority) {
		boolean success = false;
		
		Priority newPriority = new Priority();
		
		newPriority.setPriorityId(priority.getPriorityId());
		newPriority.setName(priority.getName());
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {
		    session.save(newPriority);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Creating Priority-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updatePriority(Priority priority) {
		boolean success = false;
		
		Priority newPriority = new Priority();
		
		newPriority.setPriorityId(priority.getPriorityId());
		newPriority.setName(priority.getName());
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {
		    session.update(newPriority);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Updating Priority-object with id=" + priority.getPriorityId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deletePriority(int priorityId) {
		boolean success = false;
		
		Priority deletingPriority = new Priority();
		deletingPriority.setPriorityId(priorityId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {
		    session.delete(deletingPriority);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Deleting Priority-object with id=" + priorityId + " failed!", e);
		}
		
		return success;
	}

}
