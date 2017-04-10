package com.main.excilys.persistence;

import com.main.excilys.model.Company;
import com.main.excilys.util.ComputerDbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface ICompanyDao {
  /**
   * Method to get all the companies.
   *
   * @return list of all the companies
   */
  static List<Company> getAllCompany() {
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
      throw new ComputerDbException("getAllCompany " + e);
    }
    return listCompany;
  }

  /**
   * Method to get the number of companies.
   *
   * @return the number of companies
   */
  int getNbCompany();

  /**
   * Method to get a company selected by his id.
   *
   * @param idToTest
   *          the id to select a company
   * @return the selected company
   */
  Company getCompanyById(long idToTest);

  /**
   * Method to get a list of company in range.
   *
   * @param idBegin
   *          idBegin
   * @param idEnd
   *          idEnd
   * @return the list from item n°idBegin to n°idEnd
   */
  List<Company> getCompanyInRange(long idBegin, long idEnd);

  void deleteCompany(long idToDelete);
}
