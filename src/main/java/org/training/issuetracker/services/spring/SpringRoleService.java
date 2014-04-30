package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Role;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.services.RoleService;

@Service("roleService") 
@Repository 
@Transactional 
public class SpringRoleService implements RoleService{

	@Autowired
	private RoleDAO roleDAO;
	
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	@Transactional
	@Override
	public List<Role> getRoles() {
		return roleDAO.getRoles();
	}

	@Transactional
	@Override
	public Role getRoleById(int roleId) {
		return roleDAO.getRoleById(roleId);
	}

	@Transactional
	@Override
	public boolean createRole(Role role) {
		return roleDAO.createRole(role);
	}

	@Transactional
	@Override
	public boolean updateRole(Role role) {
		return roleDAO.updateRole(role);
	}

	@Transactional
	@Override
	public boolean deleteRole(int roleId) {
		return roleDAO.deleteRole(roleId);
	}

}
