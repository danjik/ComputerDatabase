package com.main.excilys.service;

import com.main.excilys.mapper.ComputerToDtoMapper;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.persistence.ComputerDao;
import com.main.excilys.persistence.IComputerDao;

import java.util.ArrayList;
import java.util.List;

public class ComputerService {
  private IComputerDao intComputerDao = ComputerDao.INSTANCE;

  /**
   * get all the computer.
   *
   * @return list of all the computer
   */
  public List<ComputerDto> getAllComputer() {
    List<ComputerDto> listAllComputerDto = new ArrayList<>();
    intComputerDao.getAllComputer().forEach(computer -> {
      listAllComputerDto.add(ComputerToDtoMapper.INSTANCE.toComputerDto(computer));
    });
    return listAllComputerDto;
  }

  public ComputerDto getComputerById(long idToSelect) {
    return ComputerToDtoMapper.INSTANCE.toComputerDto(intComputerDao.getComputerById(idToSelect));
  }

  public long createComputer(ComputerDto newComputer) {
    return intComputerDao.createComputer(ComputerToDtoMapper.INSTANCE.toComputer(newComputer));
  }

  public int getNbComputer() {
    return intComputerDao.getNbComputer();
  }

  public void deleteComputer(long idToDelete) {
    intComputerDao.deleteComputer(idToDelete);
  }

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
   * @return list of item provided by the page
   */
  public List<ComputerDto> getComputerInRange(long idBegin, long nbObjectToGet) {
    List<ComputerDto> listAllComputerDto = new ArrayList<>();
    intComputerDao.getComputerInRange(idBegin, nbObjectToGet).forEach(computer -> {
      listAllComputerDto.add(ComputerToDtoMapper.INSTANCE.toComputerDto(computer));
    });
    return listAllComputerDto;
  }

}
