package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by JEWEL on 7/31/2016.
 */
public class MVideo {
    private int Id;
    private int TypeOne,TypeTwo,TypeThree,TypeFour,TypeFive,CategoryId,fav;
    private String menuTitle, menuVideo,menuFile_name;
    private String Ingredients,Process,PPhoto,CategoryTitle,SearchTag,Thumb;


    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
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

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuVideo() {
        return menuVideo;
    }

    public void setMenuVideo(String menuVideo) {
        this.menuVideo = menuVideo;
    }

    public String getMenuFile_name() {
        return menuFile_name;
    }

    public void setMenuFile_name(String menuFile_name) {
        this.menuFile_name = menuFile_name;
    }
}
