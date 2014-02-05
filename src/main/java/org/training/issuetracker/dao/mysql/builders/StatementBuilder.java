package org.training.issuetracker.dao.mysql.builders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class that create SQL statements with queries to MySQL database
 * @author Dzmitry Salnikau
 * @since 05.02.2014
 */
public class StatementBuilder {
	
	public static final String ALL_COLUMNS = "*";
	
	private final String WHERE = " WHERE ";
	private final String FROM = " FROM ";
	private final String AND = " AND ";
	
	// punctuation elements
	private final String EQUAL = " = ";
	private final String PLACEHOLDER = " ?";
	private final String COMMA = ", ";
	
	// query elements
	private final String SELECT = "SELECT ";
	
	// insert elements
	private final String INSERT_INTO = "INSERT INTO ";
	private final String VALUES = " VALUES ";
	
	// update elements
	private final String UPDATE = "UPDATE ";
	private final String SET = " SET ";
	
	// delete elements
	private final String DELETE = "DELETE ";
	
	private Connection connection = null;
	
	public StatementBuilder(Connection connection){
		this.connection = connection;
	}
	
	/**
	 * Returns PreparedStatement for database query by received values
	 * @param table - String, consists of table name
	 * @param columns - String, consists of column names, divided by comma
	 * @param selection - array of column names to create "WHERE" clause
	 * @param selectionArgs - array of Object values for columns, which are in selection array
	 * @return PreparedStatement - statement with query: 'SELECT ... FROM ... WHERE ... = ... AND ...'
	 * @throws SQLException 
	 */
	public PreparedStatement getQueryPreparedStatement(String table, String columns, String[] selection,
			Object[] selectionArgs) throws SQLException{
		PreparedStatement statement = null;
		
		StringBuilder queryBuilder = null;
		if(table != null && !table.equals("") && columns != null && !columns.equals("")){
			queryBuilder = new StringBuilder();
			queryBuilder.append(SELECT + columns + FROM + table);
			if(selection != null && selectionArgs != null && selection.length == selectionArgs.length){
				queryBuilder.append(WHERE + selection[0] + EQUAL + PLACEHOLDER);
				if(selection.length > 1){
					for(int i=1; i < selection.length; i++){
						queryBuilder.append(AND + selection[i] + EQUAL + PLACEHOLDER);
					}
				}
			}
		}
		
		// add values
		if(queryBuilder != null){
			statement = connection.prepareStatement(new String(queryBuilder)); 
			if(selectionArgs != null){
				for(int i=0; i < selectionArgs.length; i++){
					Object arg = selectionArgs[i];
					if(arg instanceof String){
						statement.setString(i + 1, (String)arg);
					} else if(arg instanceof Integer){
						statement.setInt(i + 1, (Integer)arg);
					} else if(arg instanceof Boolean){
						statement.setBoolean(i + 1, (Boolean)arg);
					}  
				}
			}
		}
		
		return statement;
	}
	
	/**
	 * Returns PreparedStatement for database insert by received values
	 * @param table - String, consists of table name
	 * @param columns - String, consists of column names, divided by comma
	 * @param values - array of Object values for columns, which are in columns array
	 * @return PreparedStatement - statement with query:
	 * 		'INSERT INTO ... (column1, column2, ...) VALUES (value1, value2)'
	 * @throws SQLException
	 */
	public PreparedStatement getInsertPreparedStatement(String table, String[]columns , Object[] values)
			throws SQLException{
		PreparedStatement statement = null;
		
		StringBuilder insertBuilder = null;
		if(table != null && !table.equals("")
				&& columns != null && values != null && columns.length == values.length){
			insertBuilder = new StringBuilder();
			insertBuilder.append(INSERT_INTO + table + "( ");

			// add columns
			insertBuilder.append(columns[0]);
			if(columns.length > 1){
				for(int i = 1; i < columns.length; i++){
					insertBuilder.append(COMMA + columns[i]);
				}
			}
			
			// add values
			insertBuilder.append(" )" + VALUES + "( ");
			insertBuilder.append(PLACEHOLDER);
			if(values.length > 1){
				for(int i = 1; i < values.length; i++){
					insertBuilder.append(COMMA + PLACEHOLDER);
				}
			}
			insertBuilder.append(" )");
		}
		
		// add values
		if(insertBuilder != null){
			statement = connection.prepareStatement(new String(insertBuilder)); 
			if(values != null){
				for(int i=0; i < values.length; i++){
					Object arg = values[i];
					if(arg instanceof String){
						statement.setString(i + 1, (String)arg);
					} else if(arg instanceof Integer){
						statement.setInt(i + 1, (Integer)arg);
					} else if(arg instanceof Boolean){
						statement.setBoolean(i + 1, (Boolean)arg);
					}  
				}
			}
		}
		
		return statement;
	}
	
