package com.ivoyant.upiusecase.bankdetails.controller;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.dto.BankDetailsDto;
import com.ivoyant.upiusecase.bankdetails.service.BankService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
public class BankController {

    private final BankService bankService;
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;

    public BankController(BankService bankService, UserDetailsService userDetailsService, UsersRepository usersRepository) {
        this.bankService = bankService;
        this.userDetailsService = userDetailsService;
        this.usersRepository = usersRepository;
    }

    // Add Account Controller
    @PostMapping("/saveAcct")
    public void saveBankAcct(@ModelAttribute("bankDetails") BankDetailsDto bankDetailsDto, Principal principal, Model model, HttpServletResponse response) throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        model.addAttribute("userDetails", userDetails);
        if (bankService.addBankAccount(user, bankDetailsDto, model)) {
            response.sendRedirect("/home?bankAdded");
        } else {
            response.sendRedirect("/home?bankAddFailed");
        }
    }
}
