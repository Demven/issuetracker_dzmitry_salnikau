package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.Status;

/**
 * !!! This class is not used.
 * Maybe it will be useful someday in the future in an other project.
 * @author Dzmitry_Salnikau
 * @deprecated
 */
public class MySQLStatusDAO /*implements StatusDAO */ {

	private static final Logger logger = Logger.getLogger(MySQLStatusDAO.class);
	
	//@Override
	public ArrayList<Status> getStatuses() {
		ArrayList<Status> statuses = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Status.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					statuses = new ArrayList<Status>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							statuses.add(new Status(
									rs.getInt(Status.COLUMN_ID_ID),
									rs.getString(Status.COLUMN_ID_NAME))
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
		return statuses;
	}

	//@Override
	public Status getStatusById(int statusId) {
		Status status = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Status.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Status.COLUMN_NAME_ID };
					Object[] selectionArgs = { statusId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							status = new Status(
									rs.getInt(Status.COLUMN_ID_ID),
									rs.getString(Status.COLUMN_ID_NAME)
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
		return status;
	}

	//@Override
	public boolean createStatus(Status status) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Status.TABLE_NAME;
					String[] columns = { Status.COLUMN_NAME_NAME };
					Object[] values = { status.getName() };

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
	public boolean updateStatus(Status status) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Status.TABLE_NAME;
					String[] columns = { Status.COLUMN_NAME_NAME };
					Object[] values = { status.getName() };
					String[] selection = { Status.COLUMN_NAME_ID };
					Object[] selectionArgs = { status.getStatusId() };

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
	public boolean deleteStatus(int statusId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Status.TABLE_NAME;
					String[] selection = { Status.COLUMN_NAME_ID };
					Object[] selectionArgs = { statusId };

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
