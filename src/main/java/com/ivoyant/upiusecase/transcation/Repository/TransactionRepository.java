package com.ivoyant.upiusecase.transcation.Repository;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.transcation.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {
    @Query("SELECT t FROM Transactions t WHERE t.sender = :user OR t.receiver = :user ORDER BY t.transactionDateTime DESC LIMIT 1")
    Transactions findMostRecentTransactionForUser(@Param("user") Users user);

    @Query("SELECT t FROM Transactions t WHERE t.sender = :user OR t.receiver = :user ORDER BY t.transactionDateTime DESC")
    List<Transactions> findAllTransactions(@Param("user") Users user);

}