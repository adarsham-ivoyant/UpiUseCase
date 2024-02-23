package com.ivoyant.upiusecase.transcation.dto;

import com.ivoyant.upiusecase.authentication.model.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Builder
public class TranscationDto {
    private Users sender;
    private Users receiver;
    private Double amount;
    private String upiPassword;
}
