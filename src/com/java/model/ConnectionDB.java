package com.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.util.ComputerDBException;

/**
 * Get a instance of the Computer Database connection by using the static method
 * getInstance
 *
 * Environment available : 1 = test database
 *
 * @author excilys
 *
 *
 * 
 */
public class ConnectionDB {
	private static ConnectionDB conn = null;
	private Connection connection = null;

	public static Integer SET_ENV_TEST = 1;

	private static final Logger logger = LogManager.getRootLogger();

	private ConnectionDB(String database) throws ComputerDBException {
		try {
			// TODO Put it on properties
			Configuration config = new PropertiesConfiguration("database.properties");
			String username = config.getString("username");
			String password = config.getString("password");
			logger.debug("Test to connect to the database : " + database + " with username : " + username);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = new String("jdbc:mysql://localhost:3306/" + database + "?zeroDateTimeBehavior=convertToNull");
			connection = DriverManager.getConnection(url, username, password);
			logger.debug("Success to connect database : " + database);
		} catch (InstantiationException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException("ConnectionDB " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException("ConnectionDB " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException("ConnectionDB " + e.getMessage());
		} catch (SQLException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException("ConnectionDB" + e.getMessage());
		} catch (ConfigurationException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException("ConnectionDB" + e.getMessage());
		}
	}

	public static ConnectionDB getInstance(int environment) throws ComputerDBException {
		if (environment != 1) {
			logger.error("Unknown environment : " + environment);
			throw new IllegalArgumentException("Unknown environment");
		}
		if (conn == null) {
			logger.debug("Creation of a new connection to the database : computer-database-db-test");
			conn = new ConnectionDB("computer-database-db-test");
		}
		return conn;
	}

	public static ConnectionDB getInstance() throws ComputerDBException {
		if (conn == null) {
			logger.debug("Creation of a new connection to the database : computer-database-db-test");
			conn = new ConnectionDB("computer-database-db");
		}
		return conn;
	}

	public Connection getConnection() {
		return connection;
	}

}
