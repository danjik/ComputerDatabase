package com.main.excilys.servlet;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.presentation.Toaster;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

  /**
   * Serial id.
   */
  private static final long serialVersionUID = -668189319785763106L;
  private final CompanyService companyService = new CompanyService();
  private final ComputerService computerService = new ComputerService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    List<CompanyDto> listCompanyDto = companyService.getAllCompany();
    String action = req.getParameter("action") != null ? req.getParameter("action") : "";
    switch (action) {
      case "addComputer" :
        String name = req.getParameter("computerName");
        String discontinued = req.getParameter("discontinued");
        String introduced = req.getParameter("introduced");
        CompanyDto companyDto = companyService
            .getCompanyById(Long.valueOf(req.getParameter("companyId")));
        try {
          ComputerDto newComputerDto = new ComputerDto.Builder().name(name).introduced(introduced)
              .discontinued(discontinued).companyDto(companyDto).build();
          long idCreate = computerService.createComputer(newComputerDto);
          Toaster toast = Toaster.INSTANCE.getToast("Computer n°" + idCreate + " created !",
              Toaster.SUCCESS, 3000);
          req.setAttribute("toast", toast);
        } catch (ComputerDbException e) {
          Toaster toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
          req.setAttribute("toast", toast);
        }

        break;

      default :
        break;
    }

    req.setAttribute("listCompanyDto", listCompanyDto);
    req.getRequestDispatcher("/views/addComputer.jsp").forward(req, resp);
  }

}
