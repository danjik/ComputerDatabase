package com.main.excilys.mapper;

import com.main.excilys.model.Computer;
import com.main.excilys.model.ComputerDto;

import java.time.LocalDate;

public class ComputerToDtoMapper {
  /**
   * Pass a computerDto to a computer.
   *
   * @param computerDto
   *          the data transfers representation of a computer
   * @return the entity computer
   */
  public static Computer toComputer(ComputerDto computerDto) {
    if (computerDto == null) {
      return null;
    }
    LocalDate discontinued = StringToLocalDateMapper
        .strToLocalDateMapper(computerDto.getDiscontinued());

    LocalDate introduced = StringToLocalDateMapper
        .strToLocalDateMapper(computerDto.getIntroduced());

    return new Computer(computerDto.getId(), computerDto.getName(), discontinued, introduced,
        CompanyToDtoMapper.toCompany(computerDto.getCompanyDto()));
  }

  /**
   * Pass a computerDto to a computerDto to a computer.
   *
   * @param computer
   *          the computer
   * @return the data transfers representation of a computer
   */
  public static ComputerDto toComputerDto(Computer computer) {
    if (computer == null) {
      return null;
    }
    return new ComputerDto(computer.getId(), computer.getName(),
        StringToLocalDateMapper.localDateToStringMapper(computer.getDiscontinued()),
        StringToLocalDateMapper.localDateToStringMapper(computer.getIntroduced()),
        CompanyToDtoMapper.toCompanyDto(computer.getCompany()));
  }
}
