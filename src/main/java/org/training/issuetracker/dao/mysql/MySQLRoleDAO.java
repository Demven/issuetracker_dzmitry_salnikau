package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.RoleDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.Role;

/**
 * !!! This class is not used.
 * Maybe it will be useful someday in the future in an other project.
 * @author Dzmitry_Salnikau
 * @deprecated
 */
public class MySQLRoleDAO /*implements RoleDAO */{
	
	private static final Logger logger = Logger.getLogger(MySQLRoleDAO.class);

	//@Override
	public ArrayList<Role> getRoles() {
		ArrayList<Role> roles = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Role.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					roles = new ArrayList<Role>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							roles.add(new Role(
									rs.getInt(Role.COLUMN_ID_ID),
									rs.getString(Role.COLUMN_ID_NAME))
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
		return roles;
	}

	//@Override
	public Role getRoleById(int roleId) {
		Role role = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Role.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Role.COLUMN_NAME_ID };
					Object[] selectionArgs = { roleId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							role = new Role(
									rs.getInt(Role.COLUMN_ID_ID),
									rs.getString(Role.COLUMN_ID_NAME)
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
		return role;
	}

	//@Override
	public boolean createRole(Role role) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Role.TABLE_NAME;
					String[] columns = { Role.COLUMN_NAME_NAME };
					Object[] values = { role.getName() };

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
	public boolean updateRole(Role role) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Role.TABLE_NAME;
					String[] columns = { Role.COLUMN_NAME_NAME };
					Object[] values = { role.getName() };
					String[] selection = { Role.COLUMN_NAME_ID };
					Object[] selectionArgs = { role.getRoleId() };

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
	public boolean deleteRole(int roleId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Role.TABLE_NAME;
					String[] selection = { Role.COLUMN_NAME_ID };
					Object[] selectionArgs = { roleId };

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
