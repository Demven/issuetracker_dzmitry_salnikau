package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.StatusDAO;

public class HibernateStatusDAO implements StatusDAO{

	private static final Logger logger = Logger.getLogger(HibernateStatusDAO.class);
	
	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Status> getStatuses() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Status.class);
        List<Status> result = (List<Status>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Statuses = " + result.size());
        for(Status status: result){
        	logger.warn("Id = " + status.getStatusId() + " Name=" + status.getName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Status getStatusById(int statusId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Status result = (Status) session.get(Status.class, statusId);
        session.getTransaction().commit();
        
        logger.warn("Id = " + result.getStatusId() + " Name=" + result.getName());
        return null;
	}

	@Override
	public boolean createStatus(org.training.issuetracker.dao.transferObjects.Status status) {
		boolean success = false;
		
		Status newStatus = new Status();
		
		newStatus.setStatusId(status.getStatusId());
		newStatus.setName(status.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newStatus);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updateStatus(org.training.issuetracker.dao.transferObjects.Status status) {
		boolean success = false;
		
		Status newStatus = new Status();
		
		newStatus.setStatusId(status.getStatusId());
		newStatus.setName(status.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newStatus);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deleteStatus(int statusId) {
		boolean success = false;
		
		Status deletingStatus = new Status();
		deletingStatus.setStatusId(statusId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingStatus);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
