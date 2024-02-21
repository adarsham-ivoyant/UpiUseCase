package com.ivoyant.upiusecase.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints =
@UniqueConstraint(columnNames = {"emailId", "username"}))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String emailId;
    private Long phoneNumber;
    private String username;
    private String password;
    private String role;

    public Users(String fullName, String emailId, Long phoneNumber, String username, String password, String roles) {
        this.fullName = fullName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = roles;
    }
}