package com.ivoyant.upiusecase.bankdetails.controller;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
@Slf4j
public class UpiPassChangeController {
    @Autowired
    BankDetailsRepository bankDetailsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/change/upiPass")
    public void changeUpiPassword(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        String newPassword = request.getParameter("newUpiPassword");
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        bankDetails.setUpiPassword(passwordEncoder.encode(newPassword));
        bankDetailsRepository.save(bankDetails);
        response.sendRedirect("/pay?upiPassChanged");
    }
}
