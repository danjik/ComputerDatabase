package com.main.excilys.servlet;

import com.main.excilys.model.Page;
import com.main.excilys.service.ComputerService;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {

  private static final long serialVersionUID = 6913245688455467027L;
  private ApplicationContext context = new ClassPathXmlApplicationContext(
      new String[] { "SpringBeans.xml" });
  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doDelete(req);
    resp.sendRedirect("/ComputerDatabase/dashboard");

  }

  private void doDelete(HttpServletRequest req) {
    String selection = req != null && req.getParameter("selection") != null
        ? req.getParameter("selection")
        : "";
    Arrays.asList(selection.split(",")).stream().filter(id -> id.matches("^[0-9]*$"))
        .collect(Collectors.toList()).forEach(id -> {
          computerService.deleteComputer(Long.valueOf(id));
          Page.decrementNbObject();
        });
  }

}
