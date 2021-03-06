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
import org.training.issuetracker.dao.entities.Type;
import org.training.issuetracker.dao.interfaces.TypeDAO;

@Repository("typeDAO") 
@Transactional 
public class HibernateTypeDAO implements TypeDAO{

	private static final Logger logger = Logger.getLogger(HibernateTypeDAO.class);
	private static final String TAG = HibernateTypeDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Type> getTypes() {
		List<Type> types = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Type.class);
	        criteria.addOrder(Order.asc(Type.COLUMN_ID));
	        types = (List<Type>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all types failed!", e);
		    throw e;
		}
		
        return types;
	}

	@Override
	public Type getTypeById(int typeId) {
		Type type = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        type = (Type) session.get(Type.class, typeId);
		} catch (Exception e) {
			logger.error(TAG + " Getting all types failed!", e);
		    throw e;
		}
		
        return type;
	}

	@Override
	public boolean createType(Type type) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(type);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Creating Type-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateType(Type type) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(type);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Type-object with id=" + type.getTypeId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteType(int typeId) {
		boolean success = false;
		
		Type deletingType = new Type();
		deletingType.setTypeId(typeId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingType);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Type-object with id=" + typeId + " failed!", e);
		}
		
		return success;
	}

}
