package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by Nayan on 10/2/2017.
 */
public class MProduct {
    private int id,color;
    private String size,discountParcentage;
    private int price,discountPrice;
    private int click;
    private String detail,shiftment,review;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getShiftment() {
        return shiftment;
    }

    public void setShiftment(String shiftment) {
        this.shiftment = shiftment;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountParcentage() {
        return discountParcentage;
    }

    public void setDiscountParcentage(String discountParcentage) {
        this.discountParcentage = discountParcentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
