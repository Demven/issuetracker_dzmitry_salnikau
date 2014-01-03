package org.training.issuetracker.dao.interfaces;

import org.training.issuetracker.dao.transferObjects.User;

/**
 * Interface, providing access to the users and their data
 * @author Dzmitry Salnikau
 * @since 03.01.2013
 */
public interface UserDAO {
	/**
	 * Check existing of the user with such parameters
	 * @param email
	 * @param password
	 * @return boolean - true, if the user exists
	 */
	public boolean checkLogin(String email, String password);
	
	/**
	 * Check existing of that email address
	 * @param email
	 * @return boolean - true, if such email exists
	 */
	public boolean checkUserEmail(String email);
	
	/**
	 * Check existing of the user's name and last name
	 * @param firstName
	 * @param lastName
	 * @return boolean - true, if such name exists
	 */
	public boolean checkUserNick(String firstName, String lastName);
	
	/**
	 * Defines the unique user's Id by the full name
	 * @param firstName
	 * @param lastName
	 * @return String userId
	 */
	public String getUserIdByName(String firstName, String lastName);
	
	/**
	 * Returns User object for user with stated userId
	 * @param userId
	 * @return User 
	 */
	public User getUserById(String userId);
	
	/**
	 * Adds new user in a data storage
	 * @param User
	 * @return boolean - true, if it was successful
	 */
	public boolean createUser(User user);
	
	/**
	 * Delete user from a data storage by the unique userId
	 * @param userId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteUser(String userId);
	
	/**
	 * Update the user's data 
	 * @param user
	 * @return boolean - true, if it was successful
	 */
	public boolean updateUser(User user);
	
}
