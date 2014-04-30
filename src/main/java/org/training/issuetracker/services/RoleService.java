package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.entities.Role;

public interface RoleService {

	/**
	 * @return List of all user's roles
	 */
	public List<Role> getRoles();
	
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
