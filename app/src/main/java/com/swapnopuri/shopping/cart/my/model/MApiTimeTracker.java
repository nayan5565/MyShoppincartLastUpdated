package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 10/2/2016.
 */

public class MApiTimeTracker {
    private int uid;
    private String email;
    private ArrayList<MTimeTracker>timetrack;

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

    public ArrayList<MTimeTracker> getTimetrack() {
        return timetrack;
    }

    public void setTimetrack(ArrayList<MTimeTracker> timetrack) {
        this.timetrack = timetrack;
    }
}
