package com.java.view;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.Company;
import com.java.model.ConnectionDB;
import com.java.model.Page;
import com.java.persistence.CompanyDAO;
import com.java.util.ComputerDBException;
import com.java.util.SecureInput;

public class CompanyView {
	private CompanyDAO companyDAO;
	private Page<Company> pageCompany;
	private Logger logger;

	/**
	 * The required connection can be obtained by the static method
	 * ConnectionDB.getInstance
	 *
	 * @param connectionDB
	 *            can be obtained by the static method ConnectionDB.getInstance
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public CompanyView(ConnectionDB connectionDB) throws ComputerDBException {
		this.companyDAO = new CompanyDAO(connectionDB);
		pageCompany = new Page<>(companyDAO.getAllCompany());
		logger = LogManager.getRootLogger();
	}

	public void listAllCompany() throws ComputerDBException {
		List<Company> lisCompany = companyDAO.getAllCompany();
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
