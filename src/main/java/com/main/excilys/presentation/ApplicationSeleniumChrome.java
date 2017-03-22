package com.main.excilys.presentation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ApplicationSeleniumChrome {
  /**
   * Method main.
   *
   * @param args
   *          args
   */

  public static void main(String[] args) {
    System.setProperty("webdriver.chrome.driver", "lib/chromedriver");
    WebDriver driver = new ChromeDriver();
    driver.get("localhost:8080/ComputerDatabase");
  }

}
