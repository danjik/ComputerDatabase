package com.main.excilys.util;

/**
 * This Exception class is used to represent the exceptions thrown by the application.
 */
public class ComputerDbException extends RuntimeException {

  private static final long serialVersionUID = -1133013962801264530L;

  public ComputerDbException() {
    super();
  }

  public ComputerDbException(String message) {
    super(message);
  }

  public ComputerDbException(String message, Throwable cause) {
    super(message, cause);
  }

  public ComputerDbException(Throwable cause) {
    super(cause);
  }

}
