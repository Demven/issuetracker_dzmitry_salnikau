package org.training.issuetracker.dao.XML;

import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.User;

public class XMLUserDAO implements UserDAO{

	@Override
	public boolean checkLogin(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkUserEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkUserNick(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUserIdByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
