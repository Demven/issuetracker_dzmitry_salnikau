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
import org.training.issuetracker.dao.entities.Resolution;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;

@Repository("resolutionDAO") 
@Transactional 
public class HibernateResolutionDAO implements ResolutionDAO{

	private static final Logger logger = Logger.getLogger(HibernateResolutionDAO.class);
	private static final String TAG = HibernateResolutionDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Resolution> getResolutions() {
		List<Resolution> resolutions = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Resolution.class);
	        criteria.addOrder(Order.asc(Resolution.COLUMN_ID));
	        resolutions = (List<Resolution>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all resolutions failed!", e);
		    throw e;
		}
        
        return resolutions;
	}

	@Override
	public Resolution getResolutionById(int resolutionId) {
		Resolution resolutionById = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        resolutionById = (Resolution) session.get(Resolution.class, resolutionId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Resolution-object " + resolutionId +" failed!", e);
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
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(newResolution);
		    success = true;
		} catch(Exception e) {
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
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(newResolution);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Resolution-object with id=" + resolution.getResolutionId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteResolution(int resolutionId) {
		boolean success = false;
		
		Resolution deletingResolution = new Resolution();
		deletingResolution.setResolutionId(resolutionId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingResolution);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Resolution-object with id=" + resolutionId + " failed!", e);
		}
		
		return success;
	}

}
