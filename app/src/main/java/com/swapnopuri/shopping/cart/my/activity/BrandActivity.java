package com.swapnopuri.shopping.cart.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.BannerAdapter;
import com.swapnopuri.shopping.cart.my.adapter.TrendAdapter;
import com.swapnopuri.shopping.cart.my.fragment.FragHomePage;
import com.swapnopuri.shopping.cart.my.tools.Global;

/**
 * Created by Dev on 10/29/2017.
 */

public class BrandActivity extends AppCompatActivity {
    private ImageView imgBrandLogo;
    private RecyclerView recB, recBc;
    private BannerAdapter bannerAdapter;
    private TrendAdapter trendAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_brand);
        init();
        display();


    }

    private void init() {
        imgBrandLogo = (ImageView) findViewById(R.id.imgBrandLogo);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recB = (RecyclerView) findViewById(R.id.recB);
        recBc = (RecyclerView) findViewById(R.id.recBc);
        bannerAdapter = new BannerAdapter(this);
        trendAdapter = new TrendAdapter(this);

        recB.setLayoutManager(layoutManager);
        recBc.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void display() {
        Picasso.with(this).load(Global.brandLogo).into(imgBrandLogo);
        recB.setAdapter(bannerAdapter);
        recBc.setAdapter(trendAdapter);
        if (FragHomePage.bannerArrayList != null && FragHomePage.bannerArrayList.size() > 0)
            bannerAdapter.setData(FragHomePage.bannerArrayList);
        if (FragHomePage.trendyArrayList != null && FragHomePage.trendyArrayList.size() > 0)
            trendAdapter.setData(FragHomePage.trendyArrayList);
    }
}
