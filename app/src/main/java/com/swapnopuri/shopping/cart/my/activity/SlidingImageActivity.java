package com.swapnopuri.shopping.cart.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.Sliding_Images_Adapter;
import com.swapnopuri.shopping.cart.my.fragment.FragHomePage;

/**
 * Created by Dev on 11/9/2017.
 */

public class SlidingImageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Sliding_Images_Adapter sliding_images_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_sliding_activity);
        viewPager = (ViewPager) findViewById(R.id.viewpagerProduct);
        if (FragHomePage.bannerArrayList != null && FragHomePage.bannerArrayList.size() > 0)
            sliding_images_adapter = new Sliding_Images_Adapter(this, FragHomePage.bannerArrayList) {
                @Override
                public void onClickItem(int position, View view) {

                }
            };
        viewPager.setAdapter(sliding_images_adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SlidingImageActivity.this, ActivityRecipe.class));
    }
}
