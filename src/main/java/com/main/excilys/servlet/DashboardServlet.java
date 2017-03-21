package com.main.excilys.servlet;

import com.main.excilys.model.ComputerDto;
import com.main.excilys.model.Page;
import com.main.excilys.presentation.Toaster;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 2964121582458094059L;
  private ComputerService computerService = new ComputerService();
  private Page<ComputerDto> pageComputerDto;
  private List<ComputerDto> listComputerDto;
  private int nbComputerDto;

  /**
   * Constructor of the servlet.
   */
  public DashboardServlet() {
    nbComputerDto = computerService.getNbComputer();
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
    String action = req.getParameter("action") != null ? req.getParameter("action") : "";
    switch (action) {
      case "firstPage" :
        pageComputerDto.setNumPage(0);
        break;
      case "switchPage" :
        int switchPage = req.getParameter("page").matches("^[0-9]*$")
            ? Integer.valueOf(req.getParameter("page"))
            : pageComputerDto.getNumPage();
        switchPage--;
        if (switchPage >= 0 && switchPage <= pageComputerDto.getMaxPage()) {
          pageComputerDto.setNumPage(switchPage);
        }
        break;
      case "lastPage" :
        pageComputerDto.setNumPage(pageComputerDto.getMaxPage());
        break;
      case "nextPage" :
        if (pageComputerDto.hasNext()) {
          pageComputerDto.nextPage();
        }
        break;
      case "previousPage" :
        if (pageComputerDto.hasPrevious()) {
          pageComputerDto.previousPage();
        }
        break;
      case "deleteComputer" :
        Toaster toast;
        String selection = req.getParameter("selection") != null
            ? req.getParameter("selection")
            : "";
        String[] idsToDelete = selection.split(",");
        for (String element : idsToDelete) {
          if (element.matches("^[0-9]*$")) {
            try {
              computerService.deleteComputer(Long.valueOf(element));
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
      default :
        break;
    }
    // reading the user input
    int numPage = pageComputerDto.getNumPage();
    int[] nbObjectAvailablePerPage = { 10, 50, 100 };
    long idBegin = numPage * Page.getNbObjectPerPage();
    listComputerDto = computerService.getComputerInRange(idBegin, Page.getNbObjectPerPage());
    int nbComputerDto = computerService.getNbComputer();
    req.setAttribute("nbObjectAvailablePerPage", nbObjectAvailablePerPage);
    req.setAttribute("nbObjectPerPage", Page.getNbObjectPerPage());
    req.setAttribute("page", pageComputerDto.getNumPage());
    req.setAttribute("maxPage", pageComputerDto.getMaxPage());
    req.setAttribute("nbComputerDto", nbComputerDto);
    req.setAttribute("listComputerDto", listComputerDto);
    req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
  }

}
