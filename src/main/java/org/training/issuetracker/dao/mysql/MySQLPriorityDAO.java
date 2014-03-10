package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.Priority;

/**
 * !!! This class is not used.
 * Maybe it will be useful someday in the future in an other project.
 * @author Dzmitry_Salnikau
 * @deprecated
 */
public class MySQLPriorityDAO /*implements PriorityDAO */{

	private static final Logger logger = Logger.getLogger(MySQLPriorityDAO.class);
	
	//@Override
	public ArrayList<Priority> getPriorities() {
		ArrayList<Priority> priorities = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Priority.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					priorities = new ArrayList<Priority>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							priorities.add(new Priority(
									rs.getInt(Priority.COLUMN_ID_ID),
									rs.getString(Priority.COLUMN_ID_NAME))
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
		return priorities;
	}

	//@Override
	public Priority getPriorityById(int priorityId) {
		Priority priority = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Priority.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Priority.COLUMN_NAME_ID };
					Object[] selectionArgs = { priorityId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							priority = new Priority(
									rs.getInt(Priority.COLUMN_ID_ID),
									rs.getString(Priority.COLUMN_ID_NAME)
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
		return priority;
	}

	//@Override
	public boolean createPriority(Priority priority) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Priority.TABLE_NAME;
					String[] columns = { Priority.COLUMN_NAME_NAME };
					Object[] values = { priority.getName() };

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
	public boolean updatePriority(Priority priority) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Priority.TABLE_NAME;
					String[] columns = { Priority.COLUMN_NAME_NAME };
					Object[] values = { priority.getName() };
					String[] selection = { Priority.COLUMN_NAME_ID };
					Object[] selectionArgs = { priority.getPriorityId() };

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
	public boolean deletePriority(int priorityId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Priority.TABLE_NAME;
					String[] selection = { Priority.COLUMN_NAME_ID };
					Object[] selectionArgs = { priorityId };

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
