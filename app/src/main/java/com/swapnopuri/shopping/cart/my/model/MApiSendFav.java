package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 10/8/2016.
 */

public class MApiSendFav {
    private  int uid;
    private String email;
    private ArrayList<MSendFavRecipe>recipeId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<MSendFavRecipe> getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(ArrayList<MSendFavRecipe> recipeId) {
        this.recipeId = recipeId;
    }
}
