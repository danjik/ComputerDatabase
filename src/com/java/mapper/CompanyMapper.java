package com.java.mapper;

import com.java.model.Company;
import com.java.model.CompanyDTO;

public enum CompanyMapper {
	INSTANCE;

	public Company toCompany(CompanyDTO companyDTO) {
		return new Company.Builder().id(companyDTO.getId()).name(companyDTO.getName()).build();
	}

	public CompanyDTO toCompanyDTO(Company company) {
		return new CompanyDTO.Builder().id(company.getId()).name(company.getName()).build();
	}
}
