package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MGallary;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by JEWEL on 5/18/2017.
 */

public class ImageSliderActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private static int _productId;
    private SliderLayout mDemoSlider;
    private ArrayList<MGallary> gallaries;
    private Toolbar toolbar;

    public static void start(Context context, int productId) {
        _productId = productId;
        context.startActivity(new Intent(context, ImageSliderActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        prepareDisplay();
        callOnline();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {
        setContentView(R.layout.lay_image_slider);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getLocalData() {
        gallaries = DBManager.getInstance().getList(DBManager.getQueryGallery(_productId), new MGallary());
    }

    private void callOnline() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("productid", _productId);
        params.put("userid", Utils.getUser().getId());
        client.post(Global.BASE + Global.API_PRODUCT_DETAIL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Gson gson = new Gson();
                try {
                    int productId = response.getJSONObject("product").getInt("Id");
                    gallaries = new ArrayList<MGallary>(Arrays.asList(gson.fromJson(response.getJSONObject("product").getJSONArray("Gallery").toString(), MGallary[].class)));

                    for (int i = 0; i < gallaries.size(); i++) {
                        gallaries.get(i).setProductId(productId);
                    }
                    if (gallaries != null && gallaries.size() > 0)
                        DBManager.getInstance().addAllData(DBManager.TABLE_GALLERY, gallaries, "Id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (gallaries != null && gallaries.size() > 0)
                    prepareDisplay();
            }
        });
    }

    private void prepareDisplay() {

        getLocalData();
        if (gallaries == null) return;

        for (MGallary name : gallaries) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
//                    .description(name.getPhoto())
                    .image(Global.API_BASE_BACKEND + name.getPhoto())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", Global.API_BASE_BACKEND + name.getPhoto());

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mDemoSlider.stopAutoCycle();
    }
}
