package com.java.presentation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.Company;
import com.java.model.Page;
import com.java.service.CompanyService;
import com.java.util.ComputerDBException;

public class CompanyPresentation {
	private Page<Company> pageCompany;
	private CompanyService companyService = new CompanyService();
	private Logger logger = LogManager.getRootLogger();

	/**
	 * The required connection can be obtained by the static method
	 * ConnectionDB.getInstance
	 *
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public CompanyPresentation() throws ComputerDBException {
		pageCompany = new Page<>(companyService.getAllCompany());
	}

	public void listAllCompany() throws ComputerDBException {
		List<Company> lisCompany = companyService.getAllCompany();
		lisCompany.forEach(company -> {
			System.out.println(company);
		});
	}

	public void listCompanyByPage() throws ComputerDBException {
		System.out.println("There is currently " + pageCompany.getNbPage() + " page");
		System.out.print("Which page do you want to list ? ");
		int page;
		do {
			page = SecureInput.secureInputInt("page");
		} while (page < 0 || page > pageCompany.getNbPage());
		logger.debug("Access to company page n°" + page);
		pageCompany.getPageInRange(page).forEach(company -> System.out.println(company));

	}

}
