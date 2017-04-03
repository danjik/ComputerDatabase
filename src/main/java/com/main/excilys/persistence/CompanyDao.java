package com.main.excilys.persistence;

import com.main.excilys.model.Company;
import com.main.excilys.util.ComputerDbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CompanyDao implements ICompanyDao {
  INSTANCE;
  private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  @Override
  public List<Company> getAllCompany() throws ComputerDbException {
    List<Company> listCompany = new ArrayList<>();
    String query = "select * from company";
    Company selectCompany = null;
    try (Connection conn = ConnectionDb.CONNECTION.getConnection();
        Statement selectPStatement = conn.createStatement();) {
      try (ResultSet rs = selectPStatement.executeQuery(query)) {
        while (rs.next()) {
          selectCompany = new Company.Builder().id(rs.getInt(1)).name(rs.getString(2)).build();
          listCompany.add(selectCompany);
        }
      }
    } catch (SQLException e) {
      logger.error("getAllCompany" + e);
      throw new ComputerDbException("getAllCompany " + e);
    }
    logger.debug("getAllCompany : " + listCompany.size() + " companies has been selected");

    return listCompany;
  }

  @Override
  public int getNbCompany() throws ComputerDbException {
    String query = "select count(*) from company";
    int nbCompany = -1;
    try (Connection conn = ConnectionDb.CONNECTION.getConnection();
        Statement selectPStatement = conn.createStatement();) {
      try (ResultSet rs = selectPStatement.executeQuery(query)) {
        while (rs.next()) {
          nbCompany = rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      logger.error("getNbCompany" + e.getMessage());
      throw new ComputerDbException("getNbCompany " + e);
    }

    return nbCompany;
  }

  @Override
  public Company getCompanyById(long idToTest) throws ComputerDbException {
    logger.debug("getCompanyById idToTest : " + idToTest);
    String query = "select * from company where id=?";
    Company selectCompany = null;

    try (Connection conn = ConnectionDb.CONNECTION.getConnection();
        PreparedStatement selectPStatement = conn.prepareStatement(query);) {
      selectPStatement.setLong(1, idToTest);
      try (ResultSet rs = selectPStatement.executeQuery()) {
        while (rs.next()) {
          selectCompany = new Company.Builder().id(rs.getInt(1)).name(rs.getString(2)).build();
        }
      }

    } catch (SQLException e) {
      logger.error("getCompanyById" + e.getMessage());
      throw new ComputerDbException("getCompanyById " + e);
    }
    logger.debug("getCompanyById selectedCompany : " + selectCompany);

    return selectCompany;
  }

  @Override
  public List<Company> getCompanyInRange(long idBegin, long idEnd) {
    logger.debug("getCompanyInRange idBegin : " + idBegin + " idEnd : " + idEnd);
    String query = "select * from company limit ?,?";
    List<Company> listCompany = new ArrayList<>();

    try (Connection conn = ConnectionDb.CONNECTION.getConnection();
        PreparedStatement selectPStatement = conn.prepareStatement(query);) {
      selectPStatement.setLong(1, idBegin);
      selectPStatement.setLong(2, idEnd);
      try (ResultSet rs = selectPStatement.executeQuery()) {
        while (rs.next()) {
          listCompany.add(new Company.Builder().id(rs.getInt(1)).name(rs.getString(2)).build());
        }
      }

    } catch (SQLException e) {
      logger.error("getCompanyInRange" + e.getMessage());
      throw new ComputerDbException("getCompanyInRange " + e);
    }

    return listCompany;
  }

  @Override
  public void deleteCompany(long idToDelete) {
    final String queryDeleteComputer = "delete from computer where company_id = ?";
    final String queryDeleteCompany = "delete from company where id = ?";

    try (Connection conn = ConnectionDb.CONNECTION.getConnection()) {
      try (PreparedStatement selectPStatement = conn.prepareStatement(queryDeleteComputer);) {
        conn.setAutoCommit(false);
        selectPStatement.setLong(1, idToDelete);
        selectPStatement.executeQuery();
        selectPStatement.close();
        try (
            PreparedStatement selectPStatementCompany = conn.prepareStatement(queryDeleteCompany)) {
          selectPStatementCompany.setLong(1, idToDelete);
          selectPStatementCompany.executeQuery();
          conn.commit();
          conn.setAutoCommit(true);
        }

      } catch (SQLException e) {
        conn.rollback();
        conn.setAutoCommit(true);
        logger.error("deleteCompany" + e.getMessage());
        throw new ComputerDbException("deleteCompany " + e);
      }
    } catch (SQLException e1) {
      logger.error("deleteCompany" + e1.getMessage());
      throw new ComputerDbException("deleteCompany " + e1);
    }

  }
}
