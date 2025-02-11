package com.example.consoleApp.model;

import jakarta.persistence.*;

// item class - also warehouse
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;

    Item(){}

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {

        return price;
    }

    public void setPrice(int price) {

        this.price = price;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Long getId() {

        return id;
    }

    @Override
    public String toString() {

        return "name: " + name + "  " + "price: " + price;
    }
}
