package com.main.excilys.persistence;

import com.main.excilys.model.Computer;

import java.util.List;

public interface IComputerDao {
  int getNbComputer();

  long createComputer(Computer newComputer);

  Computer getComputerById(Long idToSelect);

  List<Computer> getAllComputer();

  void deleteComputer(long idToDelete);

  void updateComputer(Computer updateComputer);

  List<Computer> getComputerInRange(long idBegin, long nbObjectToGet);
}
