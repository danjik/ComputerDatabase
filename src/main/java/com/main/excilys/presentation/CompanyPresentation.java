package com.main.excilys.presentation;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.service.CompanyService;
import com.main.excilys.util.ComputerDbException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CompanyPresentation {
  private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  private ApplicationContext context = new ClassPathXmlApplicationContext(
      new String[] { "SpringBeans.xml" });
  private CompanyService companyService = (CompanyService) context.getBean("companyService");

  /**
   * Presentation of all the company.
   *
   * @throws ComputerDbException
   *           exception
   */

  public void listAllCompany() throws ComputerDbException {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    listCompany.forEach(company -> {
      System.out.println(company);
    });
  }

  /**
   * List the item collected by using the page information.
   *
   * @throws ComputerDbException
   *           exception
   *
   *
   *           public void listCompanyByPage(int numPage, int nbObject, int nbObjectPerPage) throws
   *           ComputerDbException { long idBegin = pageCompany.getNumPage() *
   *           Page.getNbObjectPerPage(); CompanyService.INSTANCE.getCompanyInRange(idBegin,
   *           Page.getNbObjectPerPage()) .forEach(company -> System.out.println(company)); }
   */

  /**
   * Atomic deletion of a company and all the computers linked.
   *
   * @param idToDelete
   *          the ID of the deleted company
   */
  public void deleteComputer(long idToDelete) {
    logger.debug("Action to delete company n°" + idToDelete);
    companyService.deleteCompanyById(idToDelete);

  }

}
