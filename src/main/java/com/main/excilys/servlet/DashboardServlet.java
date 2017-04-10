package com.main.excilys.servlet;

import com.main.excilys.model.ComputerDto;
import com.main.excilys.model.Page;
import com.main.excilys.presentation.Toaster;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 2964121582458094059L;
  private Toaster toast;
  private static final int[] nbObjectAvailablePerPage = { 10, 50, 100 };

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Map<String, String> options = new HashMap<>();
    String search = req.getParameter("search") != null ? req.getParameter("search") : "";
    options.put("search", search);
    String column = req.getParameter("column") != null ? req.getParameter("column") : "";
    options.put("column", column);
    Page pageComputerDto = new Page();
    List<ComputerDto> listComputerDto = new ArrayList<>();

    String action = req.getParameter("action") != null ? req.getParameter("action") : "";
    switch (action) {
      case "deleteComputer" :

        this.doDelete(req);
        break;
      case "resetOptions" :
        options.clear();
        break;
      default :
        break;
    }

    this.doChangeNbComputer(req, pageComputerDto);
    this.doSwitchPage(req, pageComputerDto);
    try {
      listComputerDto = ComputerService.INSTANCE.getComputerInRange(
          pageComputerDto.getNumPage() * pageComputerDto.getNbObjectPerPage(),
          pageComputerDto.getNbObjectPerPage(), options);
    } catch (ComputerDbException e) {

      toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      req.setAttribute("toast", toast);
    }
    req.setAttribute("nbObjectAvailablePerPage", nbObjectAvailablePerPage);
    req.setAttribute("nbObjectPerPage", pageComputerDto.getNbObjectPerPage());
    req.setAttribute("page", pageComputerDto.getNumPage());
    req.setAttribute("maxPage", pageComputerDto.getMaxPage());
    req.setAttribute("nbComputerDto", Page.nbObject);
    req.setAttribute("listComputerDto", listComputerDto);
    req.setAttribute("options", options);
    req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
  }

  private void doChangeNbComputer(HttpServletRequest req, Page pageComputerDto) {
    int nbObjectPerPage = req != null && req.getParameter("nbObject") != null
        && !req.getParameter("nbObject").isEmpty()
        && req.getParameter("nbObject").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("nbObject"))
            : 10;
    pageComputerDto.setNbObjectPerPage(nbObjectPerPage);
    pageComputerDto.setNumPage(0);
  }

  private void doDelete(HttpServletRequest req) {
    String selection = req != null && req.getParameter("selection") != null
        ? req.getParameter("selection")
        : "";
    String[] idsToDelete = selection.split(",");
    for (String element : idsToDelete) {
      if (element.matches("^[0-9]*$")) {
        try {
          CompletableFuture.runAsync(() -> {
            ComputerService.INSTANCE.deleteComputer(Long.valueOf(element));
          }).thenRun(() -> {
            Page.decrementNbObject();
          });

        } catch (ComputerDbException e) {
          toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
          req.setAttribute("toast", toast);
        }
      }
    }
  }

  private void doSwitchPage(HttpServletRequest req, Page pageComputerDto) {
    int switchPage = req != null && req.getParameter("page") != null
        && !req.getParameter("page").isEmpty() && req.getParameter("page").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("page"))
            : 0;
    if (switchPage >= 0 && switchPage - 1 < pageComputerDto.getMaxPage()) {
      pageComputerDto.setNumPage(switchPage);
    }
  }
}
