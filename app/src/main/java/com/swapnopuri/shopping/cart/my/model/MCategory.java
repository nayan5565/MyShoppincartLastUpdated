package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by JEWEL on 7/15/2016.
 */
public class MCategory {
    private int categoryId;
    private int categoryTotalRecipe;
    private int categoryType;
    private int categoryUpdateAvailable;
    private int categoryDelete;

//    public int getCat_id() {
//        return cat_id;
//    }
//
//    public void setCat_id(int cat_id) {
//        this.cat_id = cat_id;
//    }

//    private int cat_id;
    private String categoryTitle = "", categoryDetails = "", categoryPhoto = "", categoryThumb = "";


    public int getCategoryDelete() {
        return categoryDelete;
    }

    public void setCategoryDelete(int categoryDelete) {
        this.categoryDelete = categoryDelete;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryTotalRecipe() {
        return categoryTotalRecipe;
    }

    public void setCategoryTotalRecipe(int categoryTotalRecipe) {
        this.categoryTotalRecipe = categoryTotalRecipe;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public int getCategoryUpdateAvailable() {
        return categoryUpdateAvailable;
    }

    public void setCategoryUpdateAvailable(int categoryUpdateAvailable) {
        this.categoryUpdateAvailable = categoryUpdateAvailable;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(String categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

    public String getCategoryPhoto() {
        return categoryPhoto;
    }

    public void setCategoryPhoto(String categoryPhoto) {
        this.categoryPhoto = categoryPhoto;
    }

    public String getCategoryThumb() {
        return categoryThumb;
    }

    public void setCategoryThumb(String categoryThumb) {
        this.categoryThumb = categoryThumb;
    }
}
