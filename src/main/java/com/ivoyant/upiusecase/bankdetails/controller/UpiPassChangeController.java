package com.ivoyant.upiusecase.bankdetails.controller;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.service.BankService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
@Slf4j
public class UpiPassChangeController {
    private final UsersRepository usersRepository;
    private final UserDetailsService userDetailsService;
    private final BankService bankService;

    public UpiPassChangeController(UsersRepository usersRepository, UserDetailsService userDetailsService, BankService bankService) {
        this.usersRepository = usersRepository;
        this.userDetailsService = userDetailsService;
        this.bankService = bankService;
    }

    // Controller to change UPI password
    @PostMapping("/change/upiPass")
    public void changeUpiPassword(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        String newPassword = request.getParameter("newUpiPassword");
        if (bankService.changeUpiPassword(user, newPassword)) {
            response.sendRedirect("/pay?upiPassChanged");
        } else {
            response.sendRedirect("/pay?upiPassChangefailed");
        }
    }
}
