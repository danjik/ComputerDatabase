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

  @Test
  public void testGetCompanyById() throws ComputerDbException {
    List<CompanyDto> listAllCompany;
    listAllCompany = CompanyService.INSTANCE.getAllCompany();

    CompanyDto randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
    long idToTest = randomCompany.getId();

    CompanyDto selectCompany = CompanyService.INSTANCE.getCompanyById(idToTest);
    assert idToTest == selectCompany.getId();
  }

  @Test
  public void testGetAllCompany() throws ComputerDbException {
    List<CompanyDto> listCompany;
    listCompany = CompanyService.INSTANCE.getAllCompany();

    int nbCompany = CompanyService.INSTANCE.getNbCompany();

    assert listCompany.size() == nbCompany;
  }
}
