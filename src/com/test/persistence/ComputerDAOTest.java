package com.test.persistence;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.java.model.Computer;
import com.java.persistence.ComputerDAO;
import com.java.util.ComputerDBException;

public class ComputerDAOTest {

	@Test
	public void testGetComputerById() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = ComputerDAO.INSTANCE.getAllComputer();

		Computer randomComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		long idToTest = randomComputer.getId();

		Computer selectComputer = ComputerDAO.INSTANCE.getComputerById(idToTest);
		assert selectComputer.getId() == idToTest;

	}

	@Test
	public void testGetNbComputer() throws ComputerDBException {
		int nbComputer;
		nbComputer = ComputerDAO.INSTANCE.getNbComputer();

		Computer newComputer = new Computer();
		newComputer.setName("Nouveau computer");
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.createComputer(newComputer);
		assert ComputerDAO.INSTANCE.getNbComputer() == nbComputer + 1;
	}

	@Test
	public void testCreateComputer() throws ComputerDBException {
		Computer newComputer = new Computer();
		newComputer.setName("Nouveau computer");
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		long generateId = ComputerDAO.INSTANCE.createComputer(newComputer);
		newComputer.setId(generateId);
		Computer testNewComputer = ComputerDAO.INSTANCE.getComputerById(generateId);
		assert testNewComputer.equals(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerNullName() throws ComputerDBException {
		Computer newComputer = new Computer();
		newComputer.setName(null);
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.createComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerMinCharName() throws ComputerDBException {
		Computer newComputer = new Computer();
		newComputer.setName("aa");
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.createComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testCreateComputerInvalidName() throws ComputerDBException {
		Computer newComputer = new Computer();
		newComputer.setName("3az");
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.createComputer(newComputer);
	}

	@Test
	public void testListAllComputer() throws ComputerDBException {
		int nbComputer;
		nbComputer = ComputerDAO.INSTANCE.getNbComputer();

		List<Computer> listComputer = ComputerDAO.INSTANCE.getAllComputer();

		assert nbComputer == listComputer.size();
	}

	@Test
	public void testDeleteComputer() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = ComputerDAO.INSTANCE.getAllComputer();

		Computer deleteComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		long idToDelete = deleteComputer.getId();
		ComputerDAO.INSTANCE.deleteComputer(idToDelete);
		assert ComputerDAO.INSTANCE.getComputerById(idToDelete) == null;
	}

	@Test
	public void testUpdateComputer() throws ComputerDBException {
		SecureRandom random = new SecureRandom();

		String randAlpha = new BigInteger(50, random).toString(32);
		List<Computer> listAllComputer;
		listAllComputer = ComputerDAO.INSTANCE.getAllComputer();

		Computer updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));

		updateComputer.setName("Update computer " + randAlpha);
		Date updateDiscontinued = new Date((long) (random.nextDouble() * new Date().getTime()));
		updateComputer.setDiscontinued(updateDiscontinued);
		Date updateIntroduced = new Date((long) (random.nextDouble() * new Date().getTime()));
		updateComputer.setIntroduced(updateIntroduced);
		updateComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.updateComputer(updateComputer);
		Computer newUpdateComputer = ComputerDAO.INSTANCE.getComputerById(updateComputer.getId());
		assert updateComputer.equals(newUpdateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateWrongCompanyIdComputer() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = ComputerDAO.INSTANCE.getAllComputer();

		Computer updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		updateComputer.setName(updateComputer.getName());
		updateComputer.setDiscontinued(updateComputer.getDiscontinued());
		updateComputer.setIntroduced(updateComputer.getIntroduced());
		updateComputer.setCompanyId(1000000);
		ComputerDAO.INSTANCE.updateComputer(updateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerNullName() throws ComputerDBException {
		List<Computer> listAllComputer;
		listAllComputer = ComputerDAO.INSTANCE.getAllComputer();

		Computer updateComputer = listAllComputer.get((int) (Math.random() * listAllComputer.size()));
		updateComputer.setName(null);
		updateComputer.setDiscontinued(null);
		updateComputer.setIntroduced(null);
		updateComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.updateComputer(updateComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerMinCharName() throws ComputerDBException {
		Computer newComputer = new Computer();
		newComputer.setName("aa");
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.updateComputer(newComputer);
	}

	@Test(expected = ComputerDBException.class)
	public void testUpdateComputerInvalidName() throws ComputerDBException {
		Computer newComputer = new Computer();
		newComputer.setName("3az");
		newComputer.setDiscontinued(null);
		newComputer.setIntroduced(null);
		newComputer.setCompanyId(10);
		ComputerDAO.INSTANCE.updateComputer(newComputer);
	}
}
