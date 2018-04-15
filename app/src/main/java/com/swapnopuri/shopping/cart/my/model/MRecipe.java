package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class MRecipe {
    private int Id, CategoryId, TypeOne, TypeTwo, TypeThree, TypeFour, TypeFive, recipeDelete;
    private int fav, view;
    private String Title, Thumb, Photo, Ingredients, Process, PPhoto, CategoryTitle, SearchTag, Description, Video = "";

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private float price;
    private int DiscountRate;
    private int cartStatus;
    private int addCart;
    private int isNew;
    private int selectedPos;
    private int quantity;
    private int status;
    private String selectedColor;

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    private String selectedSize;
    private ArrayList<MColor> colors;
    private ArrayList<MSize> size;

    public ArrayList<MSize> getSize() {
        return size;
    }

    public void setSize(ArrayList<MSize> size) {
        this.size = size;
    }

    public ArrayList<MColor> getColors() {
        return colors;
    }

    public void setColors(ArrayList<MColor> colors) {
        this.colors = colors;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }


    public int getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.DiscountRate = discountRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAddCart() {
        return addCart;
    }

    public void setAddCart(int addCart) {
        this.addCart = addCart;
    }

    public int getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(int cartStatus) {
        this.cartStatus = cartStatus;
    }


    public int getRecipeDelete() {
        return recipeDelete;
    }

    public void setRecipeDelete(int recipeDelete) {
        this.recipeDelete = recipeDelete;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public int getTypeOne() {
        return TypeOne;
    }

    public void setTypeOne(int typeOne) {
        TypeOne = typeOne;
    }

    public int getTypeTwo() {
        return TypeTwo;
    }

    public void setTypeTwo(int typeTwo) {
        TypeTwo = typeTwo;
    }

    public int getTypeThree() {
        return TypeThree;
    }

    public void setTypeThree(int typeThree) {
        TypeThree = typeThree;
    }

    public int getTypeFour() {
        return TypeFour;
    }

    public void setTypeFour(int typeFour) {
        TypeFour = typeFour;
    }

    public int getTypeFive() {
        return TypeFive;
    }

    public void setTypeFive(int typeFive) {
        TypeFive = typeFive;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getProcess() {
        return Process;
    }

    public void setProcess(String process) {
        Process = process;
    }

    public String getPPhoto() {
        return PPhoto;
    }

    public void setPPhoto(String PPhoto) {
        this.PPhoto = PPhoto;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getSearchTag() {
        return SearchTag;
    }

    public void setSearchTag(String searchTag) {
        SearchTag = searchTag;
    }


}
