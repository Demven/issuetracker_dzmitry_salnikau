package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.services.UserService;

@Service("userService") 
@Repository 
@Transactional 
public class SpringUserService implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Transactional
	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Transactional
	@Override
	public boolean checkAuth(String email, String password) {
		return userDAO.checkAuth(email, password);
	}

	@Transactional
	@Override
	public boolean checkUserEmail(String email) {
		return userDAO.checkUserEmail(email);
	}

	@Transactional
	@Override
	public Integer getUserIdByName(String firstName, String lastName) {
		return userDAO.getUserIdByName(firstName, lastName);
	}

	@Transactional
	@Override
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

	@Transactional
	@Override
	public User getUserById(Integer userId) {
		return userDAO.getUserById(userId);
	}

	@Transactional
	@Override
	public boolean createUser(User user) {
		return userDAO.createUser(user);
	}

	@Transactional
	@Override
	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}

	@Transactional
	@Override
	public boolean deleteUser(int userId) {
		return userDAO.deleteUser(userId);
	}

}
