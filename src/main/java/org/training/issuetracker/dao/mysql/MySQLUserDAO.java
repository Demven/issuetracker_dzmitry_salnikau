package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.UserDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.User;

/**
 * !!! This class is not used.
 * Maybe it will be useful someday in the future in an other project.
 * @author Dzmitry_Salnikau
 * @deprecated
 */
public class MySQLUserDAO /* implements UserDAO */  {
	
	private static final Logger logger = Logger.getLogger(MySQLUserDAO.class);

	//@Override
	public ArrayList<User> getUsers() {
		ArrayList<User> users = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					users = new ArrayList<User>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							users.add(new User(
									rs.getInt(User.COLUMN_ID_ID),
									rs.getString(User.COLUMN_ID_FIRST_NAME),
									rs.getString(User.COLUMN_ID_LAST_NAME),
									rs.getString(User.COLUMN_ID_EMAIL),
									rs.getInt(User.COLUMN_ID_ROLE),
									rs.getString(User.COLUMN_ID_PASSWORD))
							);
						}
					} finally {
						if (rs != null)
							rs.close();
					}
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return users;
	}

	//@Override
	public boolean checkAuth(String email, String password) {
		boolean isAuth = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { User.COLUMN_NAME_EMAIL, User.COLUMN_NAME_PASSWORD};
					Object[] selectionArgs = { email, password };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							isAuth = true;
						}
					} finally {
						if (rs != null)
							rs.close();
					}
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return isAuth;
	}

	//@Override
	public boolean checkUserEmail(String email) {
		boolean isEmailExists = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { User.COLUMN_NAME_EMAIL};
					Object[] selectionArgs = { email };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							isEmailExists = true;
						}
					} finally {
						if (rs != null)
							rs.close();
					}
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return isEmailExists;
	}

	//@Override
	public Integer getUserIdByName(String firstName, String lastName) {
		Integer userId = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { User.COLUMN_NAME_FIRST_NAME, User.COLUMN_NAME_LAST_NAME};
					Object[] selectionArgs = { firstName, lastName };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							userId = rs.getInt(User.COLUMN_ID_ID);
						}
					} finally {
						if (rs != null)
							rs.close();
					}
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return userId;
	}

	//@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { User.COLUMN_NAME_EMAIL };
					Object[] selectionArgs = { email };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							user = new User(
									rs.getInt(User.COLUMN_ID_ID),
									rs.getString(User.COLUMN_ID_FIRST_NAME),
									rs.getString(User.COLUMN_ID_LAST_NAME),
									rs.getString(User.COLUMN_ID_EMAIL),
									rs.getInt(User.COLUMN_ID_ROLE),
									rs.getString(User.COLUMN_ID_PASSWORD));
						}
					} finally {
						if (rs != null)
							rs.close();
					}
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return user;
	}

	//@Override
	public User getUserById(Integer userId) {
		User user = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { User.COLUMN_NAME_ID };
					Object[] selectionArgs = { userId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							user = new User(
									rs.getInt(User.COLUMN_ID_ID),
									rs.getString(User.COLUMN_ID_FIRST_NAME),
									rs.getString(User.COLUMN_ID_LAST_NAME),
									rs.getString(User.COLUMN_ID_EMAIL),
									rs.getInt(User.COLUMN_ID_ROLE),
									rs.getString(User.COLUMN_ID_PASSWORD));
						}
					} finally {
						if (rs != null)
							rs.close();
					}
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return user;
	}

	//@Override
	public boolean createUser(User user) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);
					
					String table = User.TABLE_NAME;
					String[] columns = {
							User.COLUMN_NAME_FIRST_NAME,
							User.COLUMN_NAME_LAST_NAME, 
							User.COLUMN_NAME_EMAIL, 
							User.COLUMN_NAME_ROLE, 
							User.COLUMN_NAME_PASSWORD
							};
					Object[] values = {
							user.getFirstName(),
							user.getLastName(),
							user.getEmail(),
							user.getRoleId(),
							user.getPassword()
							};

					st = statementBuilder.getInsertPreparedStatement(table,
							columns, values);
					st.executeUpdate();
					isSuccess = true;
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return isSuccess;
	}

	//@Override
	public boolean updateUser(User user) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String[] columns = {
							User.COLUMN_NAME_FIRST_NAME,
							User.COLUMN_NAME_LAST_NAME, 
							User.COLUMN_NAME_EMAIL, 
							User.COLUMN_NAME_ROLE, 
							User.COLUMN_NAME_PASSWORD
							};
					Object[] values = {
							user.getFirstName(),
							user.getLastName(),
							user.getEmail(),
							user.getRoleId(),
							user.getPassword()
							};
					String[] selection = { User.COLUMN_NAME_ID };
					Object[] selectionArgs = { user.getUserId() };

					st = statementBuilder.getUpdatePreparedStatement(table,
							columns, values, selection, selectionArgs);
					st.executeUpdate();
					isSuccess = true;
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return isSuccess;
	}
	
	//@Override
	public boolean deleteUser(int userId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = User.TABLE_NAME;
					String[] selection = { User.COLUMN_NAME_ID };
					Object[] selectionArgs = { userId };

					st = statementBuilder.getDeletePreparedStatement(table,
							selection, selectionArgs);
					st.executeUpdate();
					isSuccess = true;
				} finally {
					if (st != null)
						st.close();
				}
			} finally {
				if (cn != null)
					cn.close();
			}
		} catch (SQLException e) {
			logger.warn(e.toString());
		}
		return isSuccess;
	}

}
