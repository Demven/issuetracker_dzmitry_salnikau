package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.dao.entities.User;

/**
 * Interface, providing access to the users and their data
 * @author Dzmitry Salnikau
 * @since 03.01.2013
 */
public interface UserDAO {
	
	/**
	 * @return ArrayList of all users, registered into the system
	 */
	public List<User> getUsers();
	
	/**
	 * @return ArrayList of all users that matches a specified filter
	 */
	public List<User> getUsersWithFilter(String filter);
	
	
	/**
	 * Check existing of the user with such parameters
	 * @param email
	 * @param password
	 * @return boolean - true, if the user exists
	 */
	public boolean checkAuth(String email, String password);
	
	/**
	 * Check existing of that email address
	 * @param email
	 * @return boolean - true, if such email exists
	 */
	public boolean checkUserEmail(String email);
	
	/**
	 * Defines the unique user's Id by the full name
	 * @param firstName
	 * @param lastName
	 * @return String userId
	 */
	public Integer getUserIdByName(String firstName, String lastName);
	
	/**
	 * Defines the user by the email address and returns User object
	 * @param email
	 * @return User
	 */
	public User getUserByEmail(String email);
	
	/**
	 * Returns User object for user with stated userId
	 * @param userId
	 * @return User 
	 */
	public User getUserById(Integer userId);
	
	/**
	 * Adds new user in a data storage
	 * @param User
	 * @return boolean - true, if it was successful
	 */
	public boolean createUser(User user);
	
	/**
	 * Update the user's data 
	 * @param user
	 * @return boolean - true, if it was successful
	 */
	public boolean updateUser(User user);
	
	/**
	 * Delete user from a data storage by the unique userId
	 * @param userId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteUser(int userId);
}
