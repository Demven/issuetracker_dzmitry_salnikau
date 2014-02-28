package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.TypeDAO;

public class HibernateTypeDAO implements TypeDAO{

	private static final Logger logger = Logger.getLogger(HibernateTypeDAO.class);
	
	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Type> getTypes() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Type.class);
        List<Type> result = (List<Type>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Types = " + result.size());
        for(Type type: result){
        	logger.warn("Id = " + type.getTypeId() + " Name=" + type.getName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Type getTypeById(int typeId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Type result = (Type) session.get(Type.class, typeId);
        session.getTransaction().commit();
        
        logger.warn("Id = " + result.getTypeId() + " Name=" + result.getName());
        return null;
	}

	@Override
	public boolean createType(org.training.issuetracker.dao.transferObjects.Type type) {
		boolean success = false;
		
		Type newType = new Type();
		
		newType.setTypeId(type.getTypeId());
		newType.setName(type.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newType);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updateType(org.training.issuetracker.dao.transferObjects.Type type) {
		boolean success = false;
		
		Type newType = new Type();
		
		newType.setTypeId(type.getTypeId());
		newType.setName(type.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newType);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deleteType(int typeId) {
		boolean success = false;
		
		Type deletingType = new Type();
		deletingType.setTypeId(typeId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingType);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
