package com.ivoyant.upiusecase.bankdetails.service;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.bankdetails.dto.BankDetailsDto;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BankServiceImpl implements BankService {
    @Autowired
    private BankDetailsRepository bankDetailsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public BankDetails save(BankDetailsDto bankDetailsDto, Users user) {
        BankDetails bankDetails = BankDetails.builder()
                .bankName(bankDetailsDto.getBankName())
                .accountNumber(bankDetailsDto.getAccountNumber())
                .ifscCode(bankDetailsDto.getIfscCode())
                .balance(1000.00)
                .upiId(bankDetailsDto.getUpiId())
                .upiPassword(passwordEncoder.encode(bankDetailsDto.getUpiPassword()))
                .user(user)
                .build();
        log.info("{}",bankDetailsRepository.save(bankDetails));
        return bankDetails;
    }
}
