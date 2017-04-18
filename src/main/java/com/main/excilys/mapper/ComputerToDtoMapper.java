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

    return new Computer.Builder().id(computerDto.getId()).name(computerDto.getName())
        .introduced(introduced).discontinued(discontinued)
        .company(CompanyToDtoMapper.toCompany(computerDto.getCompanyDto())).build();
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
    return new ComputerDto.Builder().id(computer.getId()).name(computer.getName())
        .introduced(StringToLocalDateMapper.localDateToStringMapper(computer.getIntroduced()))
        .discontinued(StringToLocalDateMapper.localDateToStringMapper(computer.getDiscontinued()))
        .companyDto(CompanyToDtoMapper.toCompanyDto(computer.getCompany())).build();
  }
}
