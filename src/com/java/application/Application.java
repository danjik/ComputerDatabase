package com.java.application;

import java.sql.SQLException;

import com.java.model.ConnectionDB;
import com.java.util.ComputerDBException;
import com.java.util.SecureInput;
import com.java.view.CompanyView;
import com.java.view.ComputerView;

public class Application {
	public static void main(String[] args) {
		int choice = 0;
		long id;
		try {
			ConnectionDB connectionDB = ConnectionDB.getInstance();
			ComputerView computerView = new ComputerView(connectionDB);
			CompanyView companyView = new CompanyView(connectionDB);
			do {
				try {
					printMenu();
					choice = SecureInput.secureInputInt("choice");
					switch (choice) {
					case 1:
						id = SecureInput.secureInputLong("id");
						computerView.printComputerDetails(id);
						break;
					case 2:
						computerView.createComputer();
						break;
					case 3:
						computerView.countComputer();
						break;
					case 4:
						computerView.listAllComputer();
						break;
					case 5:

						id = SecureInput.secureInputLong("id");
						computerView.deleteComputer(id);
						break;
					case 6:
						id = SecureInput.secureInputLong("id");
						computerView.updateComputer(id);
						break;
					case 7:
						computerView.listComputerByPage();
						break;
					case 8:
						companyView.listAllCompany();
						break;

					case 9:
						companyView.listCompanyByPage();
						break;

					default:
						break;
					}
				} catch (ComputerDBException e) {
					System.out.println(e.getMessage());
				}
			} while (choice != 10);

			connectionDB.getConnection().close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ComputerDBException e1) {
			System.out.println(e1.getMessage());
		}
	}

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
