package com.example.consoleApp.controller;

import com.example.consoleApp.model.Item;
import com.example.consoleApp.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    StoreService storeService;

    public StoreController(StoreService storeService) {

        this.storeService = storeService;
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
}
