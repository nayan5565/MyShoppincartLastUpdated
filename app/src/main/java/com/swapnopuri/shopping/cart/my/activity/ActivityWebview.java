package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;

public class ActivityWebview extends AppCompatActivity {
    private WebView mWebview;
    private Toolbar toolbar;
    private int recipeId;

    public static void open(Context context,int recipeId){
        context.startActivity(new Intent(context,ActivityWebview.class).putExtra("productId",recipeId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        prepareWebView();
    }

    private void init() {
        setContentView(R.layout.lay_webview);
        mWebview = (WebView) findViewById(R.id.webView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        recipeId=getIntent().getIntExtra("productId",0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("radhooni.com");

        MyApp.getInstance().setupAnalytics("Source");
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void prepareWebView() {
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        mWebview.loadUrl(Global.URL_SOURCE+recipeId);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
