package com.main.excilys.persistence;

import com.main.excilys.model.Company;

import java.util.List;

public interface ICompanyDao {
  /**
   * Method to get all the companies.
   *
   * @return list of all the companies
   */
  List<Company> getAllCompany();

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
}
