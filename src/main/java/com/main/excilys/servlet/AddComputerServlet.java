package com.main.excilys.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.excilys.model.CompanyDTO;
import com.main.excilys.model.ComputerDTO;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -668189319785763106L;
	private final CompanyService companyService = new CompanyService();
	private final ComputerService computerService = new ComputerService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<CompanyDTO> listCompanyDTO = companyService.getAllCompany();
		String action = req.getParameter("action") != null
				? req.getParameter("action")
				: "";
		switch (action) {
			case "addComputer" :
				String name = req.getParameter("computerName");
				LocalDate introduced = !req.getParameter("introduced").isEmpty()
						? LocalDate.parse(req.getParameter("introduced"))
						: null;
				LocalDate discontinued = !req.getParameter("discontinued")
						.isEmpty()
								? LocalDate
										.parse(req.getParameter("discontinued"))
								: null;
				CompanyDTO companyDTO = companyService.getCompanyById(
						Long.valueOf(req.getParameter("companyId")));
				ComputerDTO newComputerDTO = new ComputerDTO.Builder()
						.name(name).introduced(introduced)
						.discontinued(discontinued).companyDTO(companyDTO)
						.build();
				computerService.createComputer(newComputerDTO);
				break;

			default :
				break;
		}

		req.setAttribute("listCompanyDTO", listCompanyDTO);
		req.getRequestDispatcher("/views/addComputer.jsp").forward(req, resp);
	}

}
