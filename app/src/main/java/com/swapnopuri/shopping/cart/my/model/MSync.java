package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 9/7/2016.
 */
public class MSync {
    private ArrayList<MCategory> category;
    private ArrayList<MRecipe> recipes;

    public ArrayList<MCategory> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<MCategory> category) {
        this.category = category;
    }

    public ArrayList<MRecipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<MRecipe> recipes) {
        this.recipes = recipes;
    }
}
