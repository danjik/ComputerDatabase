package com.java.service;

import java.util.ArrayList;
import java.util.List;

import com.java.mapper.ComputerToDTOMapper;
import com.java.model.ComputerDTO;
import com.java.persistence.ComputerDAO;
import com.java.persistence.IComputerDAO;

public class ComputerService {
	private IComputerDAO iComputerDAO = ComputerDAO.INSTANCE;

	public List<ComputerDTO> getAllComputer() {
		List<ComputerDTO> listAllComputerDTO = new ArrayList<>();
		iComputerDAO.getAllComputer().forEach(computer -> {
			listAllComputerDTO.add(ComputerToDTOMapper.INSTANCE.toComputerDTO(computer));
		});
		return listAllComputerDTO;
	}

	public ComputerDTO getComputerById(long idToSelect) {
		return ComputerToDTOMapper.INSTANCE.toComputerDTO(iComputerDAO.getComputerById(idToSelect));
	}

	public long createComputer(ComputerDTO newComputer) {
		return iComputerDAO.createComputer(ComputerToDTOMapper.INSTANCE.toComputer(newComputer));
	}

	public int getNbComputer() {
		return iComputerDAO.getNbComputer();
	}

	public void deleteComputer(long idToDelete) {
		iComputerDAO.deleteComputer(idToDelete);
	}

	public void updateComputer(ComputerDTO computer) {
		iComputerDAO.updateComputer(ComputerToDTOMapper.INSTANCE.toComputer(computer));
	}

	public List<ComputerDTO> getComputerInRange(long idBegin, long idEnd) {
		List<ComputerDTO> listAllComputerDTO = new ArrayList<>();
		iComputerDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> {
			listAllComputerDTO.add(ComputerToDTOMapper.INSTANCE.toComputerDTO(computer));
		});
		return listAllComputerDTO;
	}

}
