package com.main.excilys.presentation;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.model.Page;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComputerPresentation {
  private Page<ComputerDto> pageComputerDto;
  private Logger logger;
  private ComputerService computerService = new ComputerService();

  /**
   * Constructor.
   *
   * @throws ComputerDbException
   *           Application Exception
   */
  public ComputerPresentation() throws ComputerDbException {
    logger = LogManager.getRootLogger();
  }

  /**
   * Print a single computer details.
   *
   * @param idToSelect
   *          the id of the computer.
   * @throws ComputerDbException
   *           exception
   */
  public void printComputerDetails(long idToSelect) throws ComputerDbException {
    logger.debug("Access to computer n°" + idToSelect);
    ComputerDto computerDto = computerService.getComputerById(idToSelect);
    if (computerDto == null) {
      logger.debug("No computer has this id");
      System.out.println("No computer has this id");
    } else {
      logger.debug("Computer found : " + computerDto);
      System.out.println(computerDto);
    }

  }
  /**
   * Creation presentation of a computer.
   *
   * @throws ComputerDbException
   *           exception
   */

  public void createComputer() throws ComputerDbException {
    String regexName = "^[a-zA-Z][a-zA-Z .-][a-zA-Z .-]+$";
    String name;
    do {
      name = SecureInput.secureInputString("name");
      if (!name.matches(regexName)) {
        System.out.println("The name must be composed at least by 3 chars");
      }
    } while (!name.matches(regexName));
    LocalDate discontinued = SecureInput.secureInputDate("discontinued");
    LocalDate introduced = SecureInput.secureInputDate("introduced");
    // TODO replace with a company selection
    ComputerDto newComputerDto = new ComputerDto.Builder().name(name).introduced(introduced)
        .discontinued(discontinued).companyDto(new CompanyDto.Builder().id(10).name("").build())
        .build();
    long id = computerService.createComputer(newComputerDto);
    logger.debug("createComputer : Computer " + newComputerDto + " well created with id : " + id);
    System.out.println("Computer n°" + id + " well created");
  }
  /**
   * Print the number of computer.
   *
   * @throws ComputerDbException
   *           exception
   */

  public void countComputer() throws ComputerDbException {
    int nbComputer = computerService.getNbComputer();
    logger.debug("countComputer : " + nbComputer + " has been counted");
    System.out.println("There is " + nbComputer + " computer inside the database");
  }
  /**
   * Print all the computer.
   *
   * @throws ComputerDbException
   *           exception
   */

  public void listAllComputer() throws ComputerDbException {
    List<ComputerDto> listComputerDto = computerService.getAllComputer();
    listComputerDto.forEach(computerDto -> {
      System.out.println(computerDto);
    });
  }

  /**
   * Print the computer provided by the page.
   *
   * @throws ComputerDbException
   *           exception
   */
  public void listComputerByPage() throws ComputerDbException {
    logger.debug("Access to company page n°" + pageComputerDto.getNumPage());
    long idBegin = pageComputerDto.getNumPage() * Page.getNbObjectPerPage();
    long idEnd = pageComputerDto.getNumPage() * Page.getNbObjectPerPage()
        + Page.getNbObjectPerPage();
    computerService.getComputerInRange(idBegin, idEnd)
        .forEach(computer -> System.out.println(computer));
  }

  /**
   * Presentation of the delete action of a computer.
   *
   * @param idToDelete
   *          id of the computer to delete
   * @throws ComputerDbException
   *           exception
   */
  public void deleteComputer(long idToDelete) throws ComputerDbException {
    logger.debug("deleteComputer : idToDelete = " + idToDelete);
    ComputerDto computer = computerService.getComputerById(idToDelete);
    if (computer == null) {
      logger.debug("deleteComputer : No computer has this id : " + idToDelete);
      System.out.println("No computer has this id : " + idToDelete);

    } else {
      computerService.deleteComputer(idToDelete);
      logger.debug("The computer n°" + idToDelete + " has been well deleted");
      System.out.println("Computer n°" + idToDelete + " has been deleted");
    }

  }

  /**
   * Update presentation of a computer.
   *
   * @param idToUpdate
   *          the id of the asked updated computer
   * @throws ComputerDbException
   *           exception
   */
  public void updateComputer(long idToUpdate) throws ComputerDbException {
    logger.debug("updateComputer : idToUpdate = " + idToUpdate);
    ComputerDto computer = computerService.getComputerById(idToUpdate);
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
        System.out.println(" 4 - company id : " + computer.getCompanyDto().getId());
        System.out.println(" 5 - finish");

        choix = SecureInput.secureInputInt("choice");
        switch (choix) {
          case 1 :
            String newName;
            do {
              newName = SecureInput.secureInputString("name");
              if (!newName.matches(newName)) {
                System.out.println("The name must be composed at least by 3 chars");
              }
            } while (!newName.matches(newName));
            computer.setName(newName);
            break;
          case 2 :
            LocalDate dateNewDiscontinued = SecureInput.secureInputDate("new discontinued");
            computer.setDiscontinued(dateNewDiscontinued);
            break;
          case 3 :
            LocalDate dateNewIntroduced = SecureInput.secureInputDate("new introduced");
            computer.setIntroduced(dateNewIntroduced);
            break;
          case 4 :
            int newCompanyId = SecureInput.secureInputInt("new company id");
            // TODO company
            computer.setCompanyDto(new CompanyDto.Builder().id(newCompanyId).name("").build());
            break;
          default :
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
