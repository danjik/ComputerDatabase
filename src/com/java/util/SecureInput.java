package com.java.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SecureInput {
	private static Scanner scan = new Scanner(System.in);
	private static Logger logger = LogManager.getRootLogger();

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
				throw new ComputerDBException("secureInputDate parsing error " + e.getMessage());
			}

		}
		return secureInt;
	}

	public static long secureInputLong(String label) throws ComputerDBException {
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
				throw new ComputerDBException("secureInputDate parsing error " + e.getMessage());
			}
		}
		return secureLong;
	}

	public static String secureInputString(String label) {
		String secureString;
		System.out.print("Which " + label + " ? ");
		secureString = scan.nextLine();
		return secureString;

	}

	public static Date secureInputDate(String label) throws ComputerDBException {
		Date secureDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
		String strSecureDate;
		Calendar cal = Calendar.getInstance();
		do {

			do {
				strSecureDate = secureInputString(label + "(dd/MM/yyyy) and year > 1970");
			} while (!strSecureDate.matches(regex) && !strSecureDate.isEmpty());
			if (!strSecureDate.isEmpty()) {
				try {
					secureDate = sdf.parse(strSecureDate);
					cal.setTime(secureDate);
				} catch (ParseException e) {
					logger.error("secureInputDate : " + e.getMessage());
					throw new ComputerDBException("secureInputDate " + e.getMessage());
				}
			} else {
				secureDate = null;
			}
			System.out.println(cal.get(Calendar.YEAR) < 1970 && secureDate != null);
		} while (cal.get(Calendar.YEAR) < 1970 && secureDate != null);
		return secureDate;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		scan.close();
	}

}
