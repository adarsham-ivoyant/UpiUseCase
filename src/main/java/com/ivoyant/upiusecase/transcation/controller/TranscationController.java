package com.ivoyant.upiusecase.transcation.controller;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import com.ivoyant.upiusecase.transcation.Repository.TranscationRepository;
import com.ivoyant.upiusecase.transcation.dto.TranscationDto;
import com.ivoyant.upiusecase.transcation.model.Transcations;
import com.ivoyant.upiusecase.transcation.service.TranscationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
@Slf4j
public class TranscationController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BankDetailsRepository bankDetailsRepository;
    private Users toUser;
    private BankDetails tobankDetails;
    @Autowired
    private TranscationService transcationService;
    @Autowired
    TranscationRepository transcationRepository;

    @GetMapping("/sendMoney")
    public ModelAndView sendMoney(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (bankDetails != null) {
            model.addAttribute("bankDetails", bankDetails);
        }
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        return new ModelAndView("SendMoney");
    }

    @PostMapping("/findUpi")
    public ModelAndView upiId(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model) throws IOException {
        Long phoneNumber = Long.valueOf(request.getParameter("phoneNumber"));
        log.info("{}", phoneNumber);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (Objects.equals(user.getPhoneNumber(), phoneNumber)) {
            response.sendRedirect("/sendMoney?sameNumber");
        } else {
            toUser = usersRepository.findUsersByPhoneNumber(phoneNumber);
            tobankDetails = bankDetailsRepository.findBankDetailsByUser(toUser);
            log.info("{}", toUser);
            log.info("{}", tobankDetails);
            if (toUser != null && tobankDetails != null) {
                log.info("To User Found!!");
                model.addAttribute("toUser", toUser);
                model.addAttribute("toBank", tobankDetails);
                model.addAttribute("bankDetails", bankDetails);
                model.addAttribute("userDetails", userDetails);
                model.addAttribute("user", user);
                return new ModelAndView("SendMoney");
            }
            log.info("To User Not Found!!");
            response.sendRedirect("/sendMoney?notExist");
        }
        return null;
    }

    @GetMapping("/pay")
    public ModelAndView makePayment(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (bankDetails != null) {
            model.addAttribute("bankDetails", bankDetails);
        }
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        model.addAttribute("toBank", tobankDetails);
        return new ModelAndView("Payment");
    }

    @PostMapping("/pay")
    public void pay(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        Double amount = Double.valueOf(request.getParameter("amount"));
        String upiPassword = request.getParameter("upiPassword");
        TranscationDto transcationDto = TranscationDto.builder().sender(user).receiver(toUser).amount(amount).upiPassword(upiPassword).build();
        if (transcationService.save(transcationDto)) {
            response.sendRedirect("/pay?success");
        } else {
            response.sendRedirect("/pay?wrongPass");
        }
    }

    @GetMapping("/history")
    public ModelAndView showTransactionHistory(Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        ModelAndView modelAndView = new ModelAndView("Transactions");
        modelAndView.addObject("user", user);
        modelAndView.addObject("transactions", transcationRepository.findAllTransactions(user, pageable));
        return modelAndView;
    }
}
