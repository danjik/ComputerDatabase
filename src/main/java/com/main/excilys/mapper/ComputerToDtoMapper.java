package com.main.excilys.mapper;

import com.main.excilys.model.Computer;
import com.main.excilys.model.ComputerDto;

public enum ComputerToDtoMapper {
  INSTANCE;
  /**
   * Pass a computerDto to a computer.
   *
   * @param computerDto
   *          the data transfert representation of a computer
   * @return the entity computer
   */
  public Computer toComputer(ComputerDto computerDto) {
    if (computerDto == null) {
      return null;
    }
    return new Computer.Builder().id(computerDto.getId()).name(computerDto.getName())
        .introduced(computerDto.getIntroduced()).discontinued(computerDto.getDiscontinued())
        .company(CompanyToDtoMapper.INSTANCE.toCompany(computerDto.getCompanyDto())).build();
  }

  /**
   * Pass a computerDto to a computerDto to a computer.
   *
   * @param computer
   *          the computer
   * @return the data transfert representation of a computer
   */
  public ComputerDto toComputerDto(Computer computer) {
    if (computer == null) {
      return null;
    }
    return new ComputerDto.Builder().id(computer.getId()).name(computer.getName())
        .introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued())
        .companyDto(CompanyToDtoMapper.INSTANCE.toCompanyDto(computer.getCompany())).build();
  }
}
