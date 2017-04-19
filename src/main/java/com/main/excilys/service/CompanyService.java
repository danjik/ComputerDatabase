package com.main.excilys.service;

import com.main.excilys.mapper.CompanyToDtoMapper;
import com.main.excilys.model.CompanyDto;
import com.main.excilys.persistence.ICompanyDao;
import com.main.excilys.persistence.implementation.CompanyDao;

import java.util.ArrayList;
import java.util.List;

public enum CompanyService {

  INSTANCE;
  public List<CompanyDto> listCompanyDto = new ArrayList<>();

  private ICompanyDao intCompanyDao = CompanyDao.INSTANCE;

  private CompanyService() {
    CompanyDao.listCompany.forEach(company -> {
      CompanyDto companyDto = CompanyToDtoMapper.toCompanyDto(company);
      listCompanyDto.add(companyDto);
    });
  }

  /**
   * Method to get all the company.
   *
   * @return list of the company
   */
  public List<CompanyDto> getAllCompany() {
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
    return CompanyToDtoMapper.toCompanyDto(intCompanyDao.getCompanyById(idToTest));
  }

  public int getNbCompany() {
    return listCompanyDto.size();
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
      listCompanyDto.add(CompanyToDtoMapper.toCompanyDto(company));
    });
    return listCompanyDto;
  }

  public void deleteCompanyById(long idToDelete) {
    intCompanyDao.deleteCompany(idToDelete);
  }

}
