package com.test.excilys.persistence;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.service.CompanyService;
import com.main.excilys.util.ComputerDbException;

import java.util.List;

import org.junit.Test;

/**
 * Class to test the company.
 *
 * @author excilys
 *
 */
public class CompanyDaoTest {
  private CompanyService companyService = new CompanyService();

  @Test
  public void testGetCompanyById() throws ComputerDbException {
    List<CompanyDto> listAllCompany;
    listAllCompany = companyService.getAllCompany();

    CompanyDto randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
    long idToTest = randomCompany.getId();

    CompanyDto selectCompany = companyService.getCompanyById(idToTest);
    assert idToTest == selectCompany.getId();
  }

  @Test
  public void testGetAllCompany() throws ComputerDbException {
    List<CompanyDto> listCompany;
    listCompany = companyService.getAllCompany();

    int nbCompany = companyService.getNbCompany();

    assert listCompany.size() == nbCompany;
  }
}
