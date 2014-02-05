package org.training.issuetracker.dao.connectionPool;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS; 
import org.apache.commons.dbcp.datasources.SharedPoolDataSource; 
import org.apache.log4j.Logger;
import org.training.issuetracker.managers.ConfigurationManager;
 
import javax.sql.DataSource; 
import java.sql.Connection; 
import java.sql.SQLException; 

/** 
* Class that implements the pool of database connections.
* @author Dzmitry Salnikau
* @since 04.02.2014
*/ 
public class DBConnectionPool { 

	private static final Logger logger = Logger.getLogger(DBConnectionPool.class);
	
	public static final String DRIVER = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
	public static final String URL = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
	public static final String USER = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_USER);
	public static final String PASSWORD = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_PASSWORD);
	
	/** 
	 * The maximum number of active compounds that can be used simultaneously.
	 * -1 - Unlimited connections
	 */ 
	private static final int MAX_ACTIVE = 100; 
   
	/** 
	 * The maximum number of milliseconds that the pool will wait (when there are no available connections)
	 * before throw an exception.
	 * -1 - waiting will last indefinitely
	 */ 
	private static final int MAX_WAIT = 5000; 

	private Connection connection = null; 

	private static DataSource ds = null;
 
	/** 
	 * Constructor defines the variables for a database connection
	 */ 
	public DBConnectionPool(){ 
		if (ds == null) { 
			try { 
				//Adapter for JDBC-driver. Stores the settings for connecting to the database
				DriverAdapterCPDS pcds = new DriverAdapterCPDS(); 
				pcds.setDriver(DRIVER); 
				pcds.setUrl(URL); 
				pcds.setUser(USER); 
				pcds.setPassword(PASSWORD); 
	
				//DataSource interface implementation for a shared connection pool
				SharedPoolDataSource tds = new SharedPoolDataSource(); 
				tds.setConnectionPoolDataSource(pcds); 
				tds.setMaxActive(MAX_ACTIVE); 
				tds.setMaxWait(MAX_WAIT); 
				ds = tds; 
			} catch (ClassNotFoundException e) { 
				logger.warn(e.toString());
			} 
		} 
	}
 
	/** 
	 * Returns a database connection
	 */ 
	public Connection openConnection() throws SQLException { 
		if (connection == null) connection = ds.getConnection(); 
			return connection; 
	}
 
	/** 
	 * Returns the number of active database connections
	 * @return the number of active database connections
	 */ 
	public static int getActiveConnection() { 
		return ((SharedPoolDataSource)ds).getNumActive(); 
	}

	/** 
	 * If the connection is open, returns a database connection
	 */ 
	public Connection getConnection() throws SQLException { 
		return openConnection(); 
	}
 
	/** 
	 * Close connection
	 */ 
	public void close() { 
		try { 
			if (connection != null) { 
				connection.close(); 
				connection = null; 
			} 
		} catch (SQLException e) { 
			logger.warn(e.toString());
		} 
	} 
} 