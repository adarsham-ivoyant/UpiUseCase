package com.ivoyant.upiusecase.transcation.Repository;

import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.transcation.model.Transcations;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TranscationRepository extends JpaRepository<Transcations, String> {
    Transcations findAllBySender(Users users);

    Transcations findAllByReceiver(Users users);

    @Query("SELECT t FROM Transcations t WHERE t.sender = :user OR t.receiver = :user ORDER BY t.transactionDateTime DESC LIMIT 1")
    Transcations findMostRecentTransactionForUser(@Param("user") Users user);

    @Query("SELECT t FROM Transcations t WHERE t.sender = :user OR t.receiver = :user ORDER BY t.transactionDateTime DESC")
    Page<Transcations> findAllTransactions(@Param("user") Users user, Pageable pageable);

}