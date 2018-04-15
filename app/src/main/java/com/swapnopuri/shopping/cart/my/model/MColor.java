package com.swapnopuri.shopping.cart.my.model;

/**
 * Created by Dev on 10/5/2017.
 */

public class MColor {
    private int id;
    private String colorname;
    private String hex;
    private int click;

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorname() {
        return colorname;
    }

    public void setColorname(String colorname) {
        this.colorname = colorname;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}
