package com.swapnopuri.shopping.cart.my.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdCategoryList;
import com.swapnopuri.shopping.cart.my.adapter.AdRecipe;
import com.swapnopuri.shopping.cart.my.adapter.ColorAdapter;
import com.swapnopuri.shopping.cart.my.adapter.SizeAdapter;
import com.swapnopuri.shopping.cart.my.fragment.FragCategoryList;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MVideo;
import com.swapnopuri.shopping.cart.my.tools.CustomRequest;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.DatabaseHelper;
import com.swapnopuri.shopping.cart.my.tools.EndlessScrollListener;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.swapnopuri.shopping.cart.my.tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jewel on 7/12/2016.
 */
public class ActivityRecipeList extends AppCompatActivity implements View.OnClickListener {
    public static MCategory category;
    private static ArrayList<Integer> footerIcons;
    private final String TAG = ActivityRecipeList.class.getSimpleName();
    public TabLayout footerTab;
    private Context context;
    private RecyclerView recyclerView, recSubCat;
    private ArrayList<MRecipe> recipes2;
    private ArrayList<MRecipe> tmpRecipies;
    //    public static ArrayList<MRecipe> products;
    private AdRecipe adapterRecipe;
    private int dataSize = 0, videoSize;
    private RequestQueue requestQueue;
    private Gson gson;
    public static int showSizeColor, tv;
    public static int fragCatImg;
    public static int ActCatImg;
    //    private TextView tvTitle, tvVideoNo;
    private TextView tvSubCat;
    private ImageView imgBack, imgSearch, imgShopping;
    //    private FrameLayout flVideo;
    DatabaseHelper db;
    //    private MCategoryList categoryList;
    private AdCategoryList adCategoryList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private static LinearLayout lnRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_recipe_list_new);
        init();
        setupFooter();
//        callForSubCategory();
        getProductListFromOnline();
        getLocalData();
        prepareDisplay();
        MyApp.getInstance().setupAnalytics("Recipe List");
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
////        getLocalData();
//        prepareDisplay();
//        MyApp.getInstance().setupAnalytics("Recipe List");
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        context = this;
        db = new DatabaseHelper(this);
        lnRecipe = (LinearLayout) findViewById(R.id.lnRecipeList);
        tvSubCat = (TextView) findViewById(R.id.tvSubCategory);
        Utils.setFont("AvenirNext-Regular", tvSubCat);
        tvSubCat.setText(Global.categoryTitle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tvTitle = (TextView) findViewById(R.id.tvTitle);
//        tvVideoNo = (TextView) toolbar.findViewById(R.id.tvVideoNo);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgShopping = (ImageView) findViewById(R.id.imgShopping);

        footerTab = (TabLayout) findViewById(R.id.footerTab);

//        flVideo = (FrameLayout) findViewById(R.id.flVideo);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgShopping.setOnClickListener(this);
//        flVideo.setOnClickListener(this);


//        Utils.setFont(tvTitle);

        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView = (RecyclerView) findViewById(R.id.recSubCat);
//        recSubCat.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // recyclerView.addItemDecoration(new_pic SpacesItemDecoration(20));
        recyclerView.addOnScrollListener(new EndlessScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                MyLog.e("LOad", "more : " + current_page);
                getProductListFromOnline();

            }
        });

        Global.products = new ArrayList<>();
        recipes2 = new ArrayList<>();
        gson = new Gson();

