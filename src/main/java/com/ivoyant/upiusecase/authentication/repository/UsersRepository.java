package com.ivoyant.upiusecase.authentication.repository;

import com.ivoyant.upiusecase.authentication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findUsersByUsername(String emailId);
    Users findUsersByPhoneNumber(Long phoneNumber);
}
