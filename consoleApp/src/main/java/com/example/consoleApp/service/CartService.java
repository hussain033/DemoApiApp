package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;

import java.util.List;

public interface CartService {

    void addToCart(Cart cart);
    void removeFromCart(Long userId, Long itemId);
    List<Cart> listCart(Long userId);
    Cart getCartItem(Long userId, Long itemId);

}
