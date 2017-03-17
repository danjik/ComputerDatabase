package com.main.excilys.presentation;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.Page;
import com.main.excilys.service.CompanyService;
import com.main.excilys.util.ComputerDbException;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyPresentation {
  private Page<CompanyDto> pageCompany;
  private CompanyService companyService = new CompanyService();
  private Logger logger = LogManager.getRootLogger();

  /**
   * Presentation of all the company.
   *
   * @throws ComputerDbException
   *           exception
   */

  public void listAllCompany() throws ComputerDbException {
    List<CompanyDto> lisCompany = companyService.getAllCompany();
    lisCompany.forEach(company -> {
      System.out.println(company);
    });
  }

  /**
   * List the item collected by using the page information.
   *
   * @throws ComputerDbException
   *           exception
   */

  public void listCompanyByPage() throws ComputerDbException {
    logger.debug("Access to company page n°" + pageCompany.getNumPage());
    long idBegin = pageCompany.getNumPage() * Page.getNbObjectPerPage();
    companyService.getCompanyInRange(idBegin, Page.getNbObjectPerPage())
        .forEach(company -> System.out.println(company));
  }

}
