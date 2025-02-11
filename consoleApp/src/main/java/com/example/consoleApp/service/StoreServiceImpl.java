package com.example.consoleApp.service;

import com.example.consoleApp.model.Item;
import com.example.consoleApp.repository.ItemRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    ItemRepository itemRepository;

    public StoreServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getItemById(Long id) {

        Item item =  itemRepository.findById(id).orElse(null);
        if(item == null) {
            throw new NullPointerException("Item not found");
        }
        return item;
    }

    @Override
    public List<Item> listItem() {

        return itemRepository.findAll();
    }

    @Override
    public Long addItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        itemRepository.deleteAll();
    }

}
