package com.ivoyant.upiusecase.bankdetails.service;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.bankdetails.dto.BankDetailsDto;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import org.springframework.stereotype.Service;

@Service
public interface BankService {
    BankDetails save(BankDetailsDto bankDetailsDto, Users user);
}
