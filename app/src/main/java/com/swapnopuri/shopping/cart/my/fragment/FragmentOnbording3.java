package com.swapnopuri.shopping.cart.my.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.SplashActivity;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

/**
 * Created by Dev on 10/8/2017.
 */

public class FragmentOnbording3 extends Fragment {

    @Nullable
    public static FragmentOnbording3 newInstance() {
        return new FragmentOnbording3();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.on_bording_3, container, false);
        return view;
    }
}
