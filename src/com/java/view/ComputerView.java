package com.java.view;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.Computer;
import com.java.model.Page;
import com.java.persistence.ComputerDAO;
import com.java.util.ComputerDBException;
import com.java.util.SecureInput;

public class ComputerView {
	private Page<Computer> pageComputer;
	private Logger logger;

	/**
	 * The required connection can be obtained by the static method
	 * ConnectionDB.getInstance
	 *
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public ComputerView() throws ComputerDBException {
		pageComputer = new Page<>(ComputerDAO.INSTANCE.getAllComputer());
		logger = LogManager.getRootLogger();
	}

	public void printComputerDetails(long idToSelect) throws ComputerDBException {
		logger.debug("Access to computer n°" + idToSelect);
		Computer computer = ComputerDAO.INSTANCE.getComputerById(idToSelect);
		if (computer == null) {
			logger.debug("No computer has this id");
			System.out.println("No computer has this id");
		} else {
			logger.debug("Computer found : " + computer);
			System.out.println(computer);
		}

	}

	public void createComputer() throws ComputerDBException {
		Computer newComputer = new Computer();
		String regexName = "^[a-zA-Z][a-zA-Z .-][a-zA-Z .-]+$";
		String name;
		do {
			name = SecureInput.secureInputString("name");
			if (!name.matches(regexName))
				System.out.println("The name must be composed at least by 3 chars");
		} while (!name.matches(regexName));
		Date discontinued = SecureInput.secureInputDate("discontinued");
		Date introduced = SecureInput.secureInputDate("introduced");
		int companyId = SecureInput.secureInputInt("company id");
		newComputer.setName(name);
		newComputer.setDiscontinued(discontinued);
		newComputer.setIntroduced(introduced);
		newComputer.setCompanyId(companyId);
		long id = ComputerDAO.INSTANCE.createComputer(newComputer);
		logger.debug("createComputer : Computer " + newComputer + " well created with id : " + id);
		this.updatePage();
		System.out.println("Computer n°" + id + " well created");
	}

	public void countComputer() throws ComputerDBException {
		int nbComputer = ComputerDAO.INSTANCE.getNbComputer();
		logger.debug("countComputer : " + nbComputer + " has been counted");
		System.out.println("There is " + nbComputer + " computer inside the database");
	}

	public void listAllComputer() throws ComputerDBException {
		List<Computer> listComputer = ComputerDAO.INSTANCE.getAllComputer();
		listComputer.forEach(computer -> {
			System.out.println(computer);
		});
	}

	public void listComputerByPage() throws ComputerDBException {
		System.out.println("There is currently " + pageComputer.getNbPage() + " page");
		int page;
		do {
			page = SecureInput.secureInputInt("page");
		} while (page < 0 || page > pageComputer.getNbPage());
		logger.debug("Access to company page n°" + page);
		pageComputer.getPageInRange(page).forEach(computer -> System.out.println(computer));

	}

	public void deleteComputer(long idToDelete) throws ComputerDBException {
		logger.debug("deleteComputer : idToDelete = " + idToDelete);
		Computer computer = ComputerDAO.INSTANCE.getComputerById(idToDelete);
		if (computer == null) {
			logger.debug("deleteComputer : No computer has this id : " + idToDelete);
			System.out.println("No computer has this id : " + idToDelete);

		} else {
			ComputerDAO.INSTANCE.deleteComputer(idToDelete);
			this.updatePage();
			logger.debug("The computer n°" + idToDelete + " has been well deleted");
			System.out.println("Computer n°" + idToDelete + " has been deleted");
		}

	}

	public void updateComputer(long idToUpdate) throws ComputerDBException {
		logger.debug("updateComputer : idToUpdate = " + idToUpdate);
		Computer computer = ComputerDAO.INSTANCE.getComputerById(idToUpdate);
		if (computer == null) {
			logger.debug("updateComputer : No computer has this id : " + idToUpdate);
			System.out.println("No computer has this id : " + idToUpdate);

		} else {
			int choix = 0;
			do {
				System.out.println("Which value of the computer N°" + idToUpdate + " you want to change ?");
				System.out.println(" 1 - name : " + computer.getName());
				System.out.println(" 2 - discontinued : " + computer.getDiscontinued());
				System.out.println(" 3 - introduced : " + computer.getIntroduced());
				System.out.println(" 4 - company id : " + computer.getCompanyId());
				System.out.println(" 5 - finish");

				choix = SecureInput.secureInputInt("choice");
				switch (choix) {
				case 1:
					String newName;
					do {
						newName = SecureInput.secureInputString("name");
						if (!newName.matches(newName))
							System.out.println("The name must be composed at least by 3 chars");
					} while (!newName.matches(newName));
					computer.setName(newName);
					break;
				case 2:
					Date dateNewDiscontinued = SecureInput.secureInputDate("new discontinued");
					computer.setDiscontinued(dateNewDiscontinued);
					break;
				case 3:
					Date dateNewIntroduced = SecureInput.secureInputDate("new introduced");
					computer.setIntroduced(dateNewIntroduced);
					break;
				case 4:
					int newCompanyId = SecureInput.secureInputInt("new company id");
					computer.setCompanyId(newCompanyId);
					break;
				default:
					break;
				}
				if (choix < 5) {

					ComputerDAO.INSTANCE.updateComputer(computer);
					logger.debug("Computer n°" + idToUpdate + " has been modified to : " + computer);
					this.updatePage();
					System.out.println("Computer N°" + idToUpdate + " updated !");
				}
			} while (choix != 5);
		}
	}

	public void updatePage() throws ComputerDBException {
		logger.debug("Computer page updated");
		pageComputer.updatePage(ComputerDAO.INSTANCE.getAllComputer());
	}
}
