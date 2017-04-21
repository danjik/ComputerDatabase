package com.main.excilys.service;

import com.main.excilys.mapper.ComputerToDtoMapper;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.persistence.IComputerDao;
import com.main.excilys.util.OptionValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerService {

  @Autowired
  private IComputerDao intComputerDao;

  /**
   * Return an instance of computer selected by his id.
   *
   * @param idToSelect
   *          the id of the selected computer
   * @return the computer selected
   */
  public ComputerDto getComputerById(long idToSelect) {
    return ComputerToDtoMapper.toComputerDto(intComputerDao.getComputerById(idToSelect));
  }

  /**
   * Creation of a new computer.
   *
   * @param newComputer
   *          the computer to create
   * @return the generated id of the computer
   */
  public long createComputer(ComputerDto newComputer) {
    return intComputerDao.createComputer(ComputerToDtoMapper.toComputer(newComputer));
  }

  /**
   * return the number of computer.
   *
   * @param options
   *          the options of the selected list
   * @return the number of computer
   */
  public int getNbComputer(Map<String, String> options) {
    OptionValidator.validate(options);
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
    intComputerDao.updateComputer(ComputerToDtoMapper.toComputer(computer));
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
    OptionValidator.validate(options);
    List<ComputerDto> listAllComputerDto = new ArrayList<>();
    intComputerDao.getComputerInRange(idBegin, nbObjectToGet, options)
        .forEach(computer -> listAllComputerDto.add(ComputerToDtoMapper.toComputerDto(computer)));
    return listAllComputerDto;
  }

}
