package com.example.consoleApp.repository;

import com.example.consoleApp.model.Cart;

import java.util.List;


public interface CartDao {
    void save(Cart cart);
    void update(Cart cart);
    void remove(Long userId, Long itemId);
    Cart get(Long userId, Long itemId);
    List<Cart> listAll(Long userId);

}
