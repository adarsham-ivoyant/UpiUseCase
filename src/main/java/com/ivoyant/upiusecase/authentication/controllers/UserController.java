package com.ivoyant.upiusecase.authentication.controllers;

import com.ivoyant.upiusecase.authentication.dto.UsersDto;
import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.authentication.service.UserService;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import com.ivoyant.upiusecase.transcation.Repository.TranscationRepository;
import com.ivoyant.upiusecase.transcation.enums.TransactionType;
import com.ivoyant.upiusecase.transcation.model.Transcations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BankDetailsRepository bankDetailsRepository;
    @Autowired
    TranscationRepository transcationRepository;

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
        return new ModelAndView("Register", "model", model);
    }

    @GetMapping("/login")
    public ModelAndView loginPage(Model model) {
        model.addAttribute("register", "/register");
        return new ModelAndView("Login", "model", model);
    }

    @PostMapping("/login")
    public ModelAndView loginHandler(Model model, Principal principal) {
        model.addAttribute("register", "/register");
        return new ModelAndView("Login", "model", model);
    }

    @GetMapping("/home")
    public ModelAndView homePage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (bankDetails != null) {
            model.addAttribute("bankDetails", bankDetails);
            Transcations transcation = transcationRepository.findMostRecentTransactionForUser(user);
            if(transcation!=null){
                model.addAttribute("recentTxn", transcation);
                model.addAttribute("user",user);
                model.addAttribute("typeDEBIT",TransactionType.DEBIT);
                model.addAttribute("typeCREDIT",TransactionType.CREDIT);
            }
        }
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        return new ModelAndView("HomePage");
    }
}
