package com.ivoyant.upiusecase.transcation.service;


import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import com.ivoyant.upiusecase.transcation.Repository.TransactionRepository;
import com.ivoyant.upiusecase.transcation.dto.TransactionDto;
import com.ivoyant.upiusecase.transcation.enums.TransactionType;
import com.ivoyant.upiusecase.transcation.enums.TransactionStatus;
import com.ivoyant.upiusecase.transcation.model.Transactions;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class TranscationServiceImpl implements TranscationService {
    private final TransactionRepository transactionRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final UserDetailsService userDetailsService;
    private Users toUser;
    private BankDetails tobankDetails;

    public TranscationServiceImpl(TransactionRepository transactionRepository, BankDetailsRepository bankDetailsRepository, PasswordEncoder passwordEncoder, UsersRepository usersRepository, UserDetailsService userDetailsService) {
        this.transactionRepository = transactionRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.userDetailsService = userDetailsService;
    }

    public String getTxnId() {
        UUID uuid = UUID.randomUUID();
        int fiveDigitNumber = Math.abs(uuid.hashCode() % 90000) + 10000;
        return "TX" + fiveDigitNumber;
    }

    @Override
    public boolean save(TransactionDto transactionDto) {
        if (passwordEncoder.matches(transactionDto.getUpiPassword(), bankDetailsRepository.
                findBankDetailsByUser(transactionDto.getSender()).getUpiPassword())) {
            Transactions transactions = Transactions.builder()
                    .txnId(getTxnId())
                    .sender(transactionDto.getSender())
                    .receiver(transactionDto.getReceiver())
                    .amount(transactionDto.getAmount())
                    .transactionDateTime(LocalDateTime.now())
                    .senderTransactionType(TransactionType.DEBIT)
                    .receiverTransactionType(TransactionType.CREDIT)
                    .status(TransactionStatus.SUCCESS)
                    .build();
            transactionRepository.save(transactions);
            updateBalances(transactionDto.getSender(), transactionDto.getReceiver(), transactionDto.getAmount());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initializeUserDetailsAndBankDetails(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = usersRepository.findUsersByUsername(userDetails.getUsername());
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (bankDetails != null) {
            model.addAttribute("bankDetails", bankDetails);
        }
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        model.addAttribute("toUser", toUser);
        model.addAttribute("toBank", tobankDetails);
    }

    @Override
    public boolean findUser(Users user, HttpServletResponse response, Model model, Long phoneNumber) throws IOException {
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (Objects.equals(user.getPhoneNumber(), phoneNumber)) {
            response.sendRedirect("/sendMoney?sameNumber");
        } else {
            toUser = usersRepository.findUsersByPhoneNumber(phoneNumber);
            tobankDetails = bankDetailsRepository.findBankDetailsByUser(toUser);
            if (toUser != null && tobankDetails != null) {
                model.addAttribute("toUser", toUser);
                model.addAttribute("toBank", tobankDetails);
                model.addAttribute("bankDetails", bankDetails);
                model.addAttribute("user", user);
                return true;
            }
            response.sendRedirect("/sendMoney?notExist");
        }
        return false;
    }

    @Override
    public void sendMoney(Users user, Double amount, String upiPassword, HttpServletResponse response) throws IOException {
        TransactionDto transactionDto = TransactionDto.builder().sender(user).receiver(toUser).amount(amount).upiPassword(upiPassword).build();
        if (save(transactionDto)) {
            response.sendRedirect("/pay?success");
        } else {
            response.sendRedirect("/pay?wrongPass");
        }
    }

    private void updateBalances(Users sender, Users receiver, Double amount) {
        BankDetails senderBank = bankDetailsRepository.findBankDetailsByUser(sender);
        BankDetails receiverBank = bankDetailsRepository.findBankDetailsByUser(receiver);
        senderBank.setBalance(senderBank.getBalance() - amount);
        receiverBank.setBalance(receiverBank.getBalance() + amount);
    }
}
