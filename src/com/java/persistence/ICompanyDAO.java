package com.java.persistence;

import java.util.List;

import com.java.model.Company;

public interface ICompanyDAO {
	public List<Company> getAllCompany();

	public int getNbCompany();

	public Company getCompanyById(long idToTest);
}
