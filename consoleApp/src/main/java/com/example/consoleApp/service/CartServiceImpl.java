package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;
import com.example.consoleApp.repository.CartDao;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService{

    private final CartDao cartDao;

    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public void addToCart(Cart cart) {
        CartId cartId = new CartId(cart.getUserId(), cart.getItemId());
        Cart cart1 = getItemFromCart(cartId);
        if(cart1 != null) {
            int initQuantity = cart.getQuantity();
            int extendQuantity = cart1.getQuantity();
            cart.setQuantity(initQuantity + extendQuantity);
        }
        cartDao.add(cart);
    }

    @Override
    public void removeFromCart(CartId cartId) {
        cartDao.remove(cartId);
    }

    @Override
    public List<Cart> listCart(Long userId) {
        return List.of();
    }

    @Override
    public Cart getItemFromCart(CartId cartId) {
        return cartDao.get(cartId).orElse(null);
    }
}
