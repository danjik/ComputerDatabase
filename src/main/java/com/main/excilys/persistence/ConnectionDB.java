package com.main.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.main.excilys.util.ComputerDBException;

/**
 * Get a instance of the Computer Database connection by using the static method
 * getInstance
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

			String typeconn = config.getString("typeconn");
			String typedb = config.getString("typedb");
			String host = config.getString("host");
			String port = config.getString("port");
			String database = config.getString("database");
			String param = config.getString("param");

			String username = config.getString("username");
			String password = config.getString("password");

			logger.debug("Test to connect to the database : " + database + " with username : " + username);
			String url = new String(typeconn + ":" + typedb + "://" + host + ":" + port + "/" + database + param);
			return DriverManager.getConnection(url, username, password);
		} catch (ConfigurationException | SQLException e) {
			logger.error("ConnectionDB : " + e.getMessage());
			throw new ComputerDBException(e);
		}
	}
}
