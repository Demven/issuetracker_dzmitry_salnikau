package org.training.issuetracker.dao.xml;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.transferObjects.User;
import org.training.issuetracker.dao.xml.handlers.UsersHandler;
import org.training.issuetracker.managers.ConfigurationManager;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLUserDAO implements UserDAO{
	
	private static final Logger logger = Logger.getLogger(XMLUserDAO.class);
	private static final String XML_FILENAME = ConfigurationManager.getInstance().getProperty(ConfigurationManager.XML_USERS_PATH);
	
	private String xmlFullPath;
	
	public XMLUserDAO(String rootPath){
		xmlFullPath = rootPath + XML_FILENAME;
	}
	
	@Override
	public ArrayList<User> getUsers() {
		ArrayList<User> users = null;
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();  
			UsersHandler handler = new UsersHandler(); 
			reader.setContentHandler(handler); 
			reader.parse(xmlFullPath); 
			users = handler.getUsers();
		} catch (SAXException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		} catch (IOException e) { 
			e.printStackTrace(); 
			logger.error(e.getMessage()); 
		}
		return users;
	}
	
	@Override
	public boolean checkAuth(String email, String password) {
		boolean isAuth = false;
		ArrayList<User> users = getUsers();
		for(User user:users){
			if(user.getEmail().equals(email)){
				if(user.getPassword().equals(password)){
					isAuth = true;
				}
			}
		}
		return isAuth;
	}

	@Override
	public boolean checkUserEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getUserIdByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User getUserByEmail(String email) {
		User searchedUser = null;
		ArrayList<User> users = getUsers();
		for(User user:users){
			if(user.getEmail().equals(email)){
				searchedUser = user;
			}
		}
		return searchedUser;
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
