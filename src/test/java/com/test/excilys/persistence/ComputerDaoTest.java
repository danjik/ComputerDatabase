package com.test.excilys.persistence;

import com.main.excilys.config.WebInitializer;
import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebInitializer.class)
public class ComputerDaoTest {

  @Autowired
  private ComputerService computerService;

  @Autowired
  private CompanyService companyService;

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
    long nbComputer;
    nbComputer = computerService.getNbComputer(new HashMap<>());
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto(0, "Nouveau computer", emptyStr, emptyStr,
        randomCompany.getId(), randomCompany.getName());
    computerService.createComputer(newComputerDto);
    assert computerService.getNbComputer(new HashMap<>()) == nbComputer + 1;
  }

  @Test
  public void testCreateComputer() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto(0, "Nouveau computer", emptyStr, emptyStr,
        randomCompany.getId(), randomCompany.getName());
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
    ComputerDto newComputerDto = new ComputerDto(0, null, emptyStr, emptyStr, randomCompany.getId(),
        randomCompany.getName());
    computerService.createComputer(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerMinCharName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto(0, "", emptyStr, emptyStr, randomCompany.getId(),
        randomCompany.getName());
    computerService.createComputer(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerInvalidName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto(0, "3&az", emptyStr, emptyStr,
        randomCompany.getId(), randomCompany.getName());
    computerService.createComputer(newComputerDto);
  }

  @Test
  public void testListAllComputer() throws ComputerDbException {
    long nbComputer;
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
    updateComputer.setCompanyId(1000000);
    updateComputer.setCompanyName("");
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
    updateComputer.setCompanyId(randomCompany.getId());
    updateComputer.setCompanyName(randomCompany.getName());
    computerService.updateComputer(updateComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerMinCharName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputer = new ComputerDto(0, "", emptyStr, emptyStr, randomCompany.getId(),
        randomCompany.getName());
    computerService.updateComputer(newComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerInvalidName() {
    List<CompanyDto> listCompany = companyService.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputer = new ComputerDto(0, "3&<az", emptyStr, emptyStr, randomCompany.getId(),
        randomCompany.getName());
    computerService.updateComputer(newComputer);
  }
}
