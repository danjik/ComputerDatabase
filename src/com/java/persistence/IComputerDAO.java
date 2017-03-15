package com.java.persistence;

import java.util.List;

import com.java.model.Computer;

public interface IComputerDAO {
	public int getNbComputer();

	public long createComputer(Computer newComputer);

	public Computer getComputerById(Long idToSelect);

	public List<Computer> getAllComputer();

	public void deleteComputer(long idToDelete);

	public void updateComputer(Computer updateComputer);
}
