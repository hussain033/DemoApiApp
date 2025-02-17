package com.example.consoleApp.service;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.repository.CartDao;
import com.example.consoleApp.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService{

    ItemRepository itemRepository;
    CartDao cartDao;

    public CartServiceImpl(ItemRepository itemRepository, CartDao cartDao) {
        this.itemRepository = itemRepository;
        this.cartDao = cartDao;
    }

    @Override
    public void addToCart(Cart cart) {

        Cart cart1 = cartDao.get(cart.getUserId(), cart.getItemId());
        if(cart1 == null) {
            cartDao.save(cart);
        } else {
            int existQuantity = cart1.getQuantity();
            int extraQuantity = cart.getQuantity();
            cart.setQuantity(existQuantity + extraQuantity);
            cartDao.save(cart);
        }

    }

    @Override
    public void removeFromCart(Long userId, Long itemId) {
        cartDao.remove(userId, itemId);
    }

    @Override
    public Cart getCartItem(Long userId, Long itemId) {
        return cartDao.get(userId, itemId);
    }

    @Override
    public List<Cart> listCart(Long userId) {
        return cartDao.listAll(userId);
    }
}
