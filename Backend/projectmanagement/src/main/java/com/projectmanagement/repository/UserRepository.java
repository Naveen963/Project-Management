package com.projectmanagement.repository;

import com.projectmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>  findByUserName(String username);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
}