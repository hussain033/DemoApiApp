package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;

import java.util.List;

public interface CartService {

    void addToCart(Cart cart);
    void removeFromCart(CartId cartId);
    List<Cart> listCart(Long userId);
    Cart getItemFromCart(CartId cartId);
}
