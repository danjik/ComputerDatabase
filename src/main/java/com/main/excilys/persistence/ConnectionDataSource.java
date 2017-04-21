package com.main.excilys.persistence;

import javax.sql.DataSource;

public class ConnectionDataSource {

  private static DataSource dataSource;

  public static DataSource getDataSource() {
    return dataSource;
  }

  public static void setDataSource(DataSource dataSource) {
    ConnectionDataSource.dataSource = dataSource;
  }

}
