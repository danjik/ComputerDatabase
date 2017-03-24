package com.main.excilys.service;

import com.main.excilys.mapper.CompanyToDtoMapper;
import com.main.excilys.model.CompanyDto;
import com.main.excilys.persistence.CompanyDao;
import com.main.excilys.persistence.ICompanyDao;

import java.util.ArrayList;
import java.util.List;

public enum CompanyService {

  INSTANCE;

  private ICompanyDao intCompanyDao = CompanyDao.INSTANCE;

  /**
   * Method to get all the company.
   *
   * @return list of the company
   */
  public List<CompanyDto> getAllCompany() {
    List<CompanyDto> listCompanyDto = new ArrayList<>();
    intCompanyDao.getAllCompany().forEach(company -> {
      listCompanyDto.add(CompanyToDtoMapper.INSTANCE.toCompanyDto(company));
    });
    return listCompanyDto;
  }

  /**
   * Method to get a company by his id.
   *
   * @param idToTest
   *          the id of the selected company
   * @return the selected company
   */

  public CompanyDto getCompanyById(long idToTest) {
    return CompanyToDtoMapper.INSTANCE.toCompanyDto(intCompanyDao.getCompanyById(idToTest));
  }

  public int getNbCompany() {
    return intCompanyDao.getNbCompany();
  }

  /**
   * Get the item provided by the page.
   *
   * @param idBegin
   *          the begin id of the item
   * @param idEnd
   *          the end id of the item
   * @return the list of company
   */
  public List<CompanyDto> getCompanyInRange(long idBegin, long idEnd) {
    List<CompanyDto> listCompanyDto = new ArrayList<>();
    intCompanyDao.getCompanyInRange(idBegin, idEnd).forEach(company -> {
      listCompanyDto.add(CompanyToDtoMapper.INSTANCE.toCompanyDto(company));
    });
    return listCompanyDto;
  }

  public void deleteCompanyById(long idToDelete) {
    intCompanyDao.deleteCompany(idToDelete);
  }

}
