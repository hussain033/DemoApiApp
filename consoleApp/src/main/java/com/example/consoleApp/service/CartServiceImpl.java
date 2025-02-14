package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{



    @Override
    public void addToCart(Long userId, Long itemId) {

    }

    @Override
    public void removeFromCart(Long userId, Long itemId) {

    }

    @Override
    public List<Cart> listCart(Long userId) {
        return List.of();
    }
}
