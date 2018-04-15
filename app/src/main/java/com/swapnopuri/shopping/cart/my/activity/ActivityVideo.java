package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdVideo;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MVideo;
import com.swapnopuri.shopping.cart.my.tools.CustomRequest;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.swapnopuri.shopping.cart.my.tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActivityVideo extends AppCompatActivity implements View.OnClickListener {
    private static ArrayList<Integer> footerIcons;
    public TabLayout footerTab;
    private Context context;
    private RecyclerView recyclerView;
    private AdVideo adapter;
    private RequestQueue requestQueue;
    private Gson gson;
    private ArrayList<MVideo> videos;
    private ImageView imgBack,imgSearch;
    private TextView tvTitle;
    private Toolbar toolbar;
    private FrameLayout flVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_recipe_list);
        init();
        setupFooter();
        getOnlineData();
        prepareDisplay();
        MyApp.getInstance().setupAnalytics("Video");
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        context = this;
        gson = new Gson();
        videos = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        flVideo = (FrameLayout) findViewById(R.id.flVideo);
        footerTab= (TabLayout) findViewById(R.id.footerTab);

        flVideo.setVisibility(View.GONE);

        tvTitle.setText(Global.TITLE_VIDEO);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Utils.setFont(tvTitle);


        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);


        adapter = new AdVideo(this) {
            @Override
            public void onClickItem(int itemPositon, View view) {
                ActivityRecipe.recipe = getRecipe(videos.get(itemPositon));
                startActivity(new Intent(ActivityVideo.this, ActivityRecipe.class));
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private MRecipe getRecipe(MVideo video) {
        MRecipe recipe = new MRecipe();
        recipe.setId(video.getId());
        recipe.setTitle(video.getMenuTitle());
        recipe.setCategoryId(video.getCategoryId());
        recipe.setFav(video.getFav());
        recipe.setCategoryTitle(video.getCategoryTitle());
        recipe.setIngredients(video.getIngredients());
        recipe.setPhoto(video.getPPhoto());
        recipe.setProcess(video.getProcess());
        recipe.setSearchTag(video.getSearchTag());
        recipe.setThumb(video.getThumb());
        recipe.setVideo(video.getMenuVideo());

        recipe.setTypeOne(video.getTypeOne());
        recipe.setTypeTwo(video.getTypeTwo());
        recipe.setTypeThree(video.getTypeThree());
        recipe.setTypeFour(video.getTypeFour());
        recipe.setTypeFive(video.getTypeFive());
        return recipe;
    }

    private void getOnlineData() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        Map<String, String> params = new HashMap<>();
        params.put("appId", "");
        params.put("deviceId", Utils.getDeviceId(this));
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_VIDEOS, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    videos = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("videos").toString(), MVideo[].class)));
                    DBManager.getInstance().addAllData(DBManager.TABLE_VIDEO, videos, "Id");
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

    private void prepareDisplay() {

        videos = DBManager.getInstance().getData(DBManager.TABLE_VIDEO, new MVideo());
        adapter.addData(videos);
    }


    private void setupFooter() {

        footerTab.setSelectedTabIndicatorHeight(0);
        footerTab.setTabTextColors(Color.WHITE, Color.WHITE);

        footerTab.addTab(footerTab.newTab().setText(Global.TAB_CATEGORY));
        footerTab.addTab(footerTab.newTab().setText(Global.TAB_FAV));
        footerTab.addTab(footerTab.newTab().setText(Global.TAB_RECIPE));


        for (int i = 0; i < footerTab.getTabCount(); i++) {

            footerTab.getTabAt(i).setIcon(footerIcon(i, false));
        }
        footerTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                tab.setIcon(footerIcon(tab.getPosition(),true));
                MainActivity.selectTabPos = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        startActivity(new Intent(ActivityVideo.this, MainActivity.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(ActivityVideo.this, MainActivity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(ActivityVideo.this, MainActivity.class));
                        finish();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    MainActivity.selectTabPos = tab.getPosition();
                    startActivity(new Intent(ActivityVideo.this, MainActivity.class));
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgSearch:
                startActivity(new Intent(ActivityVideo.this,SearchActivity.class));
                break;
        }
    }

}
