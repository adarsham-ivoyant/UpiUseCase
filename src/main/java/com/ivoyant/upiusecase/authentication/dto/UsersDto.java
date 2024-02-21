package com.ivoyant.upiusecase.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UsersDto {
    private String fullName;
    private String emailId;
    private Long phoneNumber;
    private String username;
    private String password;
    private String role;
}
