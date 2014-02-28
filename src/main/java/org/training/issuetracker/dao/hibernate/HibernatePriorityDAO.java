package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.training.issuetracker.dao.hibernate.entities.Priority;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.PriorityDAO;

public class HibernatePriorityDAO implements PriorityDAO {

	private static final Logger logger = Logger.getLogger(HibernatePriorityDAO.class);
	
	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Priority> getPriorities() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Priority.class);
        List<Priority> result = (List<Priority>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Projects = " + result.size());
        for(Priority priority: result){
        	logger.warn("Id = " + priority.getPriorityId() + " Name=" + priority.getName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Priority getPriorityById(int priorityId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Priority result = (Priority) session.get(Priority.class, priorityId);
        session.getTransaction().commit();
        
        logger.warn("Id = " + result.getPriorityId() + " Name=" + result.getName());
        return null;
	}

	@Override
	public boolean createPriority(org.training.issuetracker.dao.transferObjects.Priority priority) {
		boolean success = false;
		
		Priority newPriority = new Priority();
		
		newPriority.setPriorityId(priority.getPriorityId());
		newPriority.setName(priority.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newPriority);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updatePriority(org.training.issuetracker.dao.transferObjects.Priority priority) {
		boolean success = false;
		
		Priority newPriority = new Priority();
		
		newPriority.setPriorityId(priority.getPriorityId());
		newPriority.setName(priority.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newPriority);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deletePriority(int priorityId) {
		boolean success = false;
		
		Priority deletingPriority = new Priority();
		deletingPriority.setPriorityId(priorityId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingPriority);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
