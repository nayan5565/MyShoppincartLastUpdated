package com.swapnopuri.shopping.cart.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.TutorialActivity;

/**
 * Created by Jewel on 7/13/2016.
 */
public class FragTutorial extends Fragment {


    private View viewMain;
    private ImageView img;
    private int pos;

    public static FragTutorial newInstance(int pos) {
        FragTutorial fragment = new FragTutorial();
        fragment.pos = pos;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.frag_tutorial, container, false);
        init();
        prepareDisplay();
        return viewMain;
    }

    private void init() {
        img = (ImageView) viewMain.findViewById(R.id.img);

    }

    private void prepareDisplay() {
        img.setImageResource(TutorialActivity.getImage(pos));
    }
}
