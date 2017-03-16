package com.main.excilys.persistence;

import java.util.List;

import com.main.excilys.model.Company;

public interface ICompanyDAO {
	public List<Company> getAllCompany();

	public int getNbCompany();

	public Company getCompanyById(long idToTest);

	public List<Company> getCompanyInRange(long idBegin, long idEnd);
}
