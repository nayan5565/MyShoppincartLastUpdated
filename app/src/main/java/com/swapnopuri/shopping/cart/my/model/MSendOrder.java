package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 2/25/2017.
 */

public class MSendOrder {
    private int userId, quantity;
    private float total;
    private String datetime;

    private ArrayList<MOrderedItem> items;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ArrayList<MOrderedItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MOrderedItem> items) {
        this.items = items;
    }
}
