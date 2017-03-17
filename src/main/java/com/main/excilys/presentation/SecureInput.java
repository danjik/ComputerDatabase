package com.main.excilys.presentation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.main.excilys.util.ComputerDBException;

public class SecureInput {
	private static Scanner scan = new Scanner(System.in);
	private static Logger logger = LogManager.getRootLogger();

	/**
	 * Get a secure valid positive int
	 *
	 * @param label
	 *            the label to print
	 * @return valid positive int
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public static int secureInputInt(String label) throws ComputerDBException {
		int secureInt = -1;
		String strSecureInt;
		String regex = "^[0-9]+$";
		do {
			strSecureInt = secureInputString(label);
		} while (!strSecureInt.matches(regex) || strSecureInt.isEmpty());
		if (!strSecureInt.equals("")) {
			try {
				secureInt = Integer.valueOf(strSecureInt);
			} catch (NumberFormatException e) {
				logger.error("secureInputInt : " + e.getMessage());
				throw new ComputerDBException(
						"secureInputDate parsing error " + e);
			}

		}
		return secureInt;
	}

	/**
	 * Get a secure valid positive long
	 *
	 * @param label
	 *            the label to print
	 * @return valid positive long
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public static long secureInputLong(String label)
			throws ComputerDBException {
		long secureLong = -1;
		String strSecureLong;
		String regex = "^[0-9]+$";
		do {
			strSecureLong = secureInputString(label);
		} while (!strSecureLong.matches(regex) || strSecureLong.isEmpty());
		if (!strSecureLong.equals("")) {
			try {

				secureLong = Long.valueOf(strSecureLong);
			} catch (NumberFormatException e) {
				logger.error("secureInputLong : " + e.getMessage());
				throw new ComputerDBException(
						"secureInputDate parsing error " + e);
			}
		}
		return secureLong;
	}

	/**
	 * Get a secure valid string
	 *
	 * @param label
	 *            the label to print
	 * @return valid positive string
	 */
	public static String secureInputString(String label) {
		String secureString;
		System.out.print("Which " + label + " ? ");
		secureString = scan.nextLine();
		return secureString;

	}

	/**
	 * Get a secure valid date match dd/MM/yyyy
	 *
	 * @param label
	 *            the label to print
	 * @return valid positive date match dd/MM/yyyy
	 * @throws ComputerDBException
	 *             Application Exception
	 */
	public static LocalDate secureInputDate(String label)
			throws ComputerDBException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate secureDate = null;
		String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
		String strSecureDate;
		do {

			do {
				strSecureDate = secureInputString(
						label + "(dd/MM/yyyy) and year > 1970");
			} while (!strSecureDate.matches(regex) && !strSecureDate.isEmpty());
			if (!strSecureDate.isEmpty()) {
				try {
					secureDate = LocalDate.parse(strSecureDate, dtf);
				} catch (DateTimeParseException e) {
					logger.error("secureInputDate : " + e.getMessage());
					throw new ComputerDBException("secureInputDate " + e);
				}
			} else {
				secureDate = null;
			}
		} while (secureDate != null && secureDate.getYear() < 1970);
		return secureDate;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		scan.close();
	}

}
