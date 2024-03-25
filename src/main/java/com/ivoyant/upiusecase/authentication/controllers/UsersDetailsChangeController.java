package com.ivoyant.upiusecase.authentication.controllers;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.authentication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
public class UsersDetailsChangeController {
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;
    private UserDetails userDetails;
    private final UserService userService;
    private Users user;

    public UsersDetailsChangeController(UserDetailsService userDetailsService, UsersRepository usersRepository, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    // Controller To Change Mail
    @PostMapping("/change/email")
    public void changeEmail(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        String newEmailId = request.getParameter("emailId");
        userDetails = userDetailsService.loadUserByUsername(principal.getName());
        user = usersRepository.findUsersByUsername(userDetails.getUsername());
        if (userService.changeEmailId(user, newEmailId)) {
            response.sendRedirect("/home?changedMail");
        } else {
            response.sendRedirect("/home?failedChangeMail");
        }
    }

    // Controller To Change Phone Number
    @PostMapping("/change/phonenumber")
    public void changePhoneNumber(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
        Long newPhoneNumber = Long.valueOf(request.getParameter("phoneNumber"));
        userDetails = userDetailsService.loadUserByUsername(principal.getName());
        user = usersRepository.findUsersByUsername(userDetails.getUsername());
        if (userService.changePhoneNumber(user, newPhoneNumber)) {
            response.sendRedirect("/home?changedPh");
        } else {
            response.sendRedirect("/home?failedChangePh");
        }
    }
}
