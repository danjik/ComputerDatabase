package com.main.excilys.presentation;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.main.excilys.model.ComputerDTO;
import com.main.excilys.model.Page;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDBException;

public class ComputerPresentation {
	private Page<ComputerDTO> pageComputerDTO;
	private Logger logger;
	private ComputerService computerService = new ComputerService();

	/**
	 *
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public ComputerPresentation() throws ComputerDBException {
		pageComputerDTO = new Page<>(computerService.getAllComputer());
		logger = LogManager.getRootLogger();
	}

	public void printComputerDetails(long idToSelect) throws ComputerDBException {
		logger.debug("Access to computer n°" + idToSelect);
		ComputerDTO computerDTO = computerService.getComputerById(idToSelect);
		if (computerDTO == null) {
			logger.debug("No computer has this id");
			System.out.println("No computer has this id");
		} else {
			logger.debug("Computer found : " + computerDTO);
			System.out.println(computerDTO);
		}

	}

	public void createComputer() throws ComputerDBException {
		String regexName = "^[a-zA-Z][a-zA-Z .-][a-zA-Z .-]+$";
		String name;
		do {
			name = SecureInput.secureInputString("name");
			if (!name.matches(regexName))
				System.out.println("The name must be composed at least by 3 chars");
		} while (!name.matches(regexName));
		LocalDate discontinued = SecureInput.secureInputDate("discontinued");
		LocalDate introduced = SecureInput.secureInputDate("introduced");
		int companyId = SecureInput.secureInputInt("company id");
		ComputerDTO newComputerDTO = new ComputerDTO.Builder().name(name).introduced(introduced)
				.discontinued(discontinued).companyId(companyId).build();
		long id = computerService.createComputer(newComputerDTO);
		logger.debug("createComputer : Computer " + newComputerDTO + " well created with id : " + id);
		System.out.println("Computer n°" + id + " well created");
	}

	public void countComputer() throws ComputerDBException {
		int nbComputer = computerService.getNbComputer();
		logger.debug("countComputer : " + nbComputer + " has been counted");
		System.out.println("There is " + nbComputer + " computer inside the database");
	}

	public void listAllComputer() throws ComputerDBException {
		List<ComputerDTO> listComputerDTO = computerService.getAllComputer();
		listComputerDTO.forEach(computerDTO -> {
			System.out.println(computerDTO);
		});
	}

	public void listComputerByPage() throws ComputerDBException {
		logger.debug("Access to company page n°" + pageComputerDTO.getNumPage());
		long idBegin = pageComputerDTO.getNumPage() * Page.NB_OBJECT_PER_PAGE;
		long idEnd = pageComputerDTO.getNumPage() * Page.NB_OBJECT_PER_PAGE + Page.NB_OBJECT_PER_PAGE;
		computerService.getComputerInRange(idBegin, idEnd).forEach(computer -> System.out.println(computer));

	}

	public void deleteComputer(long idToDelete) throws ComputerDBException {
		logger.debug("deleteComputer : idToDelete = " + idToDelete);
		ComputerDTO computer = computerService.getComputerById(idToDelete);
		if (computer == null) {
			logger.debug("deleteComputer : No computer has this id : " + idToDelete);
			System.out.println("No computer has this id : " + idToDelete);

		} else {
			computerService.deleteComputer(idToDelete);
			logger.debug("The computer n°" + idToDelete + " has been well deleted");
			System.out.println("Computer n°" + idToDelete + " has been deleted");
		}

	}

	public void updateComputer(long idToUpdate) throws ComputerDBException {
		logger.debug("updateComputer : idToUpdate = " + idToUpdate);
		ComputerDTO computer = computerService.getComputerById(idToUpdate);
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
					LocalDate dateNewDiscontinued = SecureInput.secureInputDate("new discontinued");
					computer.setDiscontinued(dateNewDiscontinued);
					break;
				case 3:
					LocalDate dateNewIntroduced = SecureInput.secureInputDate("new introduced");
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

					computerService.updateComputer(computer);
					logger.debug("Computer n°" + idToUpdate + " has been modified to : " + computer);
					System.out.println("Computer N°" + idToUpdate + " updated !");
				}
			} while (choix != 5);
		}
	}
}
