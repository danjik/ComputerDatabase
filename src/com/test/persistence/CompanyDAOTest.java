package com.test.persistence;

import java.util.List;

import org.junit.Test;

import com.java.model.Company;
import com.java.persistence.CompanyDAO;
import com.java.util.ComputerDBException;

public class CompanyDAOTest {

	@Test
	public void testGetCompanyById() throws ComputerDBException {
		List<Company> listAllCompany;
		listAllCompany = CompanyDAO.INSTANCE.getAllCompany();

		Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
		long idToTest = randomCompany.getId();

		Company selectCompany = CompanyDAO.INSTANCE.getCompanyById(idToTest);
		assert idToTest == selectCompany.getId();
	}

	@Test
	public void testGetAllCompany() throws ComputerDBException {
		List<Company> listCompany;
		listCompany = CompanyDAO.INSTANCE.getAllCompany();

		int nbCompany = CompanyDAO.INSTANCE.getNbCompany();
		System.out.println(nbCompany);
		System.out.println(listCompany.size());

		assert listCompany.size() == nbCompany;
	}
}
