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
    int nbComputerDto = ComputerService.INSTANCE.getNbComputer(options);
    Page<ComputerDto> pageComputerDto = new Page<>(nbComputerDto);
    List<ComputerDto> listComputerDto = new ArrayList<>();

    String action = req.getParameter("action") != null ? req.getParameter("action") : "";
    switch (action) {
      case "switchPage" :
        this.doSwitchPage(req, pageComputerDto);
        break;
      case "deleteComputer" :

        this.doDelete(req);
        break;
      case "changeNbComputer" :
        this.doChangeNbComputer(req, pageComputerDto);
        break;

      case "resetOptions" :
        options.clear();
        break;
      default :
        break;
    }
    try {
      nbComputerDto = ComputerService.INSTANCE.getNbComputer(options);
      Page.setNbObject(nbComputerDto);
      int numPage = pageComputerDto.getNumPage();
      long idBegin = numPage * Page.getNbObjectPerPage();
      listComputerDto = ComputerService.INSTANCE.getComputerInRange(idBegin,
          Page.getNbObjectPerPage(), options);
    } catch (ComputerDbException e) {
      toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      req.setAttribute("toast", toast);
    }
    nbComputerDto = ComputerService.INSTANCE.getNbComputer(options);
    Page.setNbObject(nbComputerDto);
    req.setAttribute("nbObjectAvailablePerPage", nbObjectAvailablePerPage);
    req.setAttribute("nbObjectPerPage", Page.getNbObjectPerPage());
    req.setAttribute("page", pageComputerDto.getNumPage());
    req.setAttribute("maxPage", pageComputerDto.getMaxPage());
    req.setAttribute("nbComputerDto", nbComputerDto);
    req.setAttribute("listComputerDto", listComputerDto);
    req.setAttribute("options", options);
    req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
  }

  private void doChangeNbComputer(HttpServletRequest req, Page<ComputerDto> pageComputerDto) {
    int nbObjectPerPage = req.getParameter("nbObject").matches("^[0-9]*$")
        ? Integer.valueOf(req.getParameter("nbObject"))
        : 10;
    Page.setNbObjectPerPage(nbObjectPerPage);
    pageComputerDto.setNumPage(0);
  }

  private void doDelete(HttpServletRequest req) {
    String selection = req.getParameter("selection") != null ? req.getParameter("selection") : "";
    String[] idsToDelete = selection.split(",");
    for (String element : idsToDelete) {
      if (element.matches("^[0-9]*$")) {
        try {
          ComputerService.INSTANCE.deleteComputer(Long.valueOf(element));
          toast = Toaster.INSTANCE.getToast("Computer n°" + selection + " deleted !",
              Toaster.SUCCESS, 3000);
          req.setAttribute("toast", toast);
        } catch (ComputerDbException e) {
          toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
          req.setAttribute("toast", toast);
        }
      }
    }
  }

  private void doSwitchPage(HttpServletRequest req, Page<ComputerDto> pageComputerDto) {
    int switchPage = req.getParameter("page").matches("^[0-9]*$")
        ? Integer.valueOf(req.getParameter("page"))
        : pageComputerDto.getNumPage() + 1;
    if (switchPage - 1 >= 0 && switchPage - 1 <= pageComputerDto.getMaxPage()) {
      pageComputerDto.setNumPage(--switchPage);
    }
  }

}
