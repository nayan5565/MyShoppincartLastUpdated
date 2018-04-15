package com.swapnopuri.shopping.cart.my.model;


/**
 * Created by Nayan on 10/30/2017.
 */

public class ContentItem extends MOrderedItem {
    private int _id;
    private String date;
    private String title, thumb;
    private int id, orderId, quantity, userId;
    private float amount;

    @Override
    public int get_id() {
        return _id;
    }

    @Override
    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getThumb() {
        return thumb;
    }

    @Override
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }


}
