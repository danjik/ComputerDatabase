package com.java.util;

/**
 * This Exception class is used to represent the exceptions thrown by the
 * application
 */
public class ComputerDBException extends Exception {

	private static final long serialVersionUID = -1133013962801264530L;

	public ComputerDBException() {
		super();
	}

	public ComputerDBException(String message) {
		super(message);
	}

	public ComputerDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComputerDBException(Throwable cause) {
		super(cause);
	}

}
