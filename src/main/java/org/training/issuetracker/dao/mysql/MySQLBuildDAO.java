package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.Build;

public class MySQLBuildDAO implements BuildDAO {

	private static final Logger logger = Logger.getLogger(MySQLBuildDAO.class);

	@Override
	public ArrayList<Build> getBuilds() {
		ArrayList<Build> builds = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Build.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					builds = new ArrayList<Build>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							builds.add(new Build(
									rs.getInt(Build.COLUMN_ID_ID),
									rs.getInt(Build.COLUMN_ID_PROJECT),
									rs.getString(Build.COLUMN_ID_VERSION))
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
		return builds;
	}
	
	@Override
	public ArrayList<Build> getBuildsForProject(int projectId) {
		ArrayList<Build> builds = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Build.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Build.COLUMN_NAME_PROJECT };
					Object[] selectionArgs = { projectId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					builds = new ArrayList<Build>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							builds.add(new Build(
									rs.getInt(Build.COLUMN_ID_ID),
									rs.getInt(Build.COLUMN_ID_PROJECT),
									rs.getString(Build.COLUMN_ID_VERSION))
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
		return builds;
	}

	@Override
	public Build getBuildById(int buildId) {
		Build build = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Build.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Build.COLUMN_NAME_ID };
					Object[] selectionArgs = { buildId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							build = new Build(rs.getInt(Build.COLUMN_ID_ID),
									rs.getInt(Build.COLUMN_ID_PROJECT),
									rs.getString(Build.COLUMN_ID_VERSION));
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
		return build;
	}

	@Override
	public boolean createBuild(Build build) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Build.TABLE_NAME;
					String[] columns = { Build.COLUMN_NAME_PROJECT,
							Build.COLUMN_NAME_VERSION };
					Object[] values = { build.getProject(), build.getVersion() };

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

	@Override
	public boolean updateBuild(Build build) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Build.TABLE_NAME;
					String[] columns = { Build.COLUMN_NAME_PROJECT,
							Build.COLUMN_NAME_VERSION };
					Object[] values = { build.getProject(), build.getVersion() };
					String[] selection = { Build.COLUMN_NAME_ID };
					Object[] selectionArgs = { build.getBuildId() };

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
	
	@Override
	public boolean deleteBuild(int buildId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Build.TABLE_NAME;
					String[] selection = { Build.COLUMN_NAME_ID };
					Object[] selectionArgs = { buildId };

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
