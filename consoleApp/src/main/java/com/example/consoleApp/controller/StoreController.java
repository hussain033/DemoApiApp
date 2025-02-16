package com.example.consoleApp.controller;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.CartId;
import com.example.consoleApp.model.Item;
import com.example.consoleApp.service.CartService;
import com.example.consoleApp.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    StoreService storeService;
    CartService cartService;

    public StoreController(StoreService storeService, CartService cartService) {
        this.storeService = storeService;
        this.cartService = cartService;
    }

    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable Long id) {

        return storeService.getItemById(id);
    }


    @GetMapping("/item")
    public List<Item> listItem() {

        return storeService.listItem();
    }

    @PostMapping("/item")
    public ResponseEntity<Long> addItem(@RequestBody Item item) {

        return ResponseEntity.ok(storeService.addItem(item));
    }

    @DeleteMapping("/item/{id}")
    public void deleteItemById(@PathVariable("id") Long id) {

        storeService.deleteItem(id);
    }

    @GetMapping("/cart/{userId}/{itemId}")
    public Cart getItembyId(@PathVariable Long userId, @PathVariable Long itemId) {
        return cartService.getItemFromCart(new CartId(userId, itemId));
    }

    @GetMapping("/cart/{userId}")
    public List<Cart> listCartItems(@PathVariable Long userId) {
        return cartService.listCart(userId);
    }

    @PostMapping("/cart/")
    public void addToCart(@RequestBody Cart cart) {
        cartService.addToCart(cart);
    }

    @DeleteMapping("/cart/{userId}/{itemId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        cartService.removeFromCart(new CartId(userId, itemId));
    }

}