	/**
	 * Returns PreparedStatement for database update by received values
	 * @param table - String, consists of table name
	 * @param columns - String, consists of column names, divided by comma
	 * @param values - array of Object values for columns, which are in columns array
	 * @param selection - array of column names to create "WHERE" clause
	 * @param selectionArgs - array of Object values for columns, which are in selection array
	 * @return PreparedStatement - statement with query:
	 * 		'UPDATE ... SET column1 = value1 ... WHERE column2 = value2 AND ...'
	 * @throws SQLException
	 */
	public PreparedStatement getUpdatePreparedStatement(String table, String[] columns, Object[] values,
			String[] selection, Object[] selectionArgs) throws SQLException{
		PreparedStatement statement = null;
		
		StringBuilder updateBuilder = null;
		if(table != null && !table.equals("")
				&& columns != null && values != null && columns.length == values.length){
			updateBuilder = new StringBuilder();
			updateBuilder.append(UPDATE + table + SET);

			// add columns
			updateBuilder.append(columns[0] + EQUAL + PLACEHOLDER);
			if(columns.length > 1){
				for(int i=1; i < columns.length; i++){
					updateBuilder.append(COMMA + columns[i] + EQUAL + PLACEHOLDER);
				}
			}
			
			
			// add WHERE clause
			if(selection != null && selectionArgs != null && selection.length == selectionArgs.length){
				updateBuilder.append(WHERE + selection[0] + EQUAL + PLACEHOLDER);
				if(selection.length > 1){
					for(int i=1; i < selection.length; i++){
						updateBuilder.append(AND + WHERE + selection[i] + EQUAL + PLACEHOLDER);
					}
				}
			}
		}
		
		// add values
		if(updateBuilder != null && values != null && selectionArgs != null){
			Object[] allObjects = new Object[values.length + selectionArgs.length];
			System.arraycopy(values, 0, allObjects, 0, values.length);
			System.arraycopy(selectionArgs, 0, allObjects, values.length, selectionArgs.length);

			statement = connection.prepareStatement(new String(updateBuilder)); 
			for(int i=0; i < allObjects.length; i++){
				Object arg = allObjects[i];
				if(arg instanceof String){
					statement.setString(i + 1, (String)arg);
				} else if(arg instanceof Integer){
					statement.setInt(i + 1, (Integer)arg);
				} else if(arg instanceof Boolean){
					statement.setBoolean(i + 1, (Boolean)arg);
				}  
			}
		}
		
		return statement;
	}
	
	/**
	 * Returns PreparedStatement for database delete by received values
	 * @param table - String, consists of table name
	 * @param selection - array of column names to create "WHERE" clause
	 * @param selectionArgs - array of Object values for columns, which are in selection array
	 * @return PreparedStatement - statement with query:
	 * 		'DELETE FROM ... WHERE column1 = value1 AND ...'
	 * @throws SQLException
	 */
	public PreparedStatement getDeletePreparedStatement(String table, String[] selection,
			Object[] selectionArgs) throws SQLException{
		PreparedStatement statement = null;
		
		StringBuilder deleteBuilder = null;
		if(table != null && !table.equals("")
				&& selection != null && selectionArgs != null && selection.length == selectionArgs.length){
			deleteBuilder = new StringBuilder();
			deleteBuilder.append(DELETE + FROM + table);
			
			// add WHERE clause
			deleteBuilder.append(WHERE + selection[0] + EQUAL + PLACEHOLDER);
			if(selection.length > 1){
				for(int i=1; i < selection.length; i++){
					deleteBuilder.append(AND + WHERE + selection[i] + EQUAL + PLACEHOLDER);
				}
			}
		}
		
		// add values
		if(deleteBuilder != null){
			statement = connection.prepareStatement(new String(deleteBuilder)); 
			if(selectionArgs != null){
				for(int i=0; i < selectionArgs.length; i++){
					Object arg = selectionArgs[i];
					if(arg instanceof String){
						statement.setString(i + 1, (String)arg);
					} else if(arg instanceof Integer){
						statement.setInt(i + 1, (Integer)arg);
					} else if(arg instanceof Boolean){
						statement.setBoolean(i + 1, (Boolean)arg);
					}  
				}
			}
		}
		
		return statement;
	}
}