//        adCategoryList = new AdCategoryList(this) {
//            @Override
//            public void onClickItem(int itemPositon, View view) {
//
//                if (Global.subCatList != null && Global.subCatList.size() > 0) {
//                    category = Global.subCatList.get(itemPositon);
//                    Global.categoriId = Global.subCatList.get(itemPositon).getCategoryId();
//                    Global.categoryTitle = Global.subCatList.get(itemPositon).getCategoryTitle();
//                    ActivityRecipeList.showSizeColor = 1;
//                    ActivityRecipeList.fragCatImg = 1;
//
//                    drawerLayout.setVisibility(View.GONE);
//                    lnRecipe.setVisibility(View.VISIBLE);
//
//                }
//            }
//        };
//
//        recSubCat.setAdapter(adCategoryList);

        adapterRecipe = new AdRecipe(context) {
            @Override
            public void onClickItem(int position, View view) {
                if (view.getId() == R.id.imgCart) {

//                    MRecipe recipe = recipes2.get(position);
                    addToOrderCart(position);
                    tv = 1;
                    ActivityRecipe.id = position;
                    Global.productPos = position;
                    if (Global.products.get(position).getSelectedSize() == null || Global.products.get(position).getSelectedColor() == null || Global.products.get(position).getSelectedSize().equals("") || Global.products.get(position).getSelectedColor().equals("") || Global.products.get(position).getSelectedSize().equals("null") || Global.products.get(position).getSelectedColor().equals("null"))
                        diaSizeColor();


                    adapterRecipe.notifyDataSetChanged();


                } else {
                    tv = 0;
                    MRecipe recipe = tmpRecipies.get(position);
                    if (recipe.getIsNew() == 0) {
                        recipe.setIsNew(1);
                        DBManager.getInstance().addRecipeData(recipe, false, DBManager.TABLE_RECEIPE);
                    }
                    Global.productId = Global.products.get(position).getId();

                    ActivityRecipe.id = position;

                    showSizeColor = 0;
//                    tv = 0;
                    Global.orderLayoutDissmiss = 1;
                    Global.singleImageURL = Global.products.get(position).getThumb();
                    ActivityRecipe.recipe = Global.products.get(position);
                    startActivity(new Intent(context, ActivityRecipe.class).putExtra("productId", recipe.getId()));
//                    finish();
                }

            }
        };
        recyclerView.setAdapter(adapterRecipe);


    }

    private void addToOrderCart(int position) {

        MRecipe recipe = Global.products.get(position);
        MOrderedItem orderedItem = new MOrderedItem();

        orderedItem.setId(recipe.getId());
        if (recipe.getDiscountRate() == 0)
            orderedItem.setAmount(recipe.getPrice());
        else
            orderedItem.setAmount(recipe.getPrice() - recipe.getPrice() * (recipe.getDiscountRate() / 100f));
//                    orderedItem.setOrderId(FragOrder.getOrderId());
        orderedItem.setQuantity(1);
        orderedItem.setThumb(recipe.getThumb());

        // set date new
        orderedItem.setDate(Utils.getDateTime());
        orderedItem.setPos(position);

        orderedItem.setTitle(recipe.getTitle());
        orderedItem.setUserId(Utils.getUser().getId());


        FragOrder.addCurrentItems(orderedItem);
    }

    private void diaSizeColor() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.lay_dia_size_color);
        RecyclerView recSize = (RecyclerView) dialog.findViewById(R.id.recSize);
        RecyclerView recColor = (RecyclerView) dialog.findViewById(R.id.recColor);
        ImageView imgBack = (ImageView) dialog.findViewById(R.id.imgBack);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        ColorAdapter colorAdapter = new ColorAdapter(this);
        SizeAdapter sizeAdapter = new SizeAdapter(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recSize.setLayoutManager(layoutManager);
        recColor.setLayoutManager(layoutManager2);
        recColor.setAdapter(colorAdapter);
        recSize.setAdapter(sizeAdapter);
        if (Global.products.get(ActivityRecipe.id).getColors().size() > 0)
            colorAdapter.setData(Global.products.get(ActivityRecipe.id).getColors());
        if (Global.products.get(ActivityRecipe.id).getSize().size() > 0)
            sizeAdapter.setData(Global.products.get(ActivityRecipe.id).getSize());
        Button btnOk = (Button) dialog.findViewById(R.id.btnAddToCart);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterRecipe.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterRecipe.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterRecipe.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Global.products.size() > 0 && Global.products != null) {
            MyLog.e("Receipe", " getDbManager " + Global.products.size());
            adapterRecipe.setData(Global.products);
            MyLog.e("Resume", "restart " + ActivityRecipe.id);
        }

