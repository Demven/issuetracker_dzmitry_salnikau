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
import org.training.issuetracker.dao.entities.Priority;
import org.training.issuetracker.dao.interfaces.PriorityDAO;

@Repository("priorityDAO") 
@Transactional 
public class HibernatePriorityDAO implements PriorityDAO {

	private static final Logger logger = Logger.getLogger(HibernatePriorityDAO.class);
	private static final String TAG = HibernatePriorityDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Priority> getPriorities() {
		List<Priority> priorities = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Priority.class);
			criteria.addOrder(Order.asc(Priority.COLUMN_ID));
			priorities = (List<Priority>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all priorities failed!", e);
		    throw e;
		}
        
        return priorities;
	}

	@Override
	public Priority getPriorityById(int priorityId) {
		Priority priority = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        priority = (Priority) session.get(Priority.class, priorityId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Priority by id failed!", e);
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
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(newPriority);
		    success = true;
		} catch(Exception e) {
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
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(newPriority);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Priority-object with id=" + priority.getPriorityId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deletePriority(int priorityId) {
		boolean success = false;
		
		Priority deletingPriority = new Priority();
		deletingPriority.setPriorityId(priorityId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingPriority);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Priority-object with id=" + priorityId + " failed!", e);
		}
		
		return success;
	}

}
