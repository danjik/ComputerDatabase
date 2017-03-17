package com.main.excilys.persistence;

import java.util.List;

import com.main.excilys.model.Company;

public interface ICompanyDAO {
	List<Company> getAllCompany();

	int getNbCompany();

	Company getCompanyById(long idToTest);

	List<Company> getCompanyInRange(long idBegin, long idEnd);
}
