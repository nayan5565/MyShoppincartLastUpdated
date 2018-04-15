package com.swapnopuri.shopping.cart.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdSubCat;
import com.swapnopuri.shopping.cart.my.fragment.FragCategoryList;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MCategoryList;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Dev on 10/25/2017.
 */

public class SubCategoryActivity extends AppCompatActivity {
    private MCategoryList categoryList;
    private RecyclerView recyclerView;
    private AdSubCat adapterRecipe;
    public TabLayout footerTab;
    public static int image;
    private static ArrayList<Integer> footerIcons;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_cat_activity);
        gson = new Gson();
        footerTab = (TabLayout) findViewById(R.id.footerTab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterRecipe = new AdSubCat(this) {
            @Override
            public void onClickItem(int itemPositon, View view) {
                if (Global.subCatList != null && Global.subCatList.size() > 0) {
//                    ActivityRecipeList.category = Global.subCatList.get(itemPositon);
                    Global.categoriId = Global.subCatList.get(itemPositon).getCategoryId();
                    Global.subCatImageURL = Global.subCatList.get(itemPositon).getCategoryThumb();
                    Global.categoryTitle = Global.subCatList.get(itemPositon).getCategoryTitle();
                    ActivityRecipeList.showSizeColor = 1;
                    ActivityRecipeList.fragCatImg = 1;
                    image = 2;
                    FragCategoryList.image = 0;
                    adapterRecipe.notifyDataSetChanged();
                    startActivity(new Intent(SubCategoryActivity.this, ActivityRecipeList.class));
                }
            }
        };
        recyclerView.setAdapter(adapterRecipe);
        if (Global.subCatList != null && Global.subCatList.size() > 0)
            adapterRecipe.addData(Global.subCatList);
        setupFooter();
//        callForSubCategory();
//        getLocalDataOfSubCat();
    }

    private void callForSubCategory() {
        if (!Utils.isInternetOn()) return;
//        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        MyLog.e("SubCat", " CatId is " + Global.categoriId);
        Global.subCatList = new ArrayList<>();
        params.put("cat_id", Global.categoriId + "");
        client.post(Global.BASE + Global.API_SUB_CAT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("subCat", response.toString());
                categoryList = gson.fromJson(response.toString(), MCategoryList.class);
//                Global.subCatList = categoryList.getCategories();
                Log.e("subCat", " subList " + Global.subCatList.size());
                if (Global.subCatList.size() < 0) {
                    return;
                } else {
                    adapterRecipe.addData(Global.subCatList);

                }
                DBManager.getInstance().addAllData(DBManager.TABLE_SUB_CATEGORY, Global.subCatList, "categoryId");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("subCat", responseString);
            }
        });
    }

    private void getLocalDataOfSubCat() {
        Global.subCatList = DBManager.getInstance().getList(DBManager.getSubCatQuery(Global.categoriId), new MCategory());
        if (Global.subCatList.size() < 0) {
            return;
        } else {
            adapterRecipe.addData(Global.subCatList);

        }
    }

    private void setupFooter() {

        footerTab.setSelectedTabIndicatorHeight(0);


        footerTab.addTab(footerTab.newTab().setText(Global.TAB_CATEGORY));
        footerTab.addTab(footerTab.newTab().setText(Global.TAB_ORDER));
        footerTab.addTab(footerTab.newTab().setText(Global.TAB_RECIPE));
//        for (int i = 0; i < footerTab.getTabCount(); i++) {
//            //noinspection ConstantConditions
//            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//            tv.setTextColor(Color.WHITE);
//            Utils.setFont("AvenirNext-Regular", tv);
//            footerTab.getTabAt(i).setCustomView(tv);
//
//        }
        footerTab.setTabTextColors(Color.WHITE, Color.WHITE);
        for (int i = 0; i < footerTab.getTabCount(); i++) {
            footerTab.getTabAt(i).setIcon(footerIcon(i, false));
        }
        footerTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainActivity.selectTabPos = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:

                        startActivity(new Intent(SubCategoryActivity.this, MainActivity.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(SubCategoryActivity.this, MainActivity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(SubCategoryActivity.this, MainActivity.class));
                        finish();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    MainActivity.selectTabPos = tab.getPosition();
                    startActivity(new Intent(SubCategoryActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private int footerIcon(int id, boolean isActive) {
        if (footerIcons == null) {
            footerIcons = new ArrayList<>();
            footerIcons.add(R.drawable.icon_cat);
            footerIcons.add(R.drawable.icon_order);
            footerIcons.add(R.drawable.icon_contact);

            footerIcons.add(R.drawable.icon_cat_active);
            footerIcons.add(R.drawable.icon_order_active);
            footerIcons.add(R.drawable.icon_contact_active);
        }

        return isActive ? footerIcons.get(id + 3) : footerIcons.get(id);

    }
}
