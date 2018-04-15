package com.swapnopuri.shopping.cart.my.tools;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by JEWEL on 7/30/2016.
 */
public class AdsManager {
    private static AdsManager instance;
    private InterstitialAd mInterstitialAd;
    private Activity context;

    private AdsManager() {

    }

    public static AdsManager getInstance(Activity context) {
        instance = new AdsManager();
        instance.context = context;
        MobileAds.initialize(context, Global.BANNER_APP_ID);
        return instance;
    }

    public void setupBannerAd(int adViewResId) {
        AdView adView = (AdView) context.findViewById(adViewResId);
        if (!Utils.isInternetOn()) {
            adView.setVisibility(View.GONE);
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    public void showInterstisial(){
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(Global.INTERSTISIAL_UID);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
    }
}
