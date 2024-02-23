package com.ivoyant.upiusecase.transcation.service;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.transcation.dto.TransactionDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.security.Principal;

@Service
@Transactional
public interface TranscationService {
    boolean save(TransactionDto transactionDto);
    void initializeUserDetailsAndBankDetails(Model model, Principal principal);
    boolean findUser(Users user, HttpServletResponse response, Model model,Long phoneNumber) throws IOException;
    void sendMoney(Users user, Double amount, String upiPassword, HttpServletResponse response) throws IOException;
}
