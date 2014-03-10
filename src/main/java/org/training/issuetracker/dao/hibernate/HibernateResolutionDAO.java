package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;

public class HibernateResolutionDAO implements ResolutionDAO{

	private static final Logger logger = Logger.getLogger(HibernateResolutionDAO.class);
	private static final String TAG = HibernateResolutionDAO.class.getSimpleName();
	
	@Override
	public List<Resolution> getResolutions() {
		List<Resolution> resolutions = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Resolution.class);
	        criteria.addOrder(Order.asc(Resolution.COLUMN_ID));
	        resolutions = (List<Resolution>) criteria.list();
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all resolutions failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return resolutions;
	}

	@Override
	public Resolution getResolutionById(int resolutionId) {
		Resolution resolutionById = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        resolutionById = (Resolution) session.get(Resolution.class, resolutionId);
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting Resolution-object " + resolutionId +" failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return resolutionById;
	}

	@Override
	public boolean createResolution(Resolution resolution) {
		boolean success = false;
		
		Resolution newResolution = new Resolution();
		
		newResolution.setResolutionId(resolution.getResolutionId());
		newResolution.setName(resolution.getName());
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.save(newResolution);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Creating Resolution-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateResolution(Resolution resolution) {
		boolean success = false;
		
		Resolution newResolution = new Resolution();
		
		newResolution.setResolutionId(resolution.getResolutionId());
		newResolution.setName(resolution.getName());
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.update(newResolution);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Updating Resolution-object with id=" + resolution.getResolutionId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteResolution(int resolutionId) {
		boolean success = false;
		
		Resolution deletingResolution = new Resolution();
		deletingResolution.setResolutionId(resolutionId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.delete(deletingResolution);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Deleting Resolution-object with id=" + resolutionId + " failed!", e);
		}
		
		return success;
	}

}
