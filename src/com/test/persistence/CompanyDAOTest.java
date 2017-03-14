package com.test.persistence;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.java.model.Company;
import com.java.model.ConnectionDB;
import com.java.persistence.CompanyDAO;
import com.java.util.ComputerDBException;

public class CompanyDAOTest {
	private ConnectionDB connectionDB;
	private CompanyDAO companyDAO;

	@Before
	public void setUp() throws ComputerDBException {
		connectionDB = ConnectionDB.getInstance(ConnectionDB.SET_ENV_TEST);
		companyDAO = new CompanyDAO(connectionDB);
	}

	@Test
	public void testGetCompanyById() throws ComputerDBException {
		List<Company> listAllCompany;
		listAllCompany = companyDAO.getAllCompany();

		Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
		long idToTest = randomCompany.getId();

		Company selectCompany = companyDAO.getCompanyById(idToTest);
		assert idToTest == selectCompany.getId();
	}

	@Test
	public void testGetAllCompany() throws ComputerDBException {
		List<Company> listCompany;
		listCompany = companyDAO.getAllCompany();

		int nbCompany = companyDAO.getNbCompany();
		System.out.println(nbCompany);
		System.out.println(listCompany.size());

		assert listCompany.size() == nbCompany;
	}
}