//        getLocalData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        adapterRecipe.notifyDataSetChanged();
        MyLog.e("Resume", "man " + ActivityRecipe.id);
//        getLocalData();

//        if (Global.products.size() <= 0) {
//            return;
//        } else {
//            MyLog.e("Resume", "isNew " + Global.products.get(ActivityRecipe.id).getIsNew());
//            adapterRecipe.setData(Global.products);
//        }
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
//                categoryList = gson.fromJson(response.toString(), MCategoryList.class);
//                Global.subCatList = categoryList.getCategories();
                Log.e("subCat", " subList " + Global.subCatList.size());
                getLocalData();
                DBManager.getInstance().addAllData(DBManager.TABLE_SUB_CATEGORY, Global.subCatList, "categoryId");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("subCat", responseString);
            }
        });
    }

    private void getOnlineData1() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        Map<String, String> params = new HashMap<>();
        params.put("cat", category.getCategoryId() + "");
        params.put("startat", dataSize + "");
        MyLog.e(TAG, dataSize + ":" + category.getCategoryId());
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_RECIPIES, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyLog.e("TEST_recipe", response.toString());
                try {
                    Global.products = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("recipes").toString(), MRecipe[].class)));
                    DBManager.getInstance().addAllData(DBManager.TABLE_RECEIPE, Global.products, "Id");
                    getLocalData();
                    prepareDisplay();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(customRequest);
    }

    private void getProductListFromOnline() {
        Log.e("Receipe", "method " + category.getCategoryId() + "");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("cat", Global.categoriId + "");
        params.put("startat", dataSize + "");
        client.get(Global.BASE + Global.API_RECIPIES, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Receipe", " response " + response.toString());
                try {

                    recipes2 = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("recipes").toString(), MRecipe[].class)));
//                    recipes2 = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("recipes").toString(), MRecipe[].class)));
//                    DBManager.getInstance().addAllData(DBManager.TABLE_RECEIPE, products, "Id");
                    saveRecipiList();
                    getLocalData();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Receipe", "failer");
            }
        });

    }

    private void saveRecipiList() {
        if (recipes2 != null && recipes2.size() > 0) {
            for (MRecipe mRecipe : recipes2) {
                DBManager.getInstance().addRecipeData(mRecipe, true, DBManager.TABLE_RECEIPE);
            }
        }

    }


    private void getLocalData() {
//        Global.subCatList = DBManager.getInstance().getList(DBManager.getRecipeQuery(Global.categoriId), new MCategory());


        Global.products = DBManager.getInstance().getRecipeData(Global.categoriId);

//        if (Global.products.size() > 0) {
//            for (int i = 0; i < Global.products.size(); i++) {
//
//                for (int j = 0; j < Global.products.get(i).getSize().size(); j++) {
//                    if (j == 0)
//                        Global.products.get(i).getSize().get(j).setClick(1);
//                    else
//                        Global.products.get(i).getSize().get(j).setClick(0);
//                }
//            }
//            for (int i = 0; i < Global.products.size(); i++) {
//
//                for (int j = 0; j < Global.products.get(i).getColors().size(); j++) {
//                    if (j == 0)
//                        Global.products.get(i).getColors().get(j).setClick(1);
//                    else
//                        Global.products.get(i).getColors().get(j).setClick(0);
//                }
//            }
//        }
//        products = DBManager.getInstance().getList(DBManager.getRecipeQuery(category.getCategoryId()), new MRecipe());
//        products = DBManager.getInstance().getList(DBManager.getRecipeQueryJointTable(category.getCategoryId()), new MRecipe());
//        recipes2 = db.getRecipeData(category.getCategoryId());
        MyLog.e("Receipe", " getDbHelper " + recipes2.size());
        MyLog.e("Receipe", " getDbManager " + Global.products.size());
//        tmpRecipies = products;
        tmpRecipies = Global.products;
        videoSize = DBManager.getInstance().getData(DBManager.TABLE_VIDEO, new MVideo()).size();
        MyApp.getInstance().setRecipes(Global.products);
        dataSize = Global.products.size();

        if (Global.products.size() <= 0) {
            return;
        } else {
            for (int i = 0; i < Global.products.size(); i++) {

                for (int j = 0; j < Global.products.get(i).getSize().size(); j++) {
                    if (j == 0)
                        Global.products.get(i).getSize().get(j).setClick(1);
                    else
                        Global.products.get(i).getSize().get(j).setClick(0);
                }
            }
            for (int i = 0; i < Global.products.size(); i++) {

                for (int j = 0; j < Global.products.get(i).getColors().size(); j++) {
                    if (j == 0)
                        Global.products.get(i).getColors().get(j).setClick(1);
                    else
                        Global.products.get(i).getColors().get(j).setClick(0);
                }
            }
            adapterRecipe.setData(Global.products);
        }


    }

    private void prepareDisplay() {

//        getSupportActionBar().setTitle("");
//        tvTitle.setText(category.getCategoryTitle());
//        tvVideoNo.setText(Utils.convertNum(videoSize + ""));
//        if (fragCatImg == 1) {
//            Picasso.with(context).load(Global.API_BASE_BACKEND + category.getCategoryThumb()).into((ImageView) findViewById(R.id.img));
//
//        } else {
//            Picasso.with(context).load(category.getCategoryThumb()).into((ImageView) findViewById(R.id.img));
//
//        }
        if (FragCategoryList.image == 1)
            Picasso.with(context).load(Global.API_BASE_BACKEND + category.getCategoryThumb()).into((ImageView) findViewById(R.id.img));
        else if (SubCategoryActivity.image == 2) {
            Picasso.with(context).load(Global.subCatImageURL).into((ImageView) findViewById(R.id.img));
            MyLog.e("Image", " Sub " + Global.subCatImageURL);
        }

    }

