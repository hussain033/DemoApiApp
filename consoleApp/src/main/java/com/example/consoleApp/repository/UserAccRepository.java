package com.example.consoleApp.repository;

import com.example.consoleApp.model.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccRepository extends JpaRepository<UserAcc, Long> {

    Optional<UserAcc> findByUsername(String username);
}
