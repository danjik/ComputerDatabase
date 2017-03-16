package com.main.excilys.mapper;

import com.main.excilys.model.Computer;
import com.main.excilys.model.ComputerDTO;

public enum ComputerToDTOMapper {
	INSTANCE;

	public Computer toComputer(ComputerDTO computerDTO) {
		if (computerDTO == null)
			return null;
		return new Computer.Builder().id(computerDTO.getId()).name(computerDTO.getName())
				.introduced(computerDTO.getIntroduced()).discontinued(computerDTO.getDiscontinued())
				.companyId(computerDTO.getCompanyId()).build();
	}

	public ComputerDTO toComputerDTO(Computer computer) {
		if (computer == null)
			return null;
		return new ComputerDTO.Builder().id(computer.getId()).name(computer.getName())
				.introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued())
				.companyId(computer.getCompanyId()).build();
	}
}
