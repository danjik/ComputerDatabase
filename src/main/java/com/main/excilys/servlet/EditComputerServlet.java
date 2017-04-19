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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {

  /**
   * serial id.
   */
  private static final long serialVersionUID = 754616699436173610L;
  private ApplicationContext context = new ClassPathXmlApplicationContext(
      new String[] { "SpringBeans.xml" });
  private CompanyService companyService = (CompanyService) context.getBean("companyService");
  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    long idComputerToEdit = this.getIdComputer(req);

    ComputerDto computerToEdit = computerService.getComputerById(idComputerToEdit);
    List<CompanyDto> listCompanyDto = companyService.getAllCompany();
    req.setAttribute("computerToEdit", computerToEdit);
    req.setAttribute("listCompanyDto", listCompanyDto);
    req.getRequestDispatcher("/views/editComputer.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ComputerDto computerDto = this.getUpdatedComputer(req);
    this.doUpdateComputer(req, computerDto);

    List<CompanyDto> listCompanyDto = companyService.getAllCompany();
    req.setAttribute("computerToEdit", computerDto);
    req.setAttribute("listCompanyDto", listCompanyDto);
    req.getRequestDispatcher("/views/editComputer.jsp").forward(req, resp);
  }

  private long getIdComputer(HttpServletRequest req) {
    return req.getParameter("id") != null ? Long.valueOf(req.getParameter("id")) : 0L;
  }

  private ComputerDto getUpdatedComputer(HttpServletRequest req) {

    long idComputerToEdit = this.getIdComputer(req);

    ComputerDto.Builder computerToEdit = new ComputerDto.Builder().id(idComputerToEdit);
    String name = req.getParameter("computerName");
    String discontinued = req.getParameter("discontinued");
    String introduced = req.getParameter("introduced");
    Long idCompanyDto = req.getParameter("companyId") != null
        ? Long.valueOf(req.getParameter("companyId"))
        : 0;
    return computerToEdit.name(name).introduced(introduced).discontinued(discontinued)
        .companyDto(new CompanyDto.Builder().id(idCompanyDto).build()).build();
  }

  private void doUpdateComputer(HttpServletRequest req, ComputerDto computerToEdit) {

    Toaster toast;
    try {
      computerService.updateComputer(computerToEdit);
      toast = Toaster.INSTANCE.getToast("Computer n°" + computerToEdit.getId() + " updated !",
          Toaster.SUCCESS, 3000);
      req.setAttribute("toast", toast);
    } catch (ComputerDbException e) {
      toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      req.setAttribute("toast", toast);
    }
  }

}
