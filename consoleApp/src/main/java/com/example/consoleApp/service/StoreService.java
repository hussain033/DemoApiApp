package com.example.consoleApp.service;

import com.example.consoleApp.model.Item;

import java.util.List;

public interface StoreService {

    Item getItemById(Long id);
    List<Item> listItem();
    Long addItem(Item item);
    void deleteItem(Long id);
    void deleteAll();
}
