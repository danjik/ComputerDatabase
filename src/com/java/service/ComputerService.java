package com.java.service;

import java.util.ArrayList;
import java.util.List;

import com.java.mapper.ComputerMapper;
import com.java.model.ComputerDTO;
import com.java.persistence.ComputerDAO;
import com.java.persistence.IComputerDAO;

public class ComputerService {
	private IComputerDAO iComputerDAO = ComputerDAO.INSTANCE;

	public List<ComputerDTO> getAllComputer() {
		List<ComputerDTO> listAllComputerDTO = new ArrayList<>();
		iComputerDAO.getAllComputer().forEach(computer -> {
			listAllComputerDTO.add(ComputerMapper.INSTANCE.toComputerDTO(computer));
		});
		return listAllComputerDTO;
	}

	public ComputerDTO getComputerById(long idToSelect) {
		return ComputerMapper.INSTANCE.toComputerDTO(iComputerDAO.getComputerById(idToSelect));
	}

	public long createComputer(ComputerDTO newComputer) {
		return iComputerDAO.createComputer(ComputerMapper.INSTANCE.toComputer(newComputer));
	}

	public int getNbComputer() {
		return iComputerDAO.getNbComputer();
	}

	public void deleteComputer(long idToDelete) {
		iComputerDAO.deleteComputer(idToDelete);
	}

	public void updateComputer(ComputerDTO computer) {
		iComputerDAO.updateComputer(ComputerMapper.INSTANCE.toComputer(computer));
	}

}
