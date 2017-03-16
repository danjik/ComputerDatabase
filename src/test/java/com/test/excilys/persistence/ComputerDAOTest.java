package com.test.excilys.persistence;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.main.excilys.model.ComputerDTO;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDBException;

public class ComputerDAOTest {
	private ComputerService computerService = new ComputerService();

	@Test
	public void testGetComputerById() throws ComputerDBException {
		List<ComputerDTO> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		ComputerDTO randomComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		long idToTest = randomComputer.getId();

		ComputerDTO selectComputer = computerService.getComputerById(idToTest);
		assert selectComputer.getId() == idToTest;

	}

	@Test
	public void testGetNbComputer() throws ComputerDBException {
		int nbComputer;
		nbComputer = computerService.getNbComputer();

		ComputerDTO newComputerDTO = new ComputerDTO.Builder().name("Nouveau computer").introduced(null)
				.discontinued(null).companyId(10).build();
		computerService.createComputer(newComputerDTO);
		assert computerService.getNbComputer() == nbComputer + 1;
	}

	@Test
	public void testCreateComputer() throws ComputerDBException {
		ComputerDTO newComputerDTO = new ComputerDTO.Builder().name("Nouveau computer").introduced(null)
				.discontinued(null).companyId(10).build();
		long generateId = computerService.createComputer(newComputerDTO);
		newComputerDTO.setId(generateId);
		ComputerDTO testNewComputerDTO = computerService.getComputerById(generateId);
		assert testNewComputerDTO.equals(newComputerDTO);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerNullName() throws ComputerDBException {
		ComputerDTO newComputerDTO = new ComputerDTO.Builder().name(null).introduced(null).discontinued(null)
				.companyId(10).build();
		computerService.createComputer(newComputerDTO);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerMinCharName() throws ComputerDBException {
		ComputerDTO newComputerDTO = new ComputerDTO.Builder().name("aa").introduced(null).discontinued(null)
				.companyId(10).build();
		computerService.createComputer(newComputerDTO);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerInvalidName() throws ComputerDBException {
		ComputerDTO newComputerDTO = new ComputerDTO.Builder().name("3az").introduced(null).discontinued(null)
				.companyId(10).build();
		computerService.createComputer(newComputerDTO);
	}

	@Test
	public void testListAllComputer() throws ComputerDBException {
		int nbComputer;
		nbComputer = computerService.getNbComputer();

		List<ComputerDTO> listComputer = computerService.getAllComputer();

		assert nbComputer == listComputer.size();
	}

	@Test
	public void testDeleteComputer() throws ComputerDBException {
		List<ComputerDTO> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		ComputerDTO deleteComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		long idToDelete = deleteComputer.getId();
		computerService.deleteComputer(idToDelete);
		computerService.getComputerById(idToDelete);
		assert computerService.getComputerById(idToDelete) == null;

	}

	@Test
	public void testUpdateComputer() throws ComputerDBException {
		SecureRandom random = new SecureRandom();

		String randAlpha = new BigInteger(50, random).toString(32);
		List<ComputerDTO> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		ComputerDTO updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));

		updateComputer.setName("Update computer " + randAlpha);
		LocalDate updateDiscontinued = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
				.toLocalDateTime().toLocalDate();
		updateComputer.setDiscontinued(updateDiscontinued);
		LocalDate updateIntroduced = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
				.toLocalDateTime().toLocalDate();
		updateComputer.setIntroduced(updateIntroduced);
		updateComputer.setCompanyId(10);
		computerService.updateComputer(updateComputer);
		ComputerDTO newUpdateComputer = computerService.getComputerById(updateComputer.getId());
		assert updateComputer.equals(newUpdateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateWrongCompanyIdComputer() throws ComputerDBException {
		List<ComputerDTO> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		ComputerDTO updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		updateComputer.setName(updateComputer.getName());
		updateComputer.setDiscontinued(updateComputer.getDiscontinued());
		updateComputer.setIntroduced(updateComputer.getIntroduced());
		updateComputer.setCompanyId(1000000);
		computerService.updateComputer(updateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerNullName() throws ComputerDBException {
		List<ComputerDTO> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		ComputerDTO updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		updateComputer.setName(null);
		updateComputer.setDiscontinued(null);
		updateComputer.setIntroduced(null);
		updateComputer.setCompanyId(10);
		computerService.updateComputer(updateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerMinCharName() throws ComputerDBException {
		ComputerDTO newComputer = new ComputerDTO.Builder().name("aa").introduced(null).discontinued(null).companyId(10)
				.build();
		computerService.updateComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerInvalidName() throws ComputerDBException {
		ComputerDTO newComputer = new ComputerDTO.Builder().name("3az").introduced(null).discontinued(null)
				.companyId(10).build();
		computerService.updateComputer(newComputer);
	}
}
