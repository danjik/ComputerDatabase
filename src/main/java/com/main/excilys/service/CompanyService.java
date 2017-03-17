package com.main.excilys.service;

import java.util.ArrayList;
import java.util.List;

import com.main.excilys.mapper.CompanyToDTOMapper;
import com.main.excilys.model.CompanyDTO;
import com.main.excilys.persistence.CompanyDAO;
import com.main.excilys.persistence.ICompanyDAO;

public class CompanyService {
	private ICompanyDAO iComputerDAO = CompanyDAO.INSTANCE;

	public List<CompanyDTO> getAllCompany() {
		List<CompanyDTO> listCompanyDTO = new ArrayList<>();
		iComputerDAO.getAllCompany().forEach(company -> {
			listCompanyDTO
					.add(CompanyToDTOMapper.INSTANCE.toCompanyDTO(company));
		});
		return listCompanyDTO;
	}

	public CompanyDTO getCompanyById(long idToTest) {
		return CompanyToDTOMapper.INSTANCE
				.toCompanyDTO(iComputerDAO.getCompanyById(idToTest));
	}

	public int getNbCompany() {
		return iComputerDAO.getNbCompany();
	}

	public List<CompanyDTO> getCompanyInRange(long idBegin, long idEnd) {
		List<CompanyDTO> listCompanyDTO = new ArrayList<>();
		iComputerDAO.getCompanyInRange(idBegin, idEnd).forEach(company -> {
			listCompanyDTO
					.add(CompanyToDTOMapper.INSTANCE.toCompanyDTO(company));
		});
		return listCompanyDTO;
	}

}
