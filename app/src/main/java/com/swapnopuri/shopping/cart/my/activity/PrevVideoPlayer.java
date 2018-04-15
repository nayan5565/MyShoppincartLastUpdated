package com.swapnopuri.shopping.cart.my.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MAds;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.io.IOException;

public class PrevVideoPlayer extends AppCompatActivity {
    FullscreenVideoLayout videoLayout;
    private String fileName = "", title = "";
    private int recipeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_video_player);
        init();
        setupBannerAd();
        preparePlayer();

        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init() {

        fileName = getIntent().getStringExtra("fileName");
        title = getIntent().getStringExtra("title");
        recipeId = getIntent().getIntExtra("productId", 0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

    }

    private void preparePlayer() {

        saveInfoIntoLocal();
        videoLayout = (FullscreenVideoLayout) findViewById(R.id.videoview);
        videoLayout.setActivity(this);

        videoLayout.hideControls();

        MyLog.e("TEST", "fi:" + fileName);

        Uri uri = Uri.parse(fileName);
        try {
            videoLayout.setVideoURI(uri);
            videoLayout.setShouldAutoplay(true);

            videoLayout.hideControls();
            videoLayout.setFullscreen(true);

        } catch (IOException e) {
           MyLog.e("ERR_PLAYER",e.toString());
        }

    }

    private void saveInfoIntoLocal() {
        if (recipeId <= 0)
            return;
        MAds ads = new MAds(recipeId);
        DBManager.getInstance().addData(DBManager.TABLE_ADS, ads, "id");
    }

    private void prepareAds() {

        //for adding ads view here

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        View v = LayoutInflater.from(this).inflate(R.layout.test, null, false);
        v.setLayoutParams(layoutParams);
        videoLayout.addView(v);
        // end ads view
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        videoLayout.resize();
    }



    public void setupBannerAd() {
        MobileAds.initialize(this, Global.BANNER_APP_ID_TEST);
        AdView adView = (AdView) findViewById(R.id.adView);
//        adView.setAdUnitId(Global.BANNER_APP_ID_TEST);
        if (!Utils.isInternetOn()) {
            adView.setVisibility(View.GONE);
            return;
        }


        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);  }


}
