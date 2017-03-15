package com.java.service;

import java.util.List;

import com.java.model.Company;
import com.java.persistence.CompanyDAO;
import com.java.persistence.ICompanyDAO;

public class CompanyService {
	private ICompanyDAO iComputerDAO = CompanyDAO.INSTANCE;

	public List<Company> getAllCompany() {
		return iComputerDAO.getAllCompany();
	}

	public Company getCompanyById(long idToTest) {
		return iComputerDAO.getCompanyById(idToTest);
	}

	public int getNbCompany() {
		return iComputerDAO.getNbCompany();
	}

}
