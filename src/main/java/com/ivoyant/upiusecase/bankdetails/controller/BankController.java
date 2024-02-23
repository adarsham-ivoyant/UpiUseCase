package com.ivoyant.upiusecase.bankdetails.controller;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.dto.BankDetailsDto;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import com.ivoyant.upiusecase.bankdetails.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    BankDetailsRepository bankDetailsRepository;

    @PostMapping("/saveAcct")
    public ModelAndView saveBankAcct(@ModelAttribute("bankDetails") BankDetailsDto bankDetailsDto, Principal principal, Model model) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        bankService.save(bankDetailsDto, user);
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        model.addAttribute("bankDetails", bankDetails);
        return new ModelAndView("HomePage");
    }
}
