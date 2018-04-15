package com.swapnopuri.shopping.cart.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swapnopuri.shopping.cart.my.R;

/**
 * Created by JEWEL on 2/13/2017.
 */

public class FragCurrnetOrder extends Fragment {
    private static FragCurrnetOrder instance;

    private View view;


    public static FragCurrnetOrder getInstance() {
        instance = new FragCurrnetOrder();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lay_current_order, container, false);
        return view;
    }


}
