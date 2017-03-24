package com.test.excilys.presentation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
  private static WebDriver driver;
  private final String homeDir = "localhost:8080/ComputerDatabase";
  private WebElement element;

  /**
   * Setup the class test.
   */
  @BeforeClass
  public static void setUp() {
    System.setProperty("webdriver.chrome.driver", "lib/chromedriver");

    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
  }

  @AfterClass
  public static void tearDown() {
    driver.close();
  }

  @Test
  public void testOpenIndex() {
    driver.get(homeDir);
    element = driver.findElement(By.tagName("body"));
    Assert.assertNotNull(element);
  }

  @Test
  public void testNavigateToDashboard() {
    driver.get(homeDir);
    driver.findElement(By.id("goToDashboard")).click();
    element = driver.findElement(By.id("dashboard"));
    Assert.assertNotNull(element);
  }

  @Test
  public void testOpenDashboard() {
    driver.navigate().to(homeDir + "/dashboard");

    element = driver.findElement(By.tagName("body"));
    Assert.assertNotNull(element);
  }

  @Test
  public void testAddComputerOnlyName() {
    driver.navigate().to(homeDir + "/addComputer");
    element = driver.findElement(By.id("computerName"));
    SecureRandom random = new SecureRandom();
    String computerName = new BigInteger(50, random).toString(32);
    element.sendKeys(computerName);
    driver.findElement(By.id("submitForm")).click();
    driver.navigate().to(homeDir + "/dashboard");
    driver.findElement(By.id("last")).click();
    List<WebElement> list = driver.findElement(By.id("tableDashboard"))
        .findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);
    driver.findElement(By.id("editComputer")).click();
    Assert.assertEquals(element.findElement(By.className("labelComputerName")).getText(),
        computerName);
    element.findElement(By.className("cb")).click();
    driver.findElement(By.id("deleteSelected")).click();
    Alert alert = driver.switchTo().alert();
    alert.accept();
    list = driver.findElement(By.id("tableDashboard")).findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);

    Assert.assertNotEquals(element.findElement(By.className("labelComputerName")).getText(),
        computerName);

  }

  @Test
  public void testAddComputerAllField() {
    driver.navigate().to(homeDir + "/addComputer");

    SecureRandom random = new SecureRandom();
    LocalDate addIntroduced = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
        .toLocalDateTime().toLocalDate();
    LocalDate addDiscontinued = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
        .toLocalDateTime().toLocalDate();
    String computerName = new BigInteger(50, random).toString(32);
    this.fillFormWithValues(computerName, addIntroduced, addDiscontinued);

    driver.navigate().to(homeDir + "/dashboard");
    driver.findElement(By.id("last")).click();
    List<WebElement> list = driver.findElement(By.id("tableDashboard"))
        .findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);
    driver.findElement(By.id("editComputer")).click();
    Assert.assertEquals(element.findElement(By.className("labelComputerName")).getText(),
        computerName);
    Assert.assertEquals(element.findElement(By.className("labelIntroduced")).getText(),
        addIntroduced.toString());
    Assert.assertEquals(element.findElement(By.className("labelDiscontinued")).getText(),
        addDiscontinued.toString());
    element.findElement(By.className("cb")).click();
    driver.findElement(By.id("deleteSelected")).click();
    Alert alert = driver.switchTo().alert();
    alert.accept();
    list = driver.findElement(By.id("tableDashboard")).findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);

    Assert.assertNotEquals(element.findElement(By.className("labelComputerName")).getText(),
        computerName);
  }

  @Test
  public void testUpdateComputerAllField() {
    driver.navigate().to(homeDir + "/dashboard");
    driver.findElement(By.id("last")).click();
    List<WebElement> list = driver.findElement(By.id("tableDashboard"))
        .findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);
    driver.findElement(By.id("editComputer")).click();

    final String oldName = element.findElement(By.className("labelComputerName")).getText();
    final String oldIntroduced = element.findElement(By.className("labelIntroduced")).getText();
    final String oldDiscontinued = element.findElement(By.className("labelDiscontinued")).getText();
    element.findElement(By.className("labelComputerName")).click();

    SecureRandom random = new SecureRandom();
    LocalDate addIntroduced = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
        .toLocalDateTime().toLocalDate();
    LocalDate addDiscontinued = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
        .toLocalDateTime().toLocalDate();
    String computerName = new BigInteger(50, random).toString(32);
    this.fillFormWithValues(computerName, addIntroduced, addDiscontinued);

    driver.navigate().to(homeDir + "/dashboard");
    driver.findElement(By.id("last")).click();
    list = driver.findElement(By.id("tableDashboard")).findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);
    driver.findElement(By.id("editComputer")).click();
    Assert.assertEquals(element.findElement(By.className("labelComputerName")).getText(),
        computerName);
    Assert.assertEquals(element.findElement(By.className("labelIntroduced")).getText(),
        addIntroduced.toString());
    Assert.assertEquals(element.findElement(By.className("labelDiscontinued")).getText(),
        addDiscontinued.toString());

    element.findElement(By.className("labelComputerName")).click();

    this.fillFormWithValues(oldName, LocalDate.parse(oldIntroduced),
        LocalDate.parse(oldDiscontinued));

    driver.navigate().to(homeDir + "/dashboard");
    driver.findElement(By.id("last")).click();
    list = driver.findElement(By.id("tableDashboard")).findElements(By.tagName("tr"));
    element = list.get(list.size() - 1);
    driver.findElement(By.id("editComputer")).click();
    Assert.assertEquals(element.findElement(By.className("labelComputerName")).getText(), oldName);
    Assert.assertEquals(element.findElement(By.className("labelIntroduced")).getText(),
        oldIntroduced);
    Assert.assertEquals(element.findElement(By.className("labelDiscontinued")).getText(),
        oldDiscontinued);

  }

  /**
   * Fill the edit or add form with the values.
   *
   * @param computerName
   *          Random name
   * @param addIntroduced
   *          Random LocalDate
   * @param addDiscontinued
   *          Random LocalDate
   */
  public void fillFormWithValues(String computerName, LocalDate addIntroduced,
      LocalDate addDiscontinued) {

    String strAddIntroduced = addIntroduced.toString();
    String strAddDiscontinued = addDiscontinued.toString();
    String[] splitDate = strAddIntroduced.split("-");
    strAddIntroduced = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
    splitDate = strAddDiscontinued.split("-");
    strAddDiscontinued = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys(computerName);
    driver.findElement(By.id("introduced")).sendKeys(strAddIntroduced);
    driver.findElement(By.id("discontinued")).sendKeys(strAddDiscontinued);
    driver.findElement(By.id("submitForm")).click();
  }

}
