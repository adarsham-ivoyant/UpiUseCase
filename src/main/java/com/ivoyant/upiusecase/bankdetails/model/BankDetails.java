package com.ivoyant.upiusecase.bankdetails.model;


import com.ivoyant.upiusecase.authentication.model.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bank_details", uniqueConstraints =
@UniqueConstraint(columnNames = {"accountNumber", "upiId"}))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    private Long accountNumber;
    private String ifscCode;
    private Double balance;
    private String upiId;
    private String upiPassword;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
