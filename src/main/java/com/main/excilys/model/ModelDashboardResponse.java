package com.main.excilys.model;

import com.main.excilys.service.ComputerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelDashboardResponse {

  private Map<String, String> options = new HashMap<>();

  @Autowired
  private Page pageComputerDto;

  private List<ComputerDto> listComputerDto = new ArrayList<>();
  private final List<Integer> nbObjectAvailablePerPage = Arrays.asList(10, 50, 100);

  @Autowired
  private ComputerService computerService;

  public ModelDashboardResponse() {
    super();

  }

  public void fill(HttpServletRequest req, HttpServletResponse resp) {
    this.doSetAttribute(req);
    this.setListComputer(req);
  }

  private void setListComputer(HttpServletRequest req) {
    listComputerDto = computerService.getComputerInRange(
        pageComputerDto.getNumPage() * pageComputerDto.getNbObjectPerPage(),
        pageComputerDto.getNbObjectPerPage(), options);
  }

  private void doSetAttribute(HttpServletRequest req) {
    this.doSetOptions(req);
    this.doSetNbComputer(req);
    this.doSetPage(req);
  }

  private void doSetOptions(HttpServletRequest req) {
    String search = req.getParameter("search") != null ? req.getParameter("search") : "";
    options.put("search", search);
    String column = req.getParameter("column") != null ? req.getParameter("column") : "";
    options.put("column", column);
  }

  private void doSetNbComputer(HttpServletRequest req) {
    int nbObjectPerPage = req != null && req.getParameter("nbObject") != null
        && !req.getParameter("nbObject").isEmpty()
        && req.getParameter("nbObject").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("nbObject"))
            : 10;
    pageComputerDto.setNbObjectPerPage(nbObjectPerPage);
    pageComputerDto.setNumPage(0);
  }

  private void doSetPage(HttpServletRequest req) {
    int switchPage = req != null && req.getParameter("page") != null
        && !req.getParameter("page").isEmpty() && req.getParameter("page").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("page"))
            : 0;
    if (switchPage >= 0 && switchPage - 1 < pageComputerDto.getMaxPage()) {
      pageComputerDto.setNumPage(switchPage);
    }
  }

  public Map<String, String> getOptions() {
    return options;
  }

  public Page getPageComputerDto() {
    return pageComputerDto;
  }

  public List<ComputerDto> getListComputerDto() {
    return listComputerDto;
  }

  public List<Integer> getNbObjectAvailablePerPage() {
    return nbObjectAvailablePerPage;
  }

}
