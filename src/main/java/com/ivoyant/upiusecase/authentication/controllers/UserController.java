package com.ivoyant.upiusecase.authentication.controllers;

import com.ivoyant.upiusecase.authentication.dto.UsersDto;
import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.authentication.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;

    public UserController(UserService userService, UserDetailsService userDetailsService, UsersRepository usersRepository) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.usersRepository = usersRepository;
    }

    // Landing Page of the website
    @RequestMapping("/")
    public ModelAndView landingPage(Model model) {
        // passing URL to register page as a model Attribute
        // NOTE: Since it is static we can also use the url directly in href of Thymeleaf
        model.addAttribute("register", "/register");
        return new ModelAndView("Landing", "model", model);
    }

    // Register Page of the website (GET - to enable /register url access)
    @GetMapping("/register")
    public ModelAndView registerPage(Model model) {
        // passing URL to login page as a model Attribute
        // NOTE: Since it is static we can also use the url directly in href of Thymeleaf
        model.addAttribute("login", "/login");
        return new ModelAndView("Register", "model", model);
    }

    // Register Page Detail Handling (POST - to process form data entered)
    @PostMapping("/register")
    public ModelAndView registerPage(@ModelAttribute("users") UsersDto usersDto, Model model) {
        // passing URL to login page as a model Attribute
        // NOTE: Since it is static we can also use the url directly in href of Thymeleaf
        model.addAttribute("login", "/login");
        if (userService.save(usersDto) != null) {
            // Save Newly registered user using DTO if not returned null then add message to model as attribute
            model.addAttribute("message", "Registered Successfully");
        } else {
            // if returned null then
            model.addAttribute("message", "Registration  Unsuccessfully");
        }
        return new ModelAndView("Register", "model", model);
    }

    // Login Page of the website (GET - to enable /login url access)
    @GetMapping("/login")
    public ModelAndView loginPage(Model model) {
        // passing URL to register page as a model Attribute
        // NOTE: Since it is static we can also use the url directly in href of Thymeleaf
        model.addAttribute("register", "/register");
        return new ModelAndView("Login", "model", model);
    }

    // Home Page of After Authentication Successfully
    @GetMapping("/home")
    public ModelAndView homePage(Model model, Principal principal) {
        // Getting UserDetails Object Using Principal Object
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        // Getting UserInstance Using username available in userDetails Instance
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        // home page details to be displayed using homeService
        userService.homeService(user, model);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        return new ModelAndView("Homepage");
    }
}
