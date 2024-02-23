package com.ivoyant.upiusecase.authentication.service;

import com.ivoyant.upiusecase.authentication.dto.UsersDto;
import com.ivoyant.upiusecase.authentication.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Users save(UsersDto usersDto);
}
