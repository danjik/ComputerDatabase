package com.java.service;

import java.util.ArrayList;
import java.util.List;

import com.java.mapper.CompanyToDTOMapper;
import com.java.model.CompanyDTO;
import com.java.persistence.CompanyDAO;
import com.java.persistence.ICompanyDAO;

public class CompanyService {
	private ICompanyDAO iComputerDAO = CompanyDAO.INSTANCE;

	public List<CompanyDTO> getAllCompany() {
		List<CompanyDTO> listCompanyDTO = new ArrayList<>();
		iComputerDAO.getAllCompany().forEach(company -> {
			listCompanyDTO.add(CompanyToDTOMapper.INSTANCE.toCompanyDTO(company));
		});
		return listCompanyDTO;
	}

	public CompanyDTO getCompanyById(long idToTest) {
		return CompanyToDTOMapper.INSTANCE.toCompanyDTO(iComputerDAO.getCompanyById(idToTest));
	}

	public int getNbCompany() {
		return iComputerDAO.getNbCompany();
	}

	public List<CompanyDTO> getCompanyInRange(long idBegin, long idEnd) {
		List<CompanyDTO> listCompanyDTO = new ArrayList<>();
		iComputerDAO.getCompanyInRange(idBegin, idEnd).forEach(company -> {
			listCompanyDTO.add(CompanyToDTOMapper.INSTANCE.toCompanyDTO(company));
		});
		return listCompanyDTO;
	}

}
