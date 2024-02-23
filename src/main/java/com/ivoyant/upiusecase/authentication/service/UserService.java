package com.ivoyant.upiusecase.authentication.service;

import com.ivoyant.upiusecase.authentication.dto.UsersDto;
import com.ivoyant.upiusecase.authentication.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface UserService {
    // To Add Newly Registered User Into DB
    Users save(UsersDto usersDto);
    // To Load Information To Be Displayed On Home Page Of User - eg.. Bank Details, UPI Id etc.
    void homeService(Users user, Model model);
    // To Change Email Id
    boolean changeEmailId(Users user,String newEmailId);
    // To Change Phone Number
    boolean changePhoneNumber(Users user,Long newPhoneNumber);
}
