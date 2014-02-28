package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.training.issuetracker.dao.hibernate.entities.Role;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.RoleDAO;

public class HibernateRoleDAO implements RoleDAO {

	private static final Logger logger = Logger.getLogger(HibernateRoleDAO.class);
	
	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Role> getRoles() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Role.class);
        List<Role> result = (List<Role>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Roles = " + result.size());
        for(Role role: result){
        	logger.warn("Id = " + role.getRoleId() + " Name=" + role.getName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Role getRoleById(int roleId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Role result = (Role) session.get(Role.class, roleId);
        session.getTransaction().commit();
        
        logger.warn("Id = " + result.getRoleId() + " Name=" + result.getName());
        return null;
	}

	@Override
	public boolean createRole(org.training.issuetracker.dao.transferObjects.Role role) {
		boolean success = false;
		
		Role newRole = new Role();
		
		newRole.setRoleId(role.getRoleId());
		newRole.setName(role.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newRole);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updateRole(org.training.issuetracker.dao.transferObjects.Role role) {
		boolean success = false;
		
		Role newRole = new Role();
		
		newRole.setRoleId(role.getRoleId());
		newRole.setName(role.getName());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newRole);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deleteRole(int roleId) {
		boolean success = false;
		
		Role deletingRole = new Role();
		deletingRole.setRoleId(roleId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingRole);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
