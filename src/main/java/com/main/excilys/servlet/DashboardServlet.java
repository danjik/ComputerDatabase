package com.main.excilys.servlet;

import com.main.excilys.model.ModelDashboardResponse;
import com.main.excilys.model.Page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 2964121582458094059L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    ModelDashboardResponse modelDashboardResponse = new ModelDashboardResponse(req, resp);
    req.setAttribute("nbComputerDto", Page.nbObject);
    req.setAttribute("model", modelDashboardResponse);
    req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doPost(req, resp);
  }

}
