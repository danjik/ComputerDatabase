package com.main.excilys.service;

import com.main.excilys.mapper.ComputerToDtoMapper;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.persistence.ComputerDao;
import com.main.excilys.persistence.IComputerDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComputerService {
  private IComputerDao intComputerDao = ComputerDao.INSTANCE;

  /**
   * get all the computer.
   *
   * @return list of all the computer
   */
  public List<ComputerDto> getAllComputer() {
    List<ComputerDto> listAllComputerDto = new ArrayList<>();
    intComputerDao.getAllComputer().forEach(
        computer -> listAllComputerDto.add(ComputerToDtoMapper.INSTANCE.toComputerDto(computer)));
    return listAllComputerDto;
  }

  /**
   * Return an instance of computer selected by his id.
   *
   * @param idToSelect
   *          the id of the selected computer
   * @return the computer selected
   */
  public ComputerDto getComputerById(long idToSelect) {
    return ComputerToDtoMapper.INSTANCE.toComputerDto(intComputerDao.getComputerById(idToSelect));
  }

  /**
   * Creation of a new computer.
   *
   * @param newComputer
   *          the computer to create
   * @return the generated id of the computer
   */
  public long createComputer(ComputerDto newComputer) {
    return intComputerDao.createComputer(ComputerToDtoMapper.INSTANCE.toComputer(newComputer));
  }

  /**
   * return the number of computer.
   *
   * @param options
   *          the options of the selected list
   * @return the number of computer
   */
  public int getNbComputer(Map<String, String> options) {
    return intComputerDao.getNbComputer(options);
  }

  /**
   * Delete a computer.
   *
   * @param idToDelete
   *          the id of the computer to delete
   */

  public void deleteComputer(long idToDelete) {
    intComputerDao.deleteComputer(idToDelete);
  }

  /**
   * Update the computer.
   *
   * @param computer
   *          the computer to update
   */

  public void updateComputer(ComputerDto computer) {
    intComputerDao.updateComputer(ComputerToDtoMapper.INSTANCE.toComputer(computer));
  }

  /**
   * Get the computer provided by the page.
   *
   * @param idBegin
   *          first n° item to get
   * @param nbObjectToGet
   *          number of item to get
   * @param options
   *          the options of the selected list
   * @return list of item provided by the page
   */
  public List<ComputerDto> getComputerInRange(long idBegin, long nbObjectToGet,
      Map<String, String> options) {
    List<ComputerDto> listAllComputerDto = new ArrayList<>();
    intComputerDao.getComputerInRange(idBegin, nbObjectToGet, options).forEach(
        computer -> listAllComputerDto.add(ComputerToDtoMapper.INSTANCE.toComputerDto(computer)));
    return listAllComputerDto;
  }

}
