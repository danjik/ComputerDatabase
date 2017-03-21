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
  private ComputerService computerService = new ComputerService();
  private CompanyService companyService = new CompanyService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action") != null ? req.getParameter("action") : "";
    long idComputerToEdit = req.getParameter("id") != null
        ? Long.valueOf(req.getParameter("id"))
        : 0L;
    ComputerDto computerToEdit = computerService.getComputerById(idComputerToEdit);

    List<CompanyDto> listCompanyDto = companyService.getAllCompany();
    switch (action) {
      case "editComputer" :
        Toaster toast;
        String name = req.getParameter("computerName");
        String discontinued = req.getParameter("discontinued");
        String introduced = req.getParameter("introduced");
        CompanyDto companyDto = companyService
            .getCompanyById(Long.valueOf(req.getParameter("companyId")));
        computerToEdit.setName(name);
        computerToEdit.setIntroduced(introduced);
        computerToEdit.setDiscontinued(discontinued);
        computerToEdit.setCompanyDto(companyDto);
        try {
          computerService.updateComputer(computerToEdit);
        } catch (ComputerDbException e) {
          toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
          req.setAttribute("toast", toast);
        }

        toast = Toaster.INSTANCE.getToast("Computer n°" + idComputerToEdit + " updated !",
            Toaster.SUCCESS, 3000);
        req.setAttribute("toast", toast);
        break;

      default :
        break;
    }

    req.setAttribute("computerToEdit", computerToEdit);
    req.setAttribute("listCompanyDto", listCompanyDto);
    req.getRequestDispatcher("/views/editComputer.jsp").forward(req, resp);
  }

}
