package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.Project;

/**
 * !!! This class is not used.
 * Maybe it will be useful someday in the future in an other project.
 * @author Dzmitry Salnikau
 * @deprecated
 */
public class MySQLProjectDAO /*implements ProjectDAO */ {

	private static final Logger logger = Logger.getLogger(MySQLProjectDAO.class);

	//@Override
	public ArrayList<Project> getProjects() {
		ArrayList<Project> projects = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Project.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					projects = new ArrayList<Project>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							projects.add(new Project(rs.getInt(Project.COLUMN_ID_ID),
									rs.getString(Project.COLUMN_ID_NAME),
									rs.getString(Project.COLUMN_ID_DESCRIPTION),
									rs.getInt(Project.COLUMN_ID_MANAGER)));
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
		return projects;
	}

	//@Override
	public Project getProjectById(int projectId) {
		Project project = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Project.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Project.COLUMN_NAME_ID };
					Object[] selectionArgs = { projectId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							project = new Project(rs.getInt(Project.COLUMN_ID_ID),
									rs.getString(Project.COLUMN_ID_NAME),
									rs.getString(Project.COLUMN_ID_DESCRIPTION),
									rs.getInt(Project.COLUMN_ID_MANAGER));
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
		return project;
	}
	
	//@Override
	public Integer getProjectIdByName(String name) {
		Integer projectId = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Project.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Project.COLUMN_NAME_NAME};
					Object[] selectionArgs = { name};

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							projectId = rs.getInt(Project.COLUMN_ID_ID);
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
		return projectId;
	}

	//@Override
	public boolean createProject(Project project) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Project.TABLE_NAME;
					String[] columns = {
							Project.COLUMN_NAME_NAME,
							Project.COLUMN_NAME_DESCRIPTION ,
							Project.COLUMN_NAME_MANAGER
							};
					Object[] values = {
							project.getName(),
							project.getDescription(),
							project.getManager()
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
	public boolean updateProject(Project project) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Project.TABLE_NAME;
					String[] columns = {
							Project.COLUMN_NAME_NAME,
							Project.COLUMN_NAME_DESCRIPTION ,
							Project.COLUMN_NAME_MANAGER
							};
					Object[] values = {
							project.getName(),
							project.getDescription(),
							project.getManager()
							};
					String[] selection = { Project.COLUMN_NAME_ID };
					Object[] selectionArgs = { project.getProjectId() };

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
	public boolean deleteProject(int projectId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Project.TABLE_NAME;
					String[] selection = { Project.COLUMN_NAME_ID };
					Object[] selectionArgs = { projectId };

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
