package com.main.excilys.service;

import java.util.ArrayList;
import java.util.List;

import com.main.excilys.mapper.ComputerToDTOMapper;
import com.main.excilys.model.ComputerDTO;
import com.main.excilys.persistence.ComputerDAO;
import com.main.excilys.persistence.IComputerDAO;

public class ComputerService {
	private IComputerDAO iComputerDAO = ComputerDAO.INSTANCE;

	public List<ComputerDTO> getAllComputer() {
		List<ComputerDTO> listAllComputerDTO = new ArrayList<>();
		iComputerDAO.getAllComputer().forEach(computer -> {
			listAllComputerDTO
					.add(ComputerToDTOMapper.INSTANCE.toComputerDTO(computer));
		});
		return listAllComputerDTO;
	}

	public ComputerDTO getComputerById(long idToSelect) {
		return ComputerToDTOMapper.INSTANCE
				.toComputerDTO(iComputerDAO.getComputerById(idToSelect));
	}

	public long createComputer(ComputerDTO newComputer) {
		return iComputerDAO.createComputer(
				ComputerToDTOMapper.INSTANCE.toComputer(newComputer));
	}

	public int getNbComputer() {
		return iComputerDAO.getNbComputer();
	}

	public void deleteComputer(long idToDelete) {
		iComputerDAO.deleteComputer(idToDelete);
	}

	public void updateComputer(ComputerDTO computer) {
		iComputerDAO.updateComputer(
				ComputerToDTOMapper.INSTANCE.toComputer(computer));
	}

	public List<ComputerDTO> getComputerInRange(long idBegin,
			long nbObjectToGet) {
		List<ComputerDTO> listAllComputerDTO = new ArrayList<>();
		iComputerDAO.getComputerInRange(idBegin, nbObjectToGet)
				.forEach(computer -> {
					listAllComputerDTO.add(ComputerToDTOMapper.INSTANCE
							.toComputerDTO(computer));
				});
		return listAllComputerDTO;
	}

}
