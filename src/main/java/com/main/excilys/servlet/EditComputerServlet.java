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

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {

  /**
   * serial id.
   */
  private static final long serialVersionUID = 754616699436173610L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    long idComputerToEdit = req.getParameter("id") != null
        ? Long.valueOf(req.getParameter("id"))
        : 0L;
    ComputerDto computerToEdit = ComputerService.INSTANCE.getComputerById(idComputerToEdit);
    List<CompanyDto> listCompanyDto = CompanyService.INSTANCE.getAllCompany();
    req.setAttribute("computerToEdit", computerToEdit);
    req.setAttribute("listCompanyDto", listCompanyDto);
    req.getRequestDispatcher("/views/editComputer.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    long idComputerToEdit = req.getParameter("id") != null
        ? Long.valueOf(req.getParameter("id"))
        : 0L;
    ComputerDto.Builder computerToEdit = new ComputerDto.Builder().id(idComputerToEdit);

    Toaster toast;
    String name = req.getParameter("computerName");
    String discontinued = req.getParameter("discontinued");
    String introduced = req.getParameter("introduced");
    Long idCompanyDto = req.getParameter("companyId") != null
        ? Long.valueOf(req.getParameter("companyId"))
        : 0;
    computerToEdit.name(name);
    computerToEdit.introduced(introduced);
    computerToEdit.discontinued(discontinued);
    computerToEdit.companyDto(new CompanyDto.Builder().id(idCompanyDto).build());
    ComputerDto computerDto = computerToEdit.build();
    try {

      ComputerService.INSTANCE.updateComputer(computerDto);
    } catch (ComputerDbException e) {
      toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      req.setAttribute("toast", toast);
    }

    List<CompanyDto> listCompanyDto = CompanyService.INSTANCE.getAllCompany();
    req.setAttribute("computerToEdit", computerDto);
    req.setAttribute("listCompanyDto", listCompanyDto);
    req.getRequestDispatcher("/views/editComputer.jsp").forward(req, resp);
  }

}
