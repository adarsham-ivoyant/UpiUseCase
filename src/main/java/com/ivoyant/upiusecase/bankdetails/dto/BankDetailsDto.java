package com.ivoyant.upiusecase.bankdetails.dto;

import com.ivoyant.upiusecase.authentication.model.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BankDetailsDto {
    private String bankName;
    private Long accountNumber;
    private String ifscCode;
    private Double balance;
    private String upiId;
    private String upiPassword;
    private Users users;
}
