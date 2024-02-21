package com.ivoyant.upiusecase.service;

import com.ivoyant.upiusecase.dto.UsersDto;
import com.ivoyant.upiusecase.model.Users;
import com.ivoyant.upiusecase.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users save(UsersDto usersDto) {
        Users user = new Users(usersDto.getFullName(), usersDto.getEmailId(), usersDto.getPhoneNumber(),
                usersDto.getUsername(), usersDto.getPassword(), usersDto.getRole());
        return usersRepository.save(user);
    }
}
