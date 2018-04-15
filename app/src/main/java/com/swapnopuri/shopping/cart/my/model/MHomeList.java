package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/15/2017.
 */

public class MHomeList {
    private int adShowTime;
    private ArrayList<MBrand> brands;
    private ArrayList<MBanner> banner;
    private ArrayList<MCategory> categories;
    private ArrayList<MRecipe> topweek;
    private ArrayList<MRecipe> trendy;
    private ArrayList<MRecipe> flash;

    public ArrayList<MCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<MCategory> categories) {
        this.categories = categories;
    }

    public ArrayList<MRecipe> getTopweek() {
        return topweek;
    }

    public void setTopweek(ArrayList<MRecipe> topweek) {
        this.topweek = topweek;
    }

    public ArrayList<MRecipe> getTrendy() {
        return trendy;
    }

    public void setTrendy(ArrayList<MRecipe> trendy) {
        this.trendy = trendy;
    }

    public ArrayList<MRecipe> getFlash() {
        return flash;
    }

    public void setFlash(ArrayList<MRecipe> flash) {
        this.flash = flash;
    }

    public ArrayList<MBanner> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<MBanner> banner) {
        this.banner = banner;
    }

    public int getAdShowTime() {
        return adShowTime;
    }

    public void setAdShowTime(int adShowTime) {
        this.adShowTime = adShowTime;
    }

    public ArrayList<MBrand> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<MBrand> brands) {
        this.brands = brands;
    }
}
