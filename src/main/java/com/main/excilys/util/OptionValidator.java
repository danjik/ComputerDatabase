package com.main.excilys.util;

import java.util.Map;
import java.util.stream.Stream;

public enum OptionValidator {
  INSTANCE;

  /**
   * Method to validate that the options given to the DAO is correct. Correct values are search :
   * [String : [a-zA-Z][a-zA-Z -.][a-zA-Z -.]+]. sort : computer.name, computer.introduced,
   * computer.discontinued, company.name.
   *
   * @param options
   *          A map of options given to the DAO
   *
   */
  public static final void validate(Map<String, String> options) {
    options.forEach((key, value) -> {
      switch (key) {
        case "search" :
          if (value != null && !value.isEmpty() && !value.matches("^[a-zA-Z -.]+$")) {
            options.remove(key);
            throw new ComputerDbException("The search value : " + value + " is incorrect");
          }
          break;
        case "sort" :
          String[] correctOptions = { "computer.name", "computer.introduced",
              "computer.discontinued", "company.name" };
          if (!Stream.of(correctOptions).anyMatch(str -> (str + " asc").equals(value))
              && !Stream.of(correctOptions).anyMatch(str -> (str + " desc").equals(value))) {
            options.remove(key);
            throw new ComputerDbException("The sort value : " + value + " is incorrect");
          }
          break;

        default :
          break;
      }
    });
  }
}
