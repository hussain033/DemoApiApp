package com.example.consoleApp.controller;

import com.example.consoleApp.model.Cart;
import com.example.consoleApp.model.Item;
import com.example.consoleApp.model.UserAcc;
import com.example.consoleApp.repository.UserAccRepository;
import com.example.consoleApp.service.AccService;
import com.example.consoleApp.service.AccServiceImpl;
import com.example.consoleApp.service.CartService;
import com.example.consoleApp.service.StoreService;
import com.example.consoleApp.service.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {

    StoreService storeService;

    CartService cartService;

    AccService accService;

    public StoreController(StoreService storeService, CartService cartService, AccService accService) {

        this.storeService = storeService;
        this.cartService = cartService;
        this.accService = accService;
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

    @GetMapping("/cart/{itemId}")
    public ResponseEntity<Cart> getCartItem(@PathVariable Long itemId) {
        Long userId = accService.getCurrentUserId();
        return ResponseEntity.ok(cartService.getCartItem(userId, itemId));
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody Cart cart) {
        cartService.addToCart(cart);
    }

    @DeleteMapping("/cart/{itemId}")
    public void removeItemFromCart(@PathVariable Long itemId) {
        Long userId = accService.getCurrentUserId();
        cartService.removeFromCart(userId, itemId);
    }

    @GetMapping("/cart/list")
    public ResponseEntity<List<Cart>> listAllItemInCart() {
        Long userId = accService.getCurrentUserId();
        return ResponseEntity.ok(cartService.listCart(userId));
    }
}
