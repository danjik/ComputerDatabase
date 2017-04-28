package com.main.excilys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyService {

  private List<CompanyDto> listCompanyDto = new ArrayList<>();

  @Autowired
  CompanyRepository companyRepository;

  /**
   * Method to get all the company.
   *
   * @return list of the company
   */
  public List<CompanyDto> getAllCompany() {
    companyRepository.findAll().forEach(company -> {
      CompanyDto companyDto = CompanyToDtoMapper.toCompanyDto(company);
      listCompanyDto.add(companyDto);
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
    return CompanyToDtoMapper.toCompanyDto(companyRepository.findOne(idToTest));
  }

  public long getNbCompany() {
    return companyRepository.count();
  }

  public void deleteCompanyById(long idToDelete) {
    companyRepository.delete(idToDelete);
  }

}
