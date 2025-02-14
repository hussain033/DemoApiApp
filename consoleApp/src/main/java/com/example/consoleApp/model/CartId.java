package com.example.consoleApp.model;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {

    public Long userId;
    public Long itemId;

    public CartId() {
    }

    public CartId(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return Objects.equals(userId, cartId.userId) && Objects.equals(itemId, cartId.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, itemId);
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
}
