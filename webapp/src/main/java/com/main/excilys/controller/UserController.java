package com.main.excilys.controller;

import com.main.excilys.model.dto.UserDto;
import com.main.excilys.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping(value = "/addUser")
  public ModelAndView doGet() {
    ModelAndView model = new ModelAndView();
    model.setViewName("addUser");
    return model;
  }

  @PostMapping(value = "/addUser")
  public ModelAndView doPost(@Valid @ModelAttribute("userDto") UserDto userDto,
      BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error));
      return new ModelAndView("redirect:/addUser");
    }

    userService.addUser(userDto);
    ModelAndView model = new ModelAndView();
    model.setViewName("redirect:/dashboard");

    return model;

  }
}
