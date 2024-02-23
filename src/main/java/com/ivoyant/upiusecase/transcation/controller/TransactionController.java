package com.ivoyant.upiusecase.transcation.controller;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.transcation.Repository.TransactionRepository;
import com.ivoyant.upiusecase.transcation.service.TranscationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@Slf4j
public class TransactionController {
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;
    private final TranscationService transcationService;
    private final TransactionRepository transactionRepository;
    private UserDetails userDetails;
    private Users user;

    public TransactionController(UserDetailsService userDetailsService, UsersRepository usersRepository, TranscationService transcationService, TransactionRepository transactionRepository) {
        this.userDetailsService = userDetailsService;
        this.usersRepository = usersRepository;
        this.transcationService = transcationService;
        this.transactionRepository = transactionRepository;
    }

    // Controller Send Money View
    @GetMapping("/sendMoney")
    public ModelAndView sendMoney(Model model, Principal principal) {
        transcationService.initializeUserDetailsAndBankDetails(model, principal);
        return new ModelAndView("SendMoney");
    }

    // Controller To Find User Using PhoneNumber
    @PostMapping("/findUpi")
    public ModelAndView upiId(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model) throws IOException {
        Long phoneNumber = Long.valueOf(request.getParameter("phoneNumber"));
        userDetails = userDetailsService.loadUserByUsername(principal.getName());
        user = usersRepository.findUsersByUsername(userDetails.getUsername());
        model.addAttribute("userDetails", userDetails);
        if (transcationService.findUser(user, response, model, phoneNumber)) {
            return new ModelAndView("SendMoney");
        } else {
            return null;
        }
    }

    // Controller to final Payment page
    @GetMapping("/pay")
    public ModelAndView makePayment(Model model, Principal principal) {
        transcationService.initializeUserDetailsAndBankDetails(model, principal);
        return new ModelAndView("Payment");
    }

    // Controller to send money
    @PostMapping("/pay")
    public void pay(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        userDetails = userDetailsService.loadUserByUsername(principal.getName());
        user = usersRepository.findUsersByUsername(userDetails.getUsername());
        Double amount = Double.valueOf(request.getParameter("amount"));
        String upiPassword = request.getParameter("upiPassword");
        transcationService.sendMoney(user, amount, upiPassword, response);
    }

    // Controller to see transaction history
    @GetMapping("/history")
    public ModelAndView showTransactionHistory(Principal principal) {
        userDetails = userDetailsService.loadUserByUsername(principal.getName());
        user = usersRepository.findUsersByUsername(userDetails.getUsername());
        ModelAndView modelAndView = new ModelAndView("Transactions");
        modelAndView.addObject("user", user);
        modelAndView.addObject("transactions", transactionRepository.findAllTransactions(user));
        return modelAndView;
    }
}
