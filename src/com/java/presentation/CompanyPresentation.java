package com.java.presentation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.CompanyDTO;
import com.java.model.Page;
import com.java.service.CompanyService;
import com.java.util.ComputerDBException;

public class CompanyPresentation {
	private Page<CompanyDTO> pageCompany;
	private CompanyService companyService = new CompanyService();
	private Logger logger = LogManager.getRootLogger();

	public CompanyPresentation() throws ComputerDBException {
		pageCompany = new Page<>(companyService.getAllCompany());
	}

	public void listAllCompany() throws ComputerDBException {
		List<CompanyDTO> lisCompany = companyService.getAllCompany();
		lisCompany.forEach(company -> {
			System.out.println(company);
		});
	}

	public void listCompanyByPage() throws ComputerDBException {
		logger.debug("Access to company page n°" + pageCompany.getNumPage());
		long idBegin = pageCompany.getNumPage() * Page.NB_OBJECT_PER_PAGE;
		long idEnd = pageCompany.getNumPage() * Page.NB_OBJECT_PER_PAGE + Page.NB_OBJECT_PER_PAGE;
		companyService.getCompanyInRange(idBegin, idEnd).forEach(company -> System.out.println(company));
	}

}
