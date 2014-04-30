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
import org.training.issuetracker.dao.entities.Role;
import org.training.issuetracker.dao.interfaces.RoleDAO;

@Repository("roleDAO") 
@Transactional 
public class HibernateRoleDAO implements RoleDAO {

	private static final Logger logger = Logger.getLogger(HibernateRoleDAO.class);
	private static final String TAG = HibernateRoleDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoles() {
		List<Role> roles = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(Role.class);
	        criteria.addOrder(Order.asc(Role.COLUMN_ID));
	        roles = (List<Role>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all roles failed!", e);
		    throw e;
		}
        
        return roles;
	}

	@Override
	public Role getRoleById(int roleId) {
		Role role = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        role = (Role) session.get(Role.class, roleId);
		} catch (Exception e) {
			logger.error(TAG + " Getting Role-object " + roleId + " failed!", e);
		    throw e;
		}
        
        return role;
	}

	@Override
	public boolean createRole(Role role) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(role);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Creating Role-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateRole(Role role) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(role);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Updating Role-object with id=" + role.getRoleId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteRole(int roleId) {
		boolean success = false;
		
		Role deletingRole = new Role();
		deletingRole.setRoleId(roleId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingRole);
		    success = true;
		} catch(Exception e) {
		    logger.error(TAG + " Deleting Role-object with id=" + roleId + " failed!", e);
		}
		
		return success;
	}

}
