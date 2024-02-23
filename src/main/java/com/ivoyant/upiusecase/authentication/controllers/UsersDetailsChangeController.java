package com.ivoyant.upiusecase.authentication.controllers;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;

@Controller
public class UsersDetailsChangeController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/change/email")
    public void changeEmail(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        String newEmailId = request.getParameter("emailId");
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        user.setEmailId(newEmailId);
        usersRepository.save(user);
        response.sendRedirect("/home?message=" + URLEncoder.encode("Email Changed Successfully", "UTF-8"));
    }

    @PostMapping("/change/phonenumber")
    public void changePhoneNumber(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        Long newPhoneNumber = Long.valueOf(request.getParameter("phoneNumber"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        user.setPhoneNumber(newPhoneNumber);
        usersRepository.save(user);
        response.sendRedirect("/home?message=" + URLEncoder.encode("Phone Number Changed Successfully", "UTF-8"));
    }
}
