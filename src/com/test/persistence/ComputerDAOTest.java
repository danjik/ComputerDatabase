package com.test.persistence;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.java.model.Computer;
import com.java.service.ComputerService;
import com.java.util.ComputerDBException;

public class ComputerDAOTest {
	private ComputerService computerService = new ComputerService();

	@Test
	public void testGetComputerById() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		Computer randomComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		long idToTest = randomComputer.getId();

		Computer selectComputer = computerService.getComputerById(idToTest);
		assert selectComputer.getId() == idToTest;

	}

	@Test
	public void testGetNbComputer() throws ComputerDBException {
		int nbComputer;
		nbComputer = computerService.getNbComputer();

		Computer newComputer = new Computer.Builder().name("Nouveau computer").introduced(null).discontinued(null)
				.companyId(10).build();
		computerService.createComputer(newComputer);
		assert computerService.getNbComputer() == nbComputer + 1;
	}

	@Test
	public void testCreateComputer() throws ComputerDBException {
		Computer newComputer = new Computer.Builder().name("Nouveau computer").introduced(null).discontinued(null)
				.companyId(10).build();
		long generateId = computerService.createComputer(newComputer);
		newComputer.setId(generateId);
		Computer testNewComputer = computerService.getComputerById(generateId);
		assert testNewComputer.equals(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerNullName() throws ComputerDBException {
		Computer newComputer = new Computer.Builder().name(null).introduced(null).discontinued(null).companyId(10)
				.build();
		computerService.createComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerMinCharName() throws ComputerDBException {
		Computer newComputer = new Computer.Builder().name("aa").introduced(null).discontinued(null).companyId(10)
				.build();
		computerService.createComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerInvalidName() throws ComputerDBException {
		Computer newComputer = new Computer.Builder().name("3az").introduced(null).discontinued(null).companyId(10)
				.build();
		computerService.createComputer(newComputer);
	}

	@Test
	public void testListAllComputer() throws ComputerDBException {
		int nbComputer;
		nbComputer = computerService.getNbComputer();

		List<Computer> listComputer = computerService.getAllComputer();

		assert nbComputer == listComputer.size();
	}

	@Test
	public void testDeleteComputer() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		Computer deleteComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		long idToDelete = deleteComputer.getId();
		computerService.deleteComputer(idToDelete);
		assert computerService.getComputerById(idToDelete) == null;
	}

	@Test
	public void testUpdateComputer() throws ComputerDBException {
		SecureRandom random = new SecureRandom();

		String randAlpha = new BigInteger(50, random).toString(32);
		List<Computer> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		Computer updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));

		updateComputer.setName("Update computer " + randAlpha);
		LocalDate updateDiscontinued = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
				.toLocalDateTime().toLocalDate();
		updateComputer.setDiscontinued(updateDiscontinued);
		LocalDate updateIntroduced = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
				.toLocalDateTime().toLocalDate();
		updateComputer.setIntroduced(updateIntroduced);
		updateComputer.setCompanyId(10);
		computerService.updateComputer(updateComputer);
		Computer newUpdateComputer = computerService.getComputerById(updateComputer.getId());
		assert updateComputer.equals(newUpdateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateWrongCompanyIdComputer() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		Computer updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		updateComputer.setName(updateComputer.getName());
		updateComputer.setDiscontinued(updateComputer.getDiscontinued());
		updateComputer.setIntroduced(updateComputer.getIntroduced());
		updateComputer.setCompanyId(1000000);
		computerService.updateComputer(updateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerNullName() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = computerService.getAllComputer();

		Computer updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		updateComputer.setName(null);
		updateComputer.setDiscontinued(null);
		updateComputer.setIntroduced(null);
		updateComputer.setCompanyId(10);
		computerService.updateComputer(updateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerMinCharName() throws ComputerDBException {
		Computer newComputer = new Computer.Builder().name("aa").introduced(null).discontinued(null).companyId(10)
				.build();
		computerService.updateComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerInvalidName() throws ComputerDBException {
		Computer newComputer = new Computer.Builder().name("3az").introduced(null).discontinued(null).companyId(10)
				.build();
		computerService.updateComputer(newComputer);
	}
}
