package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.UserDAO;

public class HibernateUserDAO implements UserDAO{

	private static final Logger logger = Logger.getLogger(HibernateUserDAO.class);
	private static final String TAG = HibernateUserDAO.class.getSimpleName();
	
	@Override
	public List<User> getUsers() {
		List<User> users = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(User.class);
	        criteria.addOrder(Order.asc(User.COLUMN_ID));
	        users = (List<User>) criteria.list();
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all users failed!", e);
		    transaction.rollback();
		    throw e;
		}

        return users;
	}

	@Override
	public boolean checkAuth(String email, String password) {
		boolean isUserExists = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
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
	        
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Checking authorization for email=" + email + " and password=" + password +" failed!", e);
		    transaction.rollback();
		    throw e;
		}
           
        return isUserExists;
	}

	@Override
	public boolean checkUserEmail(String email) {
		boolean isEmailExists = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
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
	        
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Checking user's email=" + email + " failed!", e);
		    transaction.rollback();
		    throw e;
		}
           
        return isEmailExists;
	}

	@Override
	public Integer getUserIdByName(String firstName, String lastName) {
		Integer userId = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
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
	        
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting userId by name= " + firstName + " " + lastName + " failed", e);
		    transaction.rollback();
		    throw e;
		}
           
        return userId;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(User.class);
	
	        List<User> result = (List<User>) criteria.add(Restrictions.like("email", email)).list();
	        user = result.get(0);
	        
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting User-object by email= " + email + " failed", e);
		    transaction.rollback();
		    throw e;
		}
		
        return user;
	}

	@Override
	public User getUserById(Integer userId) {
		User user = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
			user = (User) session.get(User.class, userId);
			transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting User-object by id= " + userId + " failed", e);
		    transaction.rollback();
		    throw e;
		}
           
        return user;
	}

	@Override
	public boolean createUser(User user) {
		boolean success = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.save(user);
		    transaction.commit();
		    success = true;
		} catch (Exception e) {
			logger.error(TAG + " Creating User-object failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
		return success;
	}

	@Override
	public boolean updateUser(User user) {
		boolean success = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.update(user);
		    transaction.commit();
		    success = true;
		} catch (Exception e) {
			logger.error(TAG + " Updating User-object " +  user.getUserId() + " failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
		return success;
	}

	@Override
	public boolean deleteUser(int userId) {
		boolean success = false;
		
		User deletingUser = new User();
		deletingUser.setUserId(userId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.delete(deletingUser);
		    transaction.commit();
		    success = true;
		} catch (Exception e) {
			logger.error(TAG + " Deleting User-object " + userId + " failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
		return success;
	}

}