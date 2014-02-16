package org.training.issuetracker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.training.issuetracker.dao.factories.MySQLDAOFactory;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.dao.mysql.builders.StatementBuilder;
import org.training.issuetracker.dao.transferObjects.Issue;

public class MySQLIssueDAO implements IssueDAO{

	private static final Logger logger = Logger.getLogger(MySQLIssueDAO.class);

	@Override
	public ArrayList<Issue> getIssues() {
		ArrayList<Issue> issues = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Issue.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, null, null);
					ResultSet rs = null;
					issues = new ArrayList<Issue>();
					try {
						rs = st.executeQuery();
						while (rs.next()) {
							issues.add(new Issue(
									rs.getInt(Issue.COLUMN_ID_ID),
									rs.getString(Issue.COLUMN_ID_CREATE_DATE),
									rs.getInt(Issue.COLUMN_ID_CREATED_BY),
									rs.getString(Issue.COLUMN_ID_MODIFY_DATE),
									rs.getInt(Issue.COLUMN_ID_MODIFIED_BY),
									rs.getString(Issue.COLUMN_ID_SUMMARY),
									rs.getString(Issue.COLUMN_ID_DESCRIPTION),
									rs.getInt(Issue.COLUMN_ID_STATUS),
									rs.getInt(Issue.COLUMN_ID_RESOLUTION),
									rs.getInt(Issue.COLUMN_ID_ISSUE_TYPE),
									rs.getInt(Issue.COLUMN_ID_PRIORITY),
									rs.getInt(Issue.COLUMN_ID_PROJECT),
									rs.getInt(Issue.COLUMN_ID_BUILD_FOUND),
									rs.getInt(Issue.COLUMN_ID_ASSIGNEE))
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
		return issues;
	}

	@Override
	public Issue getIssueById(Integer issueId) {
		Issue issue = null;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Issue.TABLE_NAME;
					String columns = StatementBuilder.ALL_COLUMNS;
					String[] selection = { Issue.COLUMN_NAME_ID };
					Object[] selectionArgs = { issueId };

					st = statementBuilder.getQueryPreparedStatement(table,
							columns, selection, selectionArgs);
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							issue = new Issue(
									rs.getInt(Issue.COLUMN_ID_ID),
									rs.getString(Issue.COLUMN_ID_CREATE_DATE),
									rs.getInt(Issue.COLUMN_ID_CREATED_BY),
									rs.getString(Issue.COLUMN_ID_MODIFY_DATE),
									rs.getInt(Issue.COLUMN_ID_MODIFIED_BY),
									rs.getString(Issue.COLUMN_ID_SUMMARY),
									rs.getString(Issue.COLUMN_ID_DESCRIPTION),
									rs.getInt(Issue.COLUMN_ID_STATUS),
									rs.getInt(Issue.COLUMN_ID_RESOLUTION),
									rs.getInt(Issue.COLUMN_ID_ISSUE_TYPE),
									rs.getInt(Issue.COLUMN_ID_PRIORITY),
									rs.getInt(Issue.COLUMN_ID_PROJECT),
									rs.getInt(Issue.COLUMN_ID_BUILD_FOUND),
									rs.getInt(Issue.COLUMN_ID_ASSIGNEE)
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
		return issue;
	}

	@Override
	public boolean createIssue(Issue issue) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Issue.TABLE_NAME;
					String[] columns = {
							Issue.COLUMN_NAME_CREATE_DATE,
							Issue.COLUMN_NAME_CREATED_BY,
							Issue.COLUMN_NAME_MODIFY_DATE,
							Issue.COLUMN_NAME_MODIFIED_BY,
							Issue.COLUMN_NAME_SUMMARY,
							Issue.COLUMN_NAME_DESCRIPTION,
							Issue.COLUMN_NAME_STATUS,
							Issue.COLUMN_NAME_RESOLUTION,
							Issue.COLUMN_NAME_ISSUE_TYPE,
							Issue.COLUMN_NAME_PRIORITY,
							Issue.COLUMN_NAME_PROJECT,
							Issue.COLUMN_NAME_BUILD_FOUND,
							Issue.COLUMN_NAME_ASSIGNEE
							};
					Object[] values = {
							issue.getCreateDate(),
							issue.getCreatedBy(),
							issue.getModifyDate(),
							issue.getModifiedBy(),
							issue.getSummary(),
							issue.getDescription(),
							issue.getStatus(),
							issue.getResolution(),
							issue.getType(),
							issue.getPriority(),
							issue.getProject(),
							issue.getBuildFound(),
							issue.getAssignee()
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

	@Override
	public boolean updateIssue(Issue issue) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Issue.TABLE_NAME;
					String[] columns = {
							Issue.COLUMN_NAME_CREATE_DATE,
							Issue.COLUMN_NAME_CREATED_BY,
							Issue.COLUMN_NAME_MODIFY_DATE,
							Issue.COLUMN_NAME_MODIFIED_BY,
							Issue.COLUMN_NAME_SUMMARY,
							Issue.COLUMN_NAME_DESCRIPTION,
							Issue.COLUMN_NAME_STATUS,
							Issue.COLUMN_NAME_RESOLUTION,
							Issue.COLUMN_NAME_ISSUE_TYPE,
							Issue.COLUMN_NAME_PRIORITY,
							Issue.COLUMN_NAME_PROJECT,
							Issue.COLUMN_NAME_BUILD_FOUND,
							Issue.COLUMN_NAME_ASSIGNEE
							};
					Object[] values = {
							issue.getCreateDate(),
							issue.getCreatedBy(),
							issue.getModifyDate(),
							issue.getModifiedBy(),
							issue.getSummary(),
							issue.getDescription(),
							issue.getStatus(),
							issue.getResolution(),
							issue.getType(),
							issue.getPriority(),
							issue.getProject(),
							issue.getBuildFound(),
							issue.getAssignee()
							};
					String[] selection = { Issue.COLUMN_NAME_ID };
					Object[] selectionArgs = { issue.getIssueId() };

					logger.warn("Trying to update issueId=" + issue.getIssueId());
					
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
	public boolean deleteIssue(Integer issueId) {
		boolean isSuccess = false;
		try {
			Connection cn = null;
			try {
				cn = MySQLDAOFactory.getConnection();
				PreparedStatement st = null;
				try {
					StatementBuilder statementBuilder = new StatementBuilder(cn);

					String table = Issue.TABLE_NAME;
					String[] selection = { Issue.COLUMN_NAME_ID };
					Object[] selectionArgs = { issueId };

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
