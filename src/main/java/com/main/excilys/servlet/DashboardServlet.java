package com.main.excilys.servlet;

import com.main.excilys.model.ComputerDto;
import com.main.excilys.model.Page;
import com.main.excilys.service.ComputerService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 2964121582458094059L;
  private ComputerService computerService = new ComputerService();
  private Page<ComputerDto> pageComputerDto = new Page<>();
  private List<ComputerDto> listComputerDto;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doPost(req, resp);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action") != null ? req.getParameter("action") : "";

    switch (action) {
      case "nextPage" :
        pageComputerDto.nextPage();
        break;
      case "previousPage" :
        pageComputerDto.previousPage();
        break;
      default :
        break;
    }
    // reading the user input
    int numPage = pageComputerDto.getNumPage();
    long idBegin = numPage * Page.getNbObjectPerPage();
    listComputerDto = computerService.getComputerInRange(idBegin, Page.getNbObjectPerPage());
    int nbComputerDto = computerService.getNbComputer();
    req.setAttribute("page", pageComputerDto.getNumPage());
    req.setAttribute("nbComputerDTO", nbComputerDto);
    req.setAttribute("listComputerDTO", listComputerDto);
    req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
  }

}
