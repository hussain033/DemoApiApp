package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;

import java.util.List;

public interface CartService {

    void addToCart(Long userId, Long itemId);
    void removeFromCart(Long userId, Long itemId);
    List<Cart> listCart(Long userId);

}
