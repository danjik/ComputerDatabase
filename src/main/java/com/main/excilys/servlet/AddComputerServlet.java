package com.main.excilys.servlet;

import com.main.excilys.model.CompanyDto;
import com.main.excilys.model.ComputerDto;
import com.main.excilys.model.Page;
import com.main.excilys.presentation.Toaster;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

  /**
   * Serial id.
   */
  private static final long serialVersionUID = -668189319785763106L;
  private ApplicationContext context = new ClassPathXmlApplicationContext(
      new String[] { "SpringBeans.xml" });
  private CompanyService companyService = (CompanyService) context.getBean("companyService");
  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setAttribute("listCompanyDto", companyService.getAllCompany());
    req.getRequestDispatcher("/views/addComputer.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ComputerDto newComputerDto = this.getComputerDtoFromAttribute(req);
    this.doAddComputer(req, newComputerDto);

    req.setAttribute("listCompanyDto", companyService.getAllCompany());
    req.getRequestDispatcher("/views/addComputer.jsp").forward(req, resp);
  }

  private ComputerDto getComputerDtoFromAttribute(HttpServletRequest req) {
    String name = req.getParameter("computerName");
    String discontinued = req.getParameter("discontinued");
    String introduced = req.getParameter("introduced");
    Long idCompanyDto = req.getParameter("companyId") != null
        && req.getParameter("companyId").matches("^[0-9]*$")
            ? Long.valueOf(req.getParameter("companyId"))
            : 0;

    CompanyDto companyDto = new CompanyDto.Builder().id(idCompanyDto).build();
    return new ComputerDto.Builder().name(name).introduced(introduced).discontinued(discontinued)
        .companyDto(companyDto).build();
  }

  private void doAddComputer(HttpServletRequest req, ComputerDto newComputerDto) {
    try {
      long idCreate = computerService.createComputer(newComputerDto);
      Toaster toast = Toaster.INSTANCE.getToast("Computer n°" + idCreate + " created !",
          Toaster.SUCCESS, 3000);
      req.setAttribute("toast", toast);
      Page.incrementNbObject();
    } catch (ComputerDbException e) {
      Toaster toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      req.setAttribute("toast", toast);
    }

  }

}
