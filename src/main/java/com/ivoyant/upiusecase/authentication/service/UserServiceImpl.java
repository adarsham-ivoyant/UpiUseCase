package com.ivoyant.upiusecase.service;

import com.ivoyant.upiusecase.dto.UsersDto;
import com.ivoyant.upiusecase.model.Users;
import com.ivoyant.upiusecase.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users save(UsersDto usersDto) {
        Users user = new Users(usersDto.getFullName(), usersDto.getEmailId(), usersDto.getPhoneNumber(),
                usersDto.getUsername(), passwordEncoder.encode(usersDto.getPassword()), usersDto.getRole());
        return usersRepository.save(user);
    }
}
