package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.interfaces.StatusDAO;

@Repository("statusDAO") 
@Transactional 
public class HibernateStatusDAO implements StatusDAO{

	private static final Logger logger = Logger.getLogger(HibernateStatusDAO.class);
	private static final String TAG = HibernateStatusDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Status> getStatuses() {
		List<Status> statuses = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Status.class);
	        criteria.addOrder(Order.asc(Status.COLUMN_ID));
	        statuses = (List<Status>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all statuses failed!", e);
		    throw e;
		}

        return statuses;
	}

	@Override
	public Status getStatusById(int statusId) {
		Status status = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        status = (Status) session.get(Status.class, statusId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Status-object " + statusId + " failed!", e);
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
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(newStatus);
		    success = true;
		} catch(Exception e) {
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
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(newStatus);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Status-object with id=" + status.getStatusId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteStatus(int statusId) {
		boolean success = false;
		
		Status deletingStatus = new Status();
		deletingStatus.setStatusId(statusId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingStatus);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Status-object with id=" + statusId + " failed!", e);
		}
		
		return success;
	}

}
