package com.test.persistence;

import java.util.List;

import org.junit.Test;

import com.java.model.Company;
import com.java.service.CompanyService;
import com.java.util.ComputerDBException;

public class CompanyDAOTest {
	private CompanyService companyService = new CompanyService();

	@Test
	public void testGetCompanyById() throws ComputerDBException {
		List<Company> listAllCompany;
		listAllCompany = companyService.getAllCompany();

		Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
		long idToTest = randomCompany.getId();

		Company selectCompany = companyService.getCompanyById(idToTest);
		assert idToTest == selectCompany.getId();
	}

	@Test
	public void testGetAllCompany() throws ComputerDBException {
		List<Company> listCompany;
		listCompany = companyService.getAllCompany();

		int nbCompany = companyService.getNbCompany();
		System.out.println(nbCompany);
		System.out.println(listCompany.size());

		assert listCompany.size() == nbCompany;
	}
}
