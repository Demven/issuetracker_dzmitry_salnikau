package org.training.issuetracker.dao.interfaces;

import java.util.ArrayList;

import org.training.issuetracker.dao.transferObjects.Role;


/**
 * Interface, providing access to the user's roles and their data
 * @author Dzmitry Salnikau
 * @since 10.02.2014
 */
public interface RoleDAO {

	/**
	 * @return ArrayList of all user's roles
	 */
	public ArrayList<Role> getRoles();
	
	/**
	 * Returns Role object with stated roleId
	 * @param roleId
	 * @return Role 
	 */
	public Role getRoleById(int roleId);
	
	/**
	 * Adds new role in a data storage
	 * @param Role
	 * @return boolean - true, if it was successful
	 */
	public boolean createRole(Role role);
	
	/**
	 * Update the role's data 
	 * @param Role
	 * @return boolean - true, if it was successful
	 */
	public boolean updateRole(Role role);
	
	/**
	 * Delete role from a data storage by the unique roleId
	 * @param roleId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteRole(int roleId);
}
