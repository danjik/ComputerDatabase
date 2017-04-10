package com.main.excilys.mapper;

import com.main.excilys.model.Company;
import com.main.excilys.model.CompanyDto;

public enum CompanyToDtoMapper {
  INSTANCE;

  /**
   * Pass a companyDto to a company.
   *
   * @param companyDto
   *          the data transfers representation of of a company
   * @return the corresponding company
   */
  public Company toCompany(CompanyDto companyDto) {
    Company company = companyDto != null
        ? new Company.Builder().id(companyDto.getId()).name(companyDto.getName()).build()
        : null;
    return company;
  }

  /**
   * Pass a company to a companyDto.
   *
   * @param company
   *          the company
   * @return the corresponding data transfers representation company
   */

  public CompanyDto toCompanyDto(Company company) {
    CompanyDto companyDto = company != null
        ? new CompanyDto.Builder().id(company.getId()).name(company.getName()).build()
        : null;
    return companyDto;
  }
}
