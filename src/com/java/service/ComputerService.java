package com.java.service;

import java.util.List;

import com.java.model.Computer;
import com.java.persistence.ComputerDAO;
import com.java.persistence.IComputerDAO;

public class ComputerService {
	private IComputerDAO iComputerDAO = ComputerDAO.INSTANCE;

	public List<Computer> getAllComputer() {
		return iComputerDAO.getAllComputer();
	}

	public Computer getComputerById(long idToSelect) {
		return iComputerDAO.getComputerById(idToSelect);
	}

	public long createComputer(Computer newComputer) {
		return iComputerDAO.createComputer(newComputer);
	}

	public int getNbComputer() {
		return iComputerDAO.getNbComputer();
	}

	public void deleteComputer(long idToDelete) {
		iComputerDAO.deleteComputer(idToDelete);
	}

	public void updateComputer(Computer computer) {
		iComputerDAO.updateComputer(computer);
	}

}
