package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.StatusDAO;

public class HibernateStatusDAO implements StatusDAO{

	private static final Logger logger = Logger.getLogger(HibernateStatusDAO.class);
	private static final String TAG = HibernateStatusDAO.class.getSimpleName();
	
	@Override
	public List<Status> getStatuses() {
		List<Status> statuses = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Status.class);
	        criteria.addOrder(Order.asc(Status.COLUMN_ID));
	        statuses = (List<Status>) criteria.list();
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all statuses failed!", e);
		    transaction.rollback();
		    throw e;
		}

        return statuses;
	}

	@Override
	public Status getStatusById(int statusId) {
		Status status = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        status = (Status) session.get(Status.class, statusId);
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting Status-object " + statusId + " failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return status;
	}

	@Override
	public boolean createStatus(Status status) {
		boolean success = false;
		
		Status newStatus = new Status();
		
		newStatus.setStatusId(status.getStatusId());
		newStatus.setName(status.getName());
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.save(newStatus);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Creating Status-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateStatus(Status status) {
		boolean success = false;
		
		Status newStatus = new Status();
		
		newStatus.setStatusId(status.getStatusId());
		newStatus.setName(status.getName());
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.update(newStatus);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Updating Status-object with id=" + status.getStatusId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteStatus(int statusId) {
		boolean success = false;
		
		Status deletingStatus = new Status();
		deletingStatus.setStatusId(statusId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.delete(deletingStatus);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Deleting Status-object with id=" + statusId + " failed!", e);
		}
		
		return success;
	}

}
