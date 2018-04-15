package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by JEWEL on 9/29/2016.
 */

public class MTimeTracker {
    private int id,status;
    private String login,logout;

    public MTimeTracker(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}