//    public void setData(String q) {
//        tmpRecipies = new ArrayList<>();
//        for (MRecipe ca : Global.products) {
//            if (ca.getSearchTag().contains(q) || ca.getTitle().contains(q))
//                tmpRecipies.add(ca);
//        }
//        adapterRecipe.setData(tmpRecipies);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                startActivity(new Intent(ActivityRecipeList.this, MainActivity.class));
                break;
            case R.id.imgSearch:
                startActivity(new Intent(ActivityRecipeList.this, SearchActivity.class));
                break;
            case R.id.flVideo:
                startActivity(new Intent(ActivityRecipeList.this, ActivityVideo.class));
                break;
            case R.id.imgShopping:
                footerTab.getTabAt(2).select();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityRecipeList.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupFooter() {
        footerTab.setSelectedTabIndicatorHeight(0);


        footerTab.addTab(footerTab.newTab().setText(Global.TAB_HOME_PAGE));
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

                        startActivity(new Intent(ActivityRecipeList.this, MainActivity.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(ActivityRecipeList.this, MainActivity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(ActivityRecipeList.this, MainActivity.class));
                        finish();
                        break;
                    case 3:
                        startActivity(new Intent(ActivityRecipeList.this, MainActivity.class));
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
                    startActivity(new Intent(ActivityRecipeList.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private int footerIcon(int id, boolean isActive) {
        if (footerIcons == null) {
            footerIcons = new ArrayList<>();
            footerIcons.add(R.drawable.product_icon_white);
            footerIcons.add(R.drawable.product_icon_white);
            footerIcons.add(R.drawable.delivery_icon_white);
            footerIcons.add(R.drawable.my_account_icon_white);

            footerIcons.add(R.drawable.product_icon_black);
            footerIcons.add(R.drawable.product_icon_black);
            footerIcons.add(R.drawable.delivery_icon_black);
            footerIcons.add(R.drawable.my_account_icon_black);
        }

        return isActive ? footerIcons.get(id + 4) : footerIcons.get(id);

    }


}
