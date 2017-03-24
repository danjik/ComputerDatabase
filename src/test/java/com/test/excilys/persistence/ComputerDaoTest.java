package com.test.excilys.persistence;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class ComputerDaoTest {

  @Test
  public void testGetComputerById() {
    List<ComputerDto> listAllComputer;
    listAllComputer = ComputerService.INSTANCE.getAllComputer();

    ComputerDto randomComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    long idToTest = randomComputer.getId();

    ComputerDto selectComputer = ComputerService.INSTANCE.getComputerById(idToTest);
    assert selectComputer.getId() == idToTest;

  }

  @Test
  public void testGetNbComputer() {
    int nbComputer;
    nbComputer = ComputerService.INSTANCE.getNbComputer(new HashMap<>());
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("Nouveau computer")
        .introduced(emptyStr).discontinued(emptyStr).companyDto(randomCompany).build();
    ComputerService.INSTANCE.createComputer(newComputerDto);
    assert ComputerService.INSTANCE.getNbComputer(new HashMap<>()) == nbComputer + 1;
  }

  @Test
  public void testCreateComputer() {
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("Nouveau computer")
        .introduced(emptyStr).discontinued(emptyStr).companyDto(randomCompany).build();
    long generateId = ComputerService.INSTANCE.createComputer(newComputerDto);
    newComputerDto.setId(generateId);
    ComputerDto testNewComputerDto = ComputerService.INSTANCE.getComputerById(generateId);
    assert testNewComputerDto.equals(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerNullName() {
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name(null).introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    ComputerService.INSTANCE.createComputer(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerMinCharName() {
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("aa").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    ComputerService.INSTANCE.createComputer(newComputerDto);
  }

  @Test(expected = ComputerDbException.class)
  public void testCreateComputerInvalidName() {
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputerDto = new ComputerDto.Builder().name("3&az").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    ComputerService.INSTANCE.createComputer(newComputerDto);
  }

  @Test
  public void testListAllComputer() throws ComputerDbException {
    int nbComputer;
    nbComputer = ComputerService.INSTANCE.getNbComputer(new HashMap<>());

    List<ComputerDto> listComputer = ComputerService.INSTANCE.getAllComputer();

    assert nbComputer == listComputer.size();
  }

  @Test
  public void testDeleteComputer() {
    List<ComputerDto> listAllComputer;
    listAllComputer = ComputerService.INSTANCE.getAllComputer();

    ComputerDto deleteComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    long idToDelete = deleteComputer.getId();
    ComputerService.INSTANCE.deleteComputer(idToDelete);
    assert ComputerService.INSTANCE.getComputerById(idToDelete) == null;

  }

  @Test
  public void testUpdateComputer() {
    SecureRandom random = new SecureRandom();

    String randAlpha = new BigInteger(50, random).toString(32);
    List<ComputerDto> listAllComputer;
    listAllComputer = ComputerService.INSTANCE.getAllComputer();

    ComputerDto updateComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));

    updateComputer.setName("Update computer " + randAlpha);
    LocalDate updateDiscontinued = new Timestamp(
        (long) (random.nextDouble() * new Date().getTime())).toLocalDateTime().toLocalDate();
    updateComputer.setDiscontinued(updateDiscontinued);
    LocalDate updateIntroduced = new Timestamp((long) (random.nextDouble() * new Date().getTime()))
        .toLocalDateTime().toLocalDate();
    updateComputer.setIntroduced(updateIntroduced);

    ComputerService.INSTANCE.updateComputer(updateComputer);

    ComputerDto newUpdateComputer = ComputerService.INSTANCE
        .getComputerById(updateComputer.getId());
    updateComputer.setCompanyDto(newUpdateComputer.getCompanyDto());
    assert updateComputer.equals(newUpdateComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateWrongCompanyIdComputer() {
    List<ComputerDto> listAllComputer;
    listAllComputer = ComputerService.INSTANCE.getAllComputer();

    ComputerDto updateComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    updateComputer.setName(updateComputer.getName());
    updateComputer.setDiscontinued(updateComputer.getDiscontinued());
    updateComputer.setIntroduced(updateComputer.getIntroduced());
    updateComputer.setCompanyDto(new CompanyDto.Builder().id(1000000).name("").build());
    ComputerService.INSTANCE.updateComputer(updateComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerNullName() {
    List<ComputerDto> listAllComputer;
    listAllComputer = ComputerService.INSTANCE.getAllComputer();

    ComputerDto updateComputer = listAllComputer
        .get((int) (Math.random() * listAllComputer.size()));
    String emptyStr = null;
    updateComputer.setName(null);
    updateComputer.setDiscontinued(emptyStr);
    updateComputer.setIntroduced(emptyStr);
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    updateComputer.setCompanyDto(randomCompany);
    ComputerService.INSTANCE.updateComputer(updateComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerMinCharName() {
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputer = new ComputerDto.Builder().name("aa").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    ComputerService.INSTANCE.updateComputer(newComputer);
  }

  @Test(expected = ComputerDbException.class)
  public void testUpdateComputerInvalidName() {
    List<CompanyDto> listCompany = CompanyService.INSTANCE.getAllCompany();
    CompanyDto randomCompany = listCompany.get((int) (Math.random() * listCompany.size()));
    String emptyStr = null;
    ComputerDto newComputer = new ComputerDto.Builder().name("3&<az").introduced(emptyStr)
        .discontinued(emptyStr).companyDto(randomCompany).build();
    ComputerService.INSTANCE.updateComputer(newComputer);
  }
}
