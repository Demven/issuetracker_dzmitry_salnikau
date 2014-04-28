package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.UserDAO;

@Repository("userDAO") 
@Transactional 
public class HibernateUserDAO implements UserDAO{

	private static final Logger logger = Logger.getLogger(HibernateUserDAO.class);
	private static final String TAG = HibernateUserDAO.class.getSimpleName();
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		List<User> users = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(User.class);
	        criteria.addOrder(Order.asc(User.COLUMN_ID));
	        users = (List<User>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting all users failed!", e);
		    throw e;
		}

        return users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersWithFilter(String filter) {
		List<User> users = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(User.class);
	        
	        Disjunction disjunction = Restrictions.disjunction();
	       
	        disjunction.add(Restrictions.like(User.COLUMN_FIRST_NAME, "%" + filter + "%").ignoreCase());
	        disjunction.add(Restrictions.like(User.COLUMN_LAST_NAME, "%" + filter + "%").ignoreCase());
	        
	        criteria.addOrder(Order.asc(User.COLUMN_ID));
	        criteria.add(disjunction);
	        users = (List<User>) criteria.list();
		} catch (Exception e) {
			logger.error(TAG + " Getting users using filter " + filter + " failed!", e);
		    throw e;
		}
		
        return users;
	}

	@Override
	public boolean checkAuth(String email, String password) {
		boolean isUserExists = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(User.class);
	
	        @SuppressWarnings("unchecked")
			List<User> result = (List<User>) criteria
	        		.add(Restrictions.like("email", email))
	        		.add(Restrictions.like("password", password))
	        		.list();
	        if(result != null && result.size() > 0){
	        	User user = result.get(0);
	        	if(user != null){
	            	isUserExists = true;
	            }
	        }
		} catch (Exception e) {
			logger.error(TAG + " Checking authorization for email=" + email + " and password=" + password +" failed!", e);
		    throw e;
		}
           
        return isUserExists;
	}

	@Override
	public boolean checkUserEmail(String email) {
		boolean isEmailExists = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(User.class);
	
	        @SuppressWarnings("unchecked")
			List<User> result = (List<User>) criteria
	        		.add(Restrictions.like("email", email))
	        		.list();
	        User user = null;
	        if(result != null && result.size() > 0){
	        	user = result.get(0);
	        	if(user != null){
	            	isEmailExists = true;
	            }
	        }
		} catch (Exception e) {
			logger.error(TAG + " Checking user's email=" + email + " failed!", e);
		    throw e;
		}
           
        return isEmailExists;
	}

	@Override
	public Integer getUserIdByName(String firstName, String lastName) {
		Integer userId = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(User.class);
	
	        @SuppressWarnings("unchecked")
			List<User> result = (List<User>) criteria
	        		.add(Restrictions.like("firstName", firstName))
	        		.add(Restrictions.like("lastName", lastName))
	        		.list();
	        User user = result.get(0);
	        if(user != null){
	        	userId = user.getUserId();
	        }
		} catch (Exception e) {
			logger.error(TAG + " Getting userId by name= " + firstName + " " + lastName + " failed", e);
		    throw e;
		}
           
        return userId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByEmail(String email) {
		User user = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
	        Criteria criteria = session.createCriteria(User.class);
	
	        List<User> result = (List<User>) criteria.add(Restrictions.like("email", email)).list();
	        user = result.get(0);
		} catch (Exception e) {
			logger.error(TAG + " Getting User-object by email= " + email + " failed", e);
		    throw e;
		}
		
        return user;
	}

	@Override
	public User getUserById(Integer userId) {
		User user = null;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
			user = (User) session.get(User.class, userId);
		} catch (Exception e) {
			logger.error(TAG + " Getting User-object by id= " + userId + " failed", e);
		    throw e;
		}
           
        return user;
	}

	@Override
	public boolean createUser(User user) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.save(user);
		    success = true;
		} catch (Exception e) {
			logger.error(TAG + " Creating User-object failed!", e);
		    throw e;
		}
		
		return success;
	}

	@Override
	public boolean updateUser(User user) {
		boolean success = false;
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.update(user);
		    success = true;
		} catch (Exception e) {
			logger.error(TAG + " Updating User-object " +  user.getUserId() + " failed!", e);
		    throw e;
		}
		
		return success;
	}

	@Override
	public boolean deleteUser(int userId) {
		boolean success = false;
		
		User deletingUser = new User();
		deletingUser.setUserId(userId);
		
		final Session session = sessionFactory.getCurrentSession();
		try {
		    session.delete(deletingUser);
		    success = true;
		} catch (Exception e) {
			logger.error(TAG + " Deleting User-object " + userId + " failed!", e);
		    throw e;
		}
		
		return success;
	}

}
