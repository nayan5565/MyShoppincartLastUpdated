package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by JEWEL on 10/8/2016.
 */

public class MSendFavRecipe {
    private int Id,CategoryId,type=1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }


}
