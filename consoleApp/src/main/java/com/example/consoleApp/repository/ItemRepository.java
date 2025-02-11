package com.example.consoleApp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.consoleApp.model.Item;

// Warehouse services
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("Select case when count(i) > 0 then " +
    "True else false end from Item i where " +
    "i.id = ?1")
    boolean itemExists(Long id);



}
