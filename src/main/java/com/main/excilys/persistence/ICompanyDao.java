package com.main.excilys.persistence;

import com.main.excilys.model.Company;

import java.util.List;

public interface ICompanyDao {
  List<Company> getAllCompany();

  int getNbCompany();

  Company getCompanyById(long idToTest);

  List<Company> getCompanyInRange(long idBegin, long idEnd);
}
