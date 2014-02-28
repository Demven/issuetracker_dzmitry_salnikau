package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;

public class HibernateResolutionDAO implements ResolutionDAO{

	private static final Logger logger = Logger.getLogger(HibernateResolutionDAO.class);
	
	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Resolution> getResolutions() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Resolution.class);
        List<Resolution> result = (List<Resolution>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Projects = " + result.size());
        for(Resolution resolution: result){
        	logger.warn("Id = " + resolution.getResolutionId() + " Name=" + resolution.getName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Resolution getResolutionById(int resolutionId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Resolution result = (Resolution) session.get(Resolution.class, resolutionId);
        session.getTransaction().commit();
        
        logger.warn("Id = " + result.getResolutionId() + " Name=" + result.getName());
        return null;
	}

	@Override
	public boolean createResolution(org.training.issuetracker.dao.transferObjects.Resolution resolution) {
		boolean success = false;
		
		Resolution newResolution = new Resolution();
		
		newResolution.setResolutionId(resolution.getResolutionId());
		newResolution.setName(resolution.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newResolution);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updateResolution(org.training.issuetracker.dao.transferObjects.Resolution resolution) {
		boolean success = false;
		
		Resolution newResolution = new Resolution();
		
		newResolution.setResolutionId(resolution.getResolutionId());
		newResolution.setName(resolution.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newResolution);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deleteResolution(int resolutionId) {
		boolean success = false;
		
		Resolution deletingResolution = new Resolution();
		deletingResolution.setResolutionId(resolutionId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingResolution);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
