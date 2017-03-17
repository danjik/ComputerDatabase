package com.main.excilys.service;

import com.main.excilys.mapper.CompanyToDtoMapper;
import com.main.excilys.model.CompanyDto;
import com.main.excilys.persistence.CompanyDao;
import com.main.excilys.persistence.ICompanyDao;

import java.util.ArrayList;
import java.util.List;

public class CompanyService {
  private ICompanyDao intComputerDao = CompanyDao.INSTANCE;

  /**
   * Method to get all the company.
   *
   * @return list of the company
   */
  public List<CompanyDto> getAllCompany() {
    List<CompanyDto> listCompanyDto = new ArrayList<>();
    intComputerDao.getAllCompany().forEach(company -> {
      listCompanyDto.add(CompanyToDtoMapper.INSTANCE.toCompanyDto(company));
    });
    return listCompanyDto;
  }

  public CompanyDto getCompanyById(long idToTest) {
    return CompanyToDtoMapper.INSTANCE.toCompanyDto(intComputerDao.getCompanyById(idToTest));
  }

  public int getNbCompany() {
    return intComputerDao.getNbCompany();
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
    intComputerDao.getCompanyInRange(idBegin, idEnd).forEach(company -> {
      listCompanyDto.add(CompanyToDtoMapper.INSTANCE.toCompanyDto(company));
    });
    return listCompanyDto;
  }

}
