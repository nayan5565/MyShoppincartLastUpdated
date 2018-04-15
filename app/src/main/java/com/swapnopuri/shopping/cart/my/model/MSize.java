package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by Dev on 10/5/2017.
 */

public class MSize {
    private int id;
    private int click;
    private int price;
    private int DiscountRate;
    private String notation;


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(int discountRate) {
        DiscountRate = discountRate;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }
    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
