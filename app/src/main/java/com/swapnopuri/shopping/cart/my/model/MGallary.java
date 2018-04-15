package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by JEWEL on 5/18/2017.
 */

public class MGallary {
    private int Id, ProductId;
    private String Photo;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
