package com.example.consoleApp.repository;

import com.example.consoleApp.model.AdminAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAccRepository extends JpaRepository<AdminAcc, Long> {

    Optional<AdminAcc> findByUsername(String username);
}
