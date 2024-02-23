package com.ivoyant.upiusecase.authentication.service;

import com.ivoyant.upiusecase.authentication.dto.UsersDto;
import com.ivoyant.upiusecase.authentication.model.Users;
import com.ivoyant.upiusecase.authentication.repository.UsersRepository;
import com.ivoyant.upiusecase.bankdetails.model.BankDetails;
import com.ivoyant.upiusecase.bankdetails.repository.BankDetailsRepository;
import com.ivoyant.upiusecase.transcation.Repository.TransactionRepository;
import com.ivoyant.upiusecase.transcation.enums.TransactionType;
import com.ivoyant.upiusecase.transcation.model.Transactions;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final BankDetailsRepository bankDetailsRepository;

    private final TransactionRepository transactionRepository;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, BankDetailsRepository bankDetailsRepository, TransactionRepository transactionRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.bankDetailsRepository = bankDetailsRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Users save(UsersDto usersDto) {
        Users user = new Users(usersDto.getFullName(), usersDto.getEmailId(), usersDto.getPhoneNumber(),
                usersDto.getUsername(), passwordEncoder.encode(usersDto.getPassword()), usersDto.getRole());
        return usersRepository.save(user);
    }

    @Override
    public void homeService(Users user, Model model) {
        // Fetching bank account details
        BankDetails bankDetails = bankDetailsRepository.findBankDetailsByUser(user);
        if (bankDetails != null) {
            // if already added bank details then sending instance as attribute
            model.addAttribute("bankDetails", bankDetails);
            // checking for recent transaction
            Transactions transcation = transactionRepository.findMostRecentTransactionForUser(user);
            if (transcation != null) {
                // if has the transaction
                model.addAttribute("recentTxn", transcation);
                model.addAttribute("user", user);
                model.addAttribute("typeDEBIT", TransactionType.DEBIT);
                model.addAttribute("typeCREDIT", TransactionType.CREDIT);
            }
        }
    }

    @Override
    public boolean changeEmailId(Users user, String newEmailId) {
        try {
            // Changing The Email
            user.setEmailId(newEmailId);
            usersRepository.save(user);
            return true;
        } catch (Exception e) {
            log.info("Email Change Failed -> {}", e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean changePhoneNumber(Users user, Long newPhoneNumber) {
        try {
            // Changing The Phone Number
            user.setPhoneNumber(newPhoneNumber);
            usersRepository.save(user);
            return true;
        } catch (Exception e) {
            log.info("Phone Number Change Failed -> {}", e.getLocalizedMessage());
            return false;
        }
    }
}
