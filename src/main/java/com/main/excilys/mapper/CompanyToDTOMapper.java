package com.main.excilys.mapper;

import com.main.excilys.model.Company;
import com.main.excilys.model.CompanyDTO;

public enum CompanyToDTOMapper {
	INSTANCE;

	public Company toCompany(CompanyDTO companyDTO) {
		return new Company.Builder().id(companyDTO.getId()).name(companyDTO.getName()).build();
	}

	public CompanyDTO toCompanyDTO(Company company) {
		return new CompanyDTO.Builder().id(company.getId()).name(company.getName()).build();
	}
}
