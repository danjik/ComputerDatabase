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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

  /**
   * Serial id.
   */
  private static final long serialVersionUID = -668189319785763106L;
  private WebApplicationContext ctx;
  private CompanyService companyService;
  private ComputerService computerService;

  @Override
  public void init() throws ServletException {
    // TODO Auto-generated method stub
    super.init();
    ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    companyService = (CompanyService) ctx.getBean("companyService");
    computerService = (ComputerService) ctx.getBean("computerService");
  }

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
    return new ComputerDto(0, name, discontinued, introduced, companyDto);
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
