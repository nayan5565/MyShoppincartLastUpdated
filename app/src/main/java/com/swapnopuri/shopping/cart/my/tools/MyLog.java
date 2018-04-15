package com.swapnopuri.shopping.cart.my.tools;

import android.util.Log;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class MyLog {
    private static boolean isActive = true;

    public static void e(String tag, String msg) {
        if (isActive)
            Log.e(tag, msg);
    }
}
