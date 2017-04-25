package com.main.excilys.controller;

import com.main.excilys.model.ComputerDto;
import com.main.excilys.service.CompanyService;
import com.main.excilys.service.ComputerService;
import com.main.excilys.util.ComputerDbException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EditComputerController {
  @Autowired
  private CompanyService companyService;

  @Autowired
  private ComputerService computerService;

  @GetMapping(value = "/editComputer")
  protected ModelAndView doGet(@PathParam("id") long id) throws ServletException, IOException {
    ModelAndView model = new ModelAndView();
    ComputerDto computerToEdit = computerService.getComputerById(id);
    model.setViewName("editComputer");
    model.addObject("computerToEdit", computerToEdit);
    model.addObject("listCompanyDto", companyService.getAllCompany());
    return model;

  }

  @PostMapping(value = "/editComputer")
  protected ModelAndView doPost(@ModelAttribute ComputerDto computerDto) {
    ModelAndView model = new ModelAndView();
    // Toaster toast;
    try {
      computerService.updateComputer(computerDto);
      // toast = Toaster.INSTANCE.getToast("Computer n°" + computerDto.getId() + " updated !",
      // Toaster.SUCCESS, 3000);
      // req.setAttribute("toast", toast);
    } catch (ComputerDbException e) {
      // toast = Toaster.INSTANCE.getToast(e.getMessage(), Toaster.ERROR, 3000);
      // req.setAttribute("toast", toast);
    }

    model.setViewName("editComputer");
    model.addObject("computerToEdit", computerDto);
    model.addObject("listCompanyDto", companyService.getAllCompany());
    return model;
  }

}