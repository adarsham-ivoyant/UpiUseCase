package com.ivoyant.upiusecase.transcation.service;


import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import com.ivoyant.upiusecase.transcation.Repository.TranscationRepository;
import com.ivoyant.upiusecase.transcation.dto.TranscationDto;
import com.ivoyant.upiusecase.transcation.enums.TransactionType;
import com.ivoyant.upiusecase.transcation.enums.TranscationStatus;
import com.ivoyant.upiusecase.transcation.model.Transcations;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class TranscationServiceImpl implements TranscationService {
    @Autowired
    private TranscationRepository transcationRepository;
    @Autowired
    private BankDetailsRepository bankDetailsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getTxnId() {
        UUID uuid = UUID.randomUUID();
        int fiveDigitNumber = Math.abs(uuid.hashCode() % 90000) + 10000;
        return "TX" + fiveDigitNumber;
    }

    @Override
    public boolean save(TranscationDto transcationDto) {
        if (passwordEncoder.matches(transcationDto.getUpiPassword(), bankDetailsRepository.
                findBankDetailsByUser(transcationDto.getSender()).getUpiPassword())) {
            Transcations transcations = Transcations.builder()
                    .txnId(getTxnId())
                    .sender(transcationDto.getSender())
                    .receiver(transcationDto.getReceiver())
                    .amount(transcationDto.getAmount())
                    .transactionDateTime(LocalDateTime.now())
                    .senderTransactionType(TransactionType.DEBIT)
                    .receiverTransactionType(TransactionType.CREDIT)
                    .status(TranscationStatus.SUCCESS)
                    .build();
            transcationRepository.save(transcations);
            updateBalances(transcationDto.getSender(), transcationDto.getReceiver(), transcationDto.getAmount());
            return true;
        } else {
            return false;
        }
    }

    private void updateBalances(Users sender, Users receiver, Double amount) {
        BankDetails senderBank = bankDetailsRepository.findBankDetailsByUser(sender);
        BankDetails receiverBank = bankDetailsRepository.findBankDetailsByUser(receiver);
        senderBank.setBalance(senderBank.getBalance() - amount);
        receiverBank.setBalance(receiverBank.getBalance() + amount);
    }
}
