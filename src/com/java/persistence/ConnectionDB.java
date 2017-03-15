package com.java.persistence;

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
public enum ConnectionDB {
	CONNECTION;

	private final Logger logger = LogManager.getRootLogger();

	private ConnectionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException(e);
		}
	}

	public Connection getConnection() {
		Configuration config;
		try {
			config = new PropertiesConfiguration("database.properties");

			String username = config.getString("username");
			String password = config.getString("password");
			String database = config.getString("database");
			logger.debug("Test to connect to the database : " + database + " with username : " + username);
			String url = new String("jdbc:mysql://localhost:3306/" + database + "?zeroDateTimeBehavior=convertToNull");
			return DriverManager.getConnection(url, username, password);
		} catch (ConfigurationException | SQLException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException(e);
		}
	}
}
