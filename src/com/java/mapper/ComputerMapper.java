package com.java.mapper;

import com.java.model.Computer;
import com.java.model.ComputerDTO;

public enum ComputerMapper {
	INSTANCE;

	public Computer toComputer(ComputerDTO computerDTO) {
		return new Computer.Builder().id(computerDTO.getId()).name(computerDTO.getName())
				.introduced(computerDTO.getIntroduced()).discontinued(computerDTO.getDiscontinued())
				.companyId(computerDTO.getCompanyId()).build();
	}

	public ComputerDTO toComputerDTO(Computer computer) {
		return new ComputerDTO.Builder().id(computer.getId()).name(computer.getName())
				.introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued())
				.companyId(computer.getCompanyId()).build();
	}
}
