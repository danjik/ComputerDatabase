package com.main.excilys.persistence;

import com.main.excilys.model.Computer;

import java.util.List;

public interface IComputerDao {
  /**
   * Method to get the number of computers.
   *
   * @return the number of computers
   */

  int getNbComputer();

  /**
   * Method to create a new computer.
   *
   * @param newComputer
   *          the computer to create
   * @return the generated id
   */

  long createComputer(Computer newComputer);
  /**
   * Method to get a computer by his id.
   *
   * @param idToSelect
   *          the id of the selected computer
   * @return the selected computer
   */

  Computer getComputerById(Long idToSelect);
  /**
   * Method to get all the computers.
   *
   * @return list of all the computers
   */

  List<Computer> getAllComputer();

  /**
   * Method to delete a computer.
   *
   * @param idToDelete
   *          the id of the computer to delete
   */
  void deleteComputer(long idToDelete);

  /**
   * Method to update a computer.
   *
   * @param updateComputer
   *          the updated computer
   */
  void updateComputer(Computer updateComputer);

  /**
   * Method to get a list of computers from item n°idBegin to n°idBegin + nbObject.
   *
   * @param idBegin
   *          idBegin
   * @param nbObjectToGet
   *          nb object to get
   * @return the list of computers from item n°idBegin to n°idBegin + nbObject.
   */
  List<Computer> getComputerInRange(long idBegin, long nbObjectToGet);
}
