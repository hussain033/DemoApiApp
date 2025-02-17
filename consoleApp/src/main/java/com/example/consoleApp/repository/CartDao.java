package com.example.consoleApp.repository;

import com.example.consoleApp.model.Cart;

import java.util.List;


public interface CartDao {
    public void save(Cart cart);
    public void remove(Long userId, Long itemId);
    public Cart get(Long userId, Long itemId);
    public List<Cart> listAll(Long userId);

}
