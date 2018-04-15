package com.swapnopuri.shopping.cart.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;

/**
 * Created by Dev on 11/9/2017.
 */

public class FragBannerLink extends AppCompatActivity {
    WebView mWebview;

//    @Nullable
//    public static FragBannerLink newInstance() {
//        return new FragBannerLink();
//    }
//
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.lay_frag_banner_link, container, false);
//        tvSkip.loadUrl(Global.bannerLink);
//        return view;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebview = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript


        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(FragBannerLink.this, description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebview.loadUrl("http://" + Global.bannerLink);
        setContentView(mWebview);

    }
}
