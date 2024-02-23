package com.ivoyant.upiusecase.bankdetails.service;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.bankdetails.dto.BankDetailsDto;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
public class BankServiceImpl implements BankService {
    private final BankDetailsRepository bankDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public BankServiceImpl(BankDetailsRepository bankDetailsRepository, PasswordEncoder passwordEncoder) {
        this.bankDetailsRepository = bankDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(BankDetailsDto bankDetailsDto, Users user) {
        BankDetails bankDetails = BankDetails.builder()
                .bankName(bankDetailsDto.getBankName())
                .accountNumber(bankDetailsDto.getAccountNumber())
                .ifscCode(bankDetailsDto.getIfscCode())
                .balance(1000.00)
                .upiId(bankDetailsDto.getUpiId())
                .upiPassword(passwordEncoder.encode(bankDetailsDto.getUpiPassword()))
                .user(user)
                .build();
        log.info("{}", bankDetailsRepository.save(bankDetails));
    }

    @Override
    public boolean addBankAccount(Users user, BankDetailsDto bankDetailsDto, Model model) {
        try {
            save(bankDetailsDto, user);
            BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("bankDetails", bankDetails);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean changeUpiPassword(Users user, String newUpiPassword) {
        try {
            BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
            bankDetails.setUpiPassword(passwordEncoder.encode(newUpiPassword));
            bankDetailsRepository.save(bankDetails);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
