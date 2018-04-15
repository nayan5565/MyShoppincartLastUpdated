package com.swapnopuri.shopping.cart.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swapnopuri.shopping.cart.my.R;

/**
 * Created by JEWEL on 5/8/2017.
 */

public class FragSuppliment extends Fragment {
    private static FragSuppliment instance;
    private View view;

    public static FragSuppliment newInstance() {
        instance = new FragSuppliment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lay_suppliment, container, false);
        return view;
    }
}
