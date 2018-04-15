package com.swapnopuri.shopping.cart.my.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipeList;
import com.swapnopuri.shopping.cart.my.activity.MainActivity;
import com.swapnopuri.shopping.cart.my.activity.SubCategoryActivity;
import com.swapnopuri.shopping.cart.my.adapter.AdCategoryList;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MCategoryList;
import com.swapnopuri.shopping.cart.my.model.MVideo;
import com.swapnopuri.shopping.cart.my.model.MsubCategory;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by JEWEL on 7/12/2016.
 */
public class FragCategoryList extends Fragment {
    public static FragCategoryList instance;
    private final String TAG = FragCategoryList.class.getSimpleName();
    private View viewMain;
    private Context context;
    private RecyclerView recyclerView, recSubCat;
    private ArrayList<MCategory> categories;
    private ArrayList<MCategory> subCategories;
    public static int image;
    private AdCategoryList adCategoryList;
    private RequestQueue requestQueue;
    private MCategoryList categoryList;
    private int videoNo;
    private MsubCategory msubCategory;
    private Gson gson = new Gson();

    public static FragCategoryList newInstance() {
//
        return new FragCategoryList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.frag_category_list, container, false);
        init();
        prepareDisplay();
        return viewMain;
    }

    private void init() {

        context = getContext();
        recyclerView = (RecyclerView) viewMain.findViewById(R.id.recyclerView);
        recSubCat = (RecyclerView) viewMain.findViewById(R.id.recSubCat);
        recSubCat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryList = new MCategoryList();
        categories = new ArrayList<>();
        subCategories = new ArrayList<>();

        adCategoryList = new AdCategoryList(context) {
            @Override
            public void onClickItem(int itemPositon, View view) {
                if (categories != null && categories.size() > 0) {
                    ActivityRecipeList.category = categories.get(itemPositon);
                    Global.categoriId = categories.get(itemPositon).getCategoryId();
                    Global.categoryTitle = categories.get(itemPositon).getCategoryTitle();
                    ActivityRecipeList.showSizeColor = 1;
                    ActivityRecipeList.fragCatImg = 1;
                    image = 1;
                    SubCategoryActivity.image = 0;
                    callForSubCategory();
                    MyLog.e("SUBCAT", " SIZEis" + Global.subCatList.size());

//

                }
            }
        };
        recyclerView.setAdapter(adCategoryList);
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
//                categoryList = gson.fromJson(response.toString(), MsubCategory.class);
                try {
                    Global.subCatList = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("categories").toString(), MsubCategory[].class)));
//                    for (int i = 0; i < Global.subCatList.size(); i++) {
//                        msubCategory = new MsubCategory();
//                        msubCategory.setCat_id(Global.categoriId);
//                        Global.subCatList.add(msubCategory);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                addDb();

                Log.e("subCat", " subList " + Global.subCatList.size());
                if (Global.subCatList.size() <= 0) {
                    startActivity(new Intent(getContext(), ActivityRecipeList.class));
                } else {
                    startActivity(new Intent(getContext(), SubCategoryActivity.class));
//                    adCategoryList.addData(Global.subCatList);
                }
//                DBManager.getInstance().addAllData(DBManager.TABLE_SUB_CATEGORY, Global.subCatList, Global.categoriId + "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("subCat", responseString);
            }
        });
    }

    private void getLocalData() {
        categories = DBManager.getInstance().getData(DBManager.TABLE_CATEGORY, new MCategory());
        videoNo = DBManager.getInstance().getData(DBManager.TABLE_VIDEO, new MVideo()).size();
        if (categories != null && categories.size() > 0)
            adCategoryList.addData(categories);
    }

    private void addDb() {
        for (MsubCategory msubCategory : Global.subCatList) {
            DBManager.getInstance().addSubCatJson(msubCategory);
        }
    }

    private void getLocalDataOfSubCat() {
        Global.subCatList = DBManager.getInstance().getList(DBManager.getRecipeQuery(Global.categoriId), new MCategory());
        adCategoryList.addData(categories);
    }

    private void prepareDisplay() {
        getLocalData();
        MainActivity.getInstance().setVideoNo(videoNo);
    }

    public void setData(String q) {
        ArrayList<MCategory> c = new ArrayList<>();
        for (MCategory ca : categories) {
            try {
                if (ca.getCategoryTitle().startsWith(q)) {
                    c.add(ca);
                }
            } catch (Exception e) {
                MyLog.e("ERR", e.toString());
            }
        }
        adCategoryList.addData(c);
    }
}
