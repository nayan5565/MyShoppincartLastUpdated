package com.swapnopuri.shopping.cart.my.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 8/10/2016.
 */
public class MNotification extends MOrder
{
    private ArrayList<MOrderedItem> details;

    public ArrayList<MOrderedItem> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<MOrderedItem> details) {
        this.details = details;
    }
}
