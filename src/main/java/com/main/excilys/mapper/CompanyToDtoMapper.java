package com.main.excilys.mapper;

import com.main.excilys.model.Company;
import com.main.excilys.model.CompanyDto;

public enum CompanyToDtoMapper {
  INSTANCE;

  public Company toCompany(CompanyDto companyDto) {
    return new Company.Builder().id(companyDto.getId()).name(companyDto.getName()).build();
  }

  public CompanyDto toCompanyDto(Company company) {
    return new CompanyDto.Builder().id(company.getId()).name(company.getName()).build();
  }
}
