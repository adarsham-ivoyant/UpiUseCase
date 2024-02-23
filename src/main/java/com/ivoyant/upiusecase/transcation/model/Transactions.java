package com.ivoyant.upiusecase.transcation.model;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.transcation.enums.TransactionType;
import com.ivoyant.upiusecase.transcation.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Transactions {
    @Id
    private String txnId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Users receiver;

    private LocalDateTime transactionDateTime;
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_transaction_type")
    private TransactionType senderTransactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "receiver_transaction_type")
    private TransactionType receiverTransactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}

