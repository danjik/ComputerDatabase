package com.main.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.excilys.model.ComputerDTO;
import com.main.excilys.model.Page;
import com.main.excilys.service.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 2964121582458094059L;
	private ComputerService computerService = new ComputerService();
	private Page<ComputerDTO> pageComputerDTO = new Page<>();
	private List<ComputerDTO> listComputerDTO;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action") != null
				? req.getParameter("action")
				: "";

		switch (action) {
			case "nextPage" :
				pageComputerDTO.nextPage();
				break;
			case "previousPage" :
				pageComputerDTO.previousPage();
				break;
			default :
				break;
		}
		// reading the user input
		int numPage = pageComputerDTO.getNumPage();
		long idBegin = numPage * Page.getNbObjectPerPage();
		listComputerDTO = computerService.getComputerInRange(idBegin,
				Page.getNbObjectPerPage());
		int nbCOmputerDTO = computerService.getNbComputer();
		req.setAttribute("page", pageComputerDTO.getNumPage());
		req.setAttribute("nbComputerDTO", nbCOmputerDTO);
		req.setAttribute("listComputerDTO", listComputerDTO);
		req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
	}

}
