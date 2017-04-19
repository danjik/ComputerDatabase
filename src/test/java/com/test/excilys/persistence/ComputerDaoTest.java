package com.test.excilys.persistence;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ComputerDaoTest {
  private ApplicationContext context = new ClassPathXmlApplicationContext(
      new String[] { "SpringBeans.xml" });
  private ComputerService computerService = (ComputerService) context.getBean("computerService");
  private CompanyService companyService = (CompanyService) context.getBean("companyService");

  @Test
  public void testGetComputerById() {
    List<ComputerDto> listAllComputer;
    listAllComputer = computerService.getAllComputer();

    ComputerDto randomComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    long idToTest = randomComputer.getId();

    ComputerDto selectComputer = computerService.getComputerById(idToTest);
    assert selectComputer.getId() == idToTest;

  }

  @Test
  public void testGetNbComputer() {
    int nbComputer;
    nbComputer = computerService.getNbComputer(new HashMap<>());
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("Nouveau computer")
        .introduced(emptyStr).discontinued(emptyStr).companyDto(randomCompany).build();
    computerService.createComputer(newComputerDto);
    assert computerService.getNbComputer(new HashMap<>()) == nbComputer + 1;
  }

  @Test
  public void testCreateComputer() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("Nouveau computer")
        .introduced(emptyStr).discontinued(emptyStr).companyDto(randomCompany).build();
    long generateId = computerService.createComputer(newComputerDto);
    newComputerDto.setId(generateId);
    ComputerDto testNewComputerDto = computerService.getComputerById(generateId);
    assert testNewComputerDto.equals(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerNullName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name(null).introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    computerService.createComputer(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerMinCharName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    computerService.createComputer(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerInvalidName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("3&az").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    computerService.createComputer(newComputerDto);
  }

  @Test
  public void testListAllComputer() throws ComputerDbException {
    int nbComputer;
    nbComputer = computerService.getNbComputer(new HashMap<>());

    List<ComputerDto> listComputer = computerService.getAllComputer();

    assert nbComputer == listComputer.size();
  }

  @Test
  public void testDeleteComputer() {
    List<ComputerDto> listAllComputer;
    listAllComputer = computerService.getAllComputer();

    ComputerDto deleteComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    long idToDelete = deleteComputer.getId();
    computerService.deleteComputer(idToDelete);
    assert computerService.getComputerById(idToDelete) == null;

  }

  // @Test
  // public void testUpdateComputer() {
  // SecureRandom random = new SecureRandom();
  //
  // String randAlpha = new BigInteger(50, random).toString(32);
  // List<ComputerDto> listAllComputer;
  // listAllComputer = computerService.getAllComputer();
  //
  // ComputerDto updateComputer = listAllComputer
  // .get((int) (Math.random() * listAllComputer.size()));
  //
  // updateComputer.setName("Update computer " + randAlpha);
  // LocalDate updateDiscontinued = new Timestamp(
  // (long) (random.nextDouble() * new Date().getTime())).toLocalDateTime().toLocalDate();
  // updateComputer.setDiscontinued(updateDiscontinued);
  // LocalDate updateIntroduced = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
  // .toLocalDateTime().toLocalDate();
  // updateComputer.setIntroduced(updateIntroduced);
  // System.out.println(updateComputer);
  //
  // computerService.updateComputer(updateComputer);
  //
  // ComputerDto newUpdateComputer = computerService
  // .getComputerById(updateComputer.getId());
  // updateComputer.setCompanyDto(newUpdateComputer.getCompanyDto());
  // assert updateComputer.equals(newUpdateComputer);
  // }

  @Test(expected = ComputerDbException.class)
  public void testUpdateWrongCompanyIdComputer() {
    List<ComputerDto> listAllComputer;
    listAllComputer = computerService.getAllComputer();

    ComputerDto updateComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    updateComputer.setName(updateComputer.getName());
    updateComputer.setDiscontinued(updateComputer.getDiscontinued());
    updateComputer.setIntroduced(updateComputer.getIntroduced());
    updateComputer.setCompanyDto(new CompanyDto.Builder().id(1000000).name("").build());
    computerService.updateComputer(updateComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerNullName() {
    List<ComputerDto> listAllComputer;
    listAllComputer = computerService.getAllComputer();

    ComputerDto updateComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    String emptyStr = null;
    updateComputer.setName(null);
    updateComputer.setDiscontinued(emptyStr);
    updateComputer.setIntroduced(emptyStr);
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    updateComputer.setCompanyDto(randomCompany);
    computerService.updateComputer(updateComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerMinCharName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputer = new ComputerDto.Builder().name("").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    computerService.updateComputer(newComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerInvalidName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputer = new ComputerDto.Builder().name("3&<az").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    computerService.updateComputer(newComputer);
  }
}
