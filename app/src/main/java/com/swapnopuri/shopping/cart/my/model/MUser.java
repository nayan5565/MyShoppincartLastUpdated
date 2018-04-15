package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by JEWEL on 2/11/2017.
 */

public class MUser {
    private int id, type;
    private String username, pass, fullName;

    public MUser(int id, int type, String username, String pass, String fullName) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.pass = pass;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public int getId() {
//        return 1;
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
