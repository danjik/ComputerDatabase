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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// reading the user input
		int numPage = pageComputerDTO.getNumPage();
		long idBegin = numPage * Page.getNbObjectPerPage();
		List<ComputerDTO> listComputerDTO = computerService.getComputerInRange(idBegin, Page.getNbObjectPerPage());
		int nbCOmputerDTO = computerService.getNbComputer();
		request.setAttribute("nbComputerDTO", nbCOmputerDTO);
		request.setAttribute("listComputerDTO", listComputerDTO);
		request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
}
