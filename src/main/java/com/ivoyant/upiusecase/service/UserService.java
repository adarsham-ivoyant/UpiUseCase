package com.ivoyant.upiusecase.service;

import com.ivoyant.upiusecase.dto.UsersDto;
import com.ivoyant.upiusecase.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Users save(UsersDto usersDto);
}
