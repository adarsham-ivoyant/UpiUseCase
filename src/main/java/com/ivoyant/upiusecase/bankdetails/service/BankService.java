package com.ivoyant.upiusecase.bankdetails.service;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.bankdetails.dto.BankDetailsDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface BankService {
    void save(BankDetailsDto bankDetailsDto, Users user);
    boolean addBankAccount(Users user, BankDetailsDto bankDetailsDto, Model model);
    boolean changeUpiPassword(Users user, String newUpiPassword);
}
