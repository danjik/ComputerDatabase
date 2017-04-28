package com.main.excilys;

public class CompanyToDtoMapper {

  /**
   * Pass a companyDto to a company.
   *
   * @param companyDto
   *          the data transfers representation of of a company
   * @return the corresponding company
   */
  public static Company toCompany(CompanyDto companyDto) {
    Company company = companyDto != null
        ? new Company(companyDto.getId(), companyDto.getName())
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

  public static CompanyDto toCompanyDto(Company company) {
    CompanyDto companyDto = company != null
        ? new CompanyDto.Builder().id(company.getId()).name(company.getName()).build()
        : null;
    return companyDto;
  }
}
