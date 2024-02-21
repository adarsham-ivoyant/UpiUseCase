package com.ivoyant.upiusecase.controllers;

import com.ivoyant.upiusecase.dto.UsersDto;
import com.ivoyant.upiusecase.service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public ModelAndView landingPage(Model model) {
        model.addAttribute("register", "/register");
        return new ModelAndView("Landing", "model", model);
    }

    @GetMapping("/register")
    public ModelAndView registerPage(Model model) {
        model.addAttribute("login", "/login");
        return new ModelAndView("Register", "model", model);
    }

    @PostMapping("/register")
    public ModelAndView registerPage(@ModelAttribute("users") UsersDto usersDto, Model model) {
        userService.save(usersDto);
        model.addAttribute("login", "/login");
        model.addAttribute("message", "Registered Successfully");
        return new ModelAndView("Logout", "model", model);
    }

    @GetMapping("/login")
    public ModelAndView loginPage(Model model) {
        model.addAttribute("register", "/register");
        return new ModelAndView("Login", "model", model);
    }

    @PostMapping("/login")
    public ModelAndView loginHandler(Model model) {
        model.addAttribute("register", "/register");
        return new ModelAndView("Login", "model", model);
    }

    @GetMapping("/home")
    public ModelAndView homePage() {
        return new ModelAndView("HomePage");
    }
}
