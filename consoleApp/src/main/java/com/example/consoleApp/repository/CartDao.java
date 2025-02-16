package com.example.consoleApp.repository;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    void add(Cart cart);
    void update(Cart cart);
    void remove(CartId cartId);
    List<Cart> list(Long userId);
    Optional<Cart> get(CartId cartId);
}
