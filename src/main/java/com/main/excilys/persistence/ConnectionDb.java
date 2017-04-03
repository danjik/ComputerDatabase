package com.main.excilys.persistence;

import com.main.excilys.util.ComputerDbException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ConnectionDb {
  CONNECTION;

  private Logger logger = LoggerFactory.getLogger(ConnectionDb.class);
  private HikariConfig config;
  private HikariDataSource hikariDataSource;

  /**
   * Simple private constructor.
   */
  ConnectionDb() {
    try {
      final String resourceName = "hikari.properties";
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      Properties props = new Properties();
      try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
        props.load(resourceStream);
        config = new HikariConfig(props);
        hikariDataSource = new HikariDataSource(config);

      }
    } catch (IOException e) {
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
    ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<Connection>() {

      @Override
      protected Connection initialValue() {
        // TODO Auto-generated method stub
        try {
          return hikariDataSource.getConnection();
        } catch (SQLException e) {
          throw new ComputerDbException(e.getMessage());
        }
      }

      @Override
      public void remove() {
        super.remove();
        try {
          this.get().close();
        } catch (Exception e) {
          throw new ComputerDbException(e.getMessage());
        }
      }

    };
    return threadLocalConnection.get();
  }
}
