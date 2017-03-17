package com.main.excilys.presentation;

import com.main.excilys.util.ComputerDbException;

public class Application {

  /**
   * Main method.
   *
   * @param args
   *          args
   */

  public static void main(String[] args) {
    int choice = 0;
    long id;
    try {
      ComputerPresentation computerView = new ComputerPresentation();
      CompanyPresentation companyView = new CompanyPresentation();
      do {
        try {
          printMenu();
          choice = SecureInput.secureInputInt("choice");
          switch (choice) {
            case 1 :
              id = SecureInput.secureInputLong("id");
              computerView.printComputerDetails(id);
              break;
            case 2 :
              computerView.createComputer();
              break;
            case 3 :
              computerView.countComputer();
              break;
            case 4 :
              computerView.listAllComputer();
              break;
            case 5 :

              id = SecureInput.secureInputLong("id");
              computerView.deleteComputer(id);
              break;
            case 6 :
              id = SecureInput.secureInputLong("id");
              computerView.updateComputer(id);
              break;
            case 7 :
              computerView.listComputerByPage();
              break;
            case 8 :
              companyView.listAllCompany();
              break;

            case 9 :
              companyView.listCompanyByPage();
              break;

            default :
              break;
          }
        } catch (ComputerDbException e) {
          System.out.println(e.getMessage());
        }
      } while (choice != 10);
    } catch (ComputerDbException e1) {
      System.out.println(e1.getMessage());
    }
  }
  /**
   * Method to print the menu of the app.
   */

  public static void printMenu() {
    System.out.println("==============================");
    System.out.println("= 1 - Show a computer details");
    System.out.println("= 2 - Create a new computer");
    System.out.println("= 3 - Count the computer");
    System.out.println("= 4 - List all computers");
    System.out.println("= 5 - Delete a computer");
    System.out.println("= 6 - Update a computer");
    System.out.println("= 7 - List page of computer");
    System.out.println("= 8 - List all companies");
    System.out.println("= 9 - List page of company");
    System.out.println("= 10 - Exit");
    System.out.println("==============================");
  }
}
