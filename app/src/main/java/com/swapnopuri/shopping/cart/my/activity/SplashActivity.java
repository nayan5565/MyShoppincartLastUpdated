package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MBanner;
import com.swapnopuri.shopping.cart.my.model.MBrand;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MCategoryList;
import com.swapnopuri.shopping.cart.my.model.MHomeList;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MSync;
import com.swapnopuri.shopping.cart.my.model.MsubCategory;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.swapnopuri.shopping.cart.my.tools.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jewel on 7/13/2016.
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private ArrayList<MCategory> categories;
    private ArrayList<MCategory> subCategories;
    private MCategoryList categoryList;
    private Gson gson;
    private MHomeList mHomeList;
    private ProgressBar progressBar;
    private ArrayList<MBrand> brandArrayList;
    private ArrayList<MBanner> bannerArrayList;
    private ArrayList<MRecipe> flashArrayList;
    private ArrayList<MCategory> categoryArrayList;
    private ArrayList<MRecipe> trendyArrayList;
    private ArrayList<MRecipe> topWeekArrayList;
    TextView tvTitle, tvTitle3;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_splash);
        init();


    }

    private void init() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle3 = (TextView) findViewById(R.id.tvTitle3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        gson = new Gson();
        Utils.setFont("AvenirNext-Regular", tvTitle, tvTitle3);
        requestQueue = VolleySingleton.getInstance().getRequestQueue();

        if (DBManager.getInstance().getData(DBManager.TABLE_CATEGORY, new MCategory()).size() <= 0) {
//            callForCategory();
            getHomeListFromOnline();
        } else
            callForSync();


        if (!Utils.isInternetOn()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private void getHomeListFromOnline() {
        if (!Utils.isInternetOn()) return;
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Global.BASE + Global.API_HOME_LIST, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

//                progressBar.setVisibility(View.GONE);
                Log.e("NEW", response.toString());
                mHomeList = gson.fromJson(response.toString(), MHomeList.class);
                brandArrayList = mHomeList.getBrands();
                bannerArrayList = mHomeList.getBanner();
                topWeekArrayList = mHomeList.getTopweek();
                trendyArrayList = mHomeList.getTrendy();
                flashArrayList = mHomeList.getFlash();
                categoryArrayList = mHomeList.getCategories();

                MyLog.e("List ", " Flash online size " + flashArrayList.size());
                MyLog.e("List ", " brand online size " + brandArrayList.size());
                MyLog.e("List ", " Banner online size " + bannerArrayList.size());
                MyLog.e("List ", " Trendy online size " + trendyArrayList.size());
                MyLog.e("List ", " TopWeek online size " + topWeekArrayList.size());
                MyLog.e("List ", " Cat online size " + categoryArrayList.size());
                DBManager.getInstance().addAllData(DBManager.TABLE_CATEGORY2, categoryArrayList, "categoryId");
//                DBManager.getInstance().addRecipeData(DBManager.TABLE_FLASH, flashArrayList, "Id");
//                DBManager.getInstance().addAllData(DBManager.TABLE_TOP_WEEK, topWeekArrayList, "Id");
//                DBManager.getInstance().addAllData(DBManager.TABLE_TRENDY, trendyArrayList, "Id");
                DBManager.getInstance().addAllData(DBManager.TABLE_BANNER, bannerArrayList, "id");
                DBManager.getInstance().addAllData(DBManager.TABLE_BRAND, brandArrayList, "id");
                saveFlashList();
                saveTrendyList();
                saveTopWeekList();
                callForCategory();
                callForSync();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressBar.setVisibility(View.GONE);
                Log.e("NEW-ERR", responseString);
            }
        });
    }

    private void saveFlashList() {
        for (MRecipe mRecipe : flashArrayList) {
            DBManager.getInstance().addRecipeData(mRecipe, true, DBManager.TABLE_FLASH);
        }
    }

    private void saveTrendyList() {
        for (MRecipe mRecipe : trendyArrayList) {
            DBManager.getInstance().addRecipeData(mRecipe, true, DBManager.TABLE_TRENDY);
        }
    }

    private void saveTopWeekList() {
        for (MRecipe mRecipe : topWeekArrayList) {
            DBManager.getInstance().addRecipeData(mRecipe, true, DBManager.TABLE_TOP_WEEK);
        }
    }

    private void callForSubCategory() {
        if (!Utils.isInternetOn()) return;
//        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        MyLog.e("SubCat", " CatId is " + Global.categoriId);
        Global.subCatList = new ArrayList<>();
        params.put("cat_id", 162 + "");
        client.post(Global.BASE + Global.API_SUB_CAT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("subCat", response.toString());
//                categoryList = gson.fromJson(response.toString(), MCategoryList.class);
//                Global.subCatList = categoryList.getCategories();
                Global.subCatList = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), MsubCategory[].class)));
                DBManager.getInstance().addAllData(DBManager.TABLE_SUB_CATEGORY, Global.subCatList, "categoryId");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("subCat", responseString);
            }
        });
    }

    private void callForCategory() {
        if (!Utils.isInternetOn()) return;
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Global.BASE + Global.API_CATEGORY_LIST, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressBar.setVisibility(View.GONE);
                Log.e("NEW", response.toString());
                categoryList = gson.fromJson(response.toString(), MCategoryList.class);
                categories = categoryList.getCategories();
                DBManager.getInstance().addAllData(DBManager.TABLE_CATEGORY, categories, "categoryId");
//                getHomeListFromOnline();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
//                callForSync();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressBar.setVisibility(View.GONE);
                Log.e("NEW-ERR", responseString);
            }
        });
    }

    private void callForSync() {
        if (!Utils.isInternetOn()) return;
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        params.put("send", "01-01-2015");
        params.put("send", Utils.getPref(Global.SYNC, "01-01-2015"));
        client.post(Global.BASE + Global.API_SYNC, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Sync", response.toString());
                progressBar.setVisibility(View.GONE);
                sync(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressBar.setVisibility(View.GONE);
                Log.e("NEW-ERR", responseString);
            }
        });
    }


    private void sync(String response) {
        MSync sync = gson.fromJson(response, MSync.class);
        ArrayList<MCategory> categories = sync.getCategory();
        ArrayList<MRecipe> recipes = sync.getRecipes();
        if (categories != null && categories.size() > 0) {
            DBManager.getInstance().addAllData(DBManager.TABLE_CATEGORY, categories, "categoryId");
            for (MCategory category : categories) {
                if (category.getCategoryDelete() == 1)
                    DBManager.getInstance().deleteData(DBManager.TABLE_CATEGORY, "categoryId", category.getCategoryId() + "");
            }
        }
        if (recipes != null && recipes.size() > 0) {
            DBManager.getInstance().addAllData(DBManager.TABLE_RECEIPE, recipes, "Id");
            for (MRecipe recipe : recipes) {
                if (recipe.getRecipeDelete() == 1)
                    DBManager.getInstance().deleteData(DBManager.TABLE_RECEIPE, "Id", recipe.getId() + "");
            }
        }


        Utils.savePref(Global.SYNC, Utils.getToday());
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }


}
