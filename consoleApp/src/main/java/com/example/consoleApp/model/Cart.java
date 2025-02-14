package com.example.consoleApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(CartId.class)
public class Cart {

    @Id
    private Long userId;

    @Id
    private Long itemId;

    private Integer quantity;

    private Boolean billed;

    public Cart() {
    }

    public Cart(Long userId, Long itemId, Integer quantity, Boolean billed) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.billed = billed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isBilled() {
        return billed;
    }

    public void setBilled(Boolean billed) {
        this.billed = billed;
    }
}
