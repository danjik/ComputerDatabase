package com.main.excilys.persistence;

import com.main.excilys.util.ComputerDbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ConnectionDb {
  CONNECTION;

  private final Logger logger = LogManager.getRootLogger();

  /**
   * Simple private constructor.
   */
  ConnectionDb() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      logger.error("ConnectionDB : " + e.getMessage());
      throw new ComputerDbException(e);
    }
  }
  /**
   * Method to get an instance of SQL connection.
   *
   * @return An SQL connection instance.
   */

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

      logger
          .debug("Test to connect to the database : " + database + " with username : " + username);
      String url = typeconn + ":" + typedb + "://" + host + ":" + port + "/" + database + param;
      return DriverManager.getConnection(url, username, password);
    } catch (ConfigurationException | SQLException e) {
      logger.error("ConnectionDB : " + e.getMessage());
      throw new ComputerDbException(e);
    }
  }
}
