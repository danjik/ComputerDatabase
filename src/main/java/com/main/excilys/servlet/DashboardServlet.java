package com.main.excilys.servlet;

import com.main.excilys.model.ComputerDto;
import com.main.excilys.model.Page;
import com.main.excilys.presentation.Toaster;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.io.IOException;
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
  private Page<ComputerDto> pageComputerDto;
  private List<ComputerDto> listComputerDto;
  private int nbComputerDto;
  private Map<String, String> options = new HashMap<>();

  /**
   * Constructor of the servlet.
   */
  public DashboardServlet() {
    nbComputerDto = ComputerService.INSTANCE.getNbComputer(options);
    pageComputerDto = new Page<>(nbComputerDto);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doPost(req, resp);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Toaster toast;
    String action = req.getParameter("action") != null ? req.getParameter("action") : "";
    switch (action) {
      case "switchPage" :
        int switchPage = req.getParameter("page").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("page"))
            : pageComputerDto.getNumPage() + 1;
        if (switchPage - 1 >= 0 && switchPage - 1 <= pageComputerDto.getMaxPage()) {
          pageComputerDto.setNumPage(--switchPage);
        }
        break;
      case "deleteComputer" :

        String selection = req.getParameter("selection") != null
            ? req.getParameter("selection")
            : "";
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
        break;
      case "changeNbComputer" :
        int nbObjectPerPage = req.getParameter("nbObject").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("nbObject"))
            : 10;
        Page.setNbObjectPerPage(nbObjectPerPage);
        pageComputerDto.setNumPage(0);
        break;

      case "option" :
        String param = req.getParameter("param") != null ? req.getParameter("param") : "";
        String value = req.getParameter("value") != null ? req.getParameter("value") : "";
        if (param.equals("sort")) {
          value += " asc";
          if (options.get(param) != null && options.get(param).equals(value)) {
            value = req.getParameter("value") + " desc";
          }
        }

        options.put(param, value);
        pageComputerDto.setNumPage(0);
        break;
      case "resetOptions" :
        options.clear();
        break;
      default :
        break;
    }
    try {
      int nbComputerDto = ComputerService.INSTANCE.getNbComputer(options);
      Page.setNbObject(nbComputerDto);
      int numPage = pageComputerDto.getNumPage();
      long idBegin = numPage * Page.getNbObjectPerPage();
      listComputerDto = ComputerService.INSTANCE.getComputerInRange(idBegin,
          Page.getNbObjectPerPage(), options);
    } catch (ComputerDbException e) {
      toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      req.setAttribute("toast", toast);
    }

    final int[] nbObjectAvailablePerPage = { 10, 50, 100 };
    req.setAttribute("nbObjectAvailablePerPage", nbObjectAvailablePerPage);
    req.setAttribute("nbObjectPerPage", Page.getNbObjectPerPage());
    req.setAttribute("page", pageComputerDto.getNumPage());
    req.setAttribute("maxPage", pageComputerDto.getMaxPage());
    req.setAttribute("nbComputerDto", nbComputerDto);
    req.setAttribute("listComputerDto", listComputerDto);
    req.setAttribute("options", options);
    req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
  }

}
