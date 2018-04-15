package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/15/2016.
 */
public class MCategoryList {
    private int totalCategory;
//            , totalVideo, adShowTime;

    public int getTotalCategory() {
        return totalCategory;
    }

    public void setTotalCategory(int totalCategory) {
        this.totalCategory = totalCategory;
    }

//    public int getTotalVideo() {
//        return totalVideo;
//    }
//
//    public void setTotalVideo(int totalVideo) {
//        this.totalVideo = totalVideo;
//    }
//
//    public int getAdShowTime() {
//        return adShowTime;
//    }
//
//    public void setAdShowTime(int adShowTime) {
//        this.adShowTime = adShowTime;
//    }

    private ArrayList<MCategory> categories;

    public ArrayList<MCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<MCategory> categories) {
        this.categories = categories;
    }
}
