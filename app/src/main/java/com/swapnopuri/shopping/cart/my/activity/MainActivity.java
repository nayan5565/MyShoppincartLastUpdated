package com.swapnopuri.shopping.cart.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdFooterTab;
import com.swapnopuri.shopping.cart.my.adapter.AdRecipe;
import com.swapnopuri.shopping.cart.my.fragment.FragCategoryList;
import com.swapnopuri.shopping.cart.my.fragment.FragHomePage;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.fragment.FragRecipeAsk;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MCategoryList;
import com.swapnopuri.shopping.cart.my.model.MNotification;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MTimeTracker;
import com.swapnopuri.shopping.cart.my.tools.CustomRequest;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.swapnopuri.shopping.cart.my.tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int selectTabPos;
    private static ArrayList<Integer> footerIcons;
    private static MainActivity instance;
    public static TabLayout footerTab;
    private ArrayList<MCategory> subCategories;
    private MCategoryList categoryList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private AdFooterTab adapterFooter;
    private ArrayList<MRecipe> allRecipes, tmpRecipes;
    private AdRecipe adRecipe;
    private TextView tvVideoNo;
    private RequestQueue requestQueue;
    private Gson gson = new Gson();
    private Toolbar toolbar;
    private ImageView imgSearch, imgShopping;
    private FrameLayout flVideo;
    String state;
    private boolean doubleBackToExitPressedOnce;
    private MTimeTracker timeTracker;


    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupNavigation();
        setupFooter();
//        callForSubCategory();
        MyApp.getInstance().setupAnalytics("Home Screen");

        timeTracker = new MTimeTracker();
        timeTracker.setLogin(Utils.getDateTime());

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (footerTab != null) {
            footerTab.getTabAt(selectTabPos).select();
            footerTab.getTabAt(selectTabPos).setIcon(footerIcon(selectTabPos, true));
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (footerTab.getSelectedTabPosition() == 0) {
            exitFromApp();
        } else {
            footerTab.getTabAt(0).select();
            footerTab.getTabAt(0).setIcon(footerIcon(0, true));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flVideo:
                startActivity(new Intent(MainActivity.this, ActivityVideo.class));
                break;
            case R.id.imgSearch:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.imgShopping:
                footerTab.getTabAt(2).select();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        timeTracker.setLogout(Utils.getDateTime());
        DBManager.getInstance().addData(DBManager.TABLE_TIME_TRACKER, timeTracker, "id", true);
        super.onDestroy();
    }

    private void init() {
        instance = this;
        state = Utils.getPref("profile2", Global.STATE_TUT);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont("AvenirNext-Regular", mTitle);
        toolbar.setBackgroundColor(Color.parseColor("#333333"));
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        flVideo = (FrameLayout) toolbar.findViewById(R.id.flVideo);

        footerTab = (TabLayout) findViewById(R.id.footerTab);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgShopping = (ImageView) findViewById(R.id.imgShopping);
        imgShopping.setOnClickListener(this);

        tvVideoNo = (TextView) findViewById(R.id.tvVideoNo);


        imgSearch.setOnClickListener(this);
        flVideo.setOnClickListener(this);

        allRecipes = new ArrayList<>();
        tmpRecipes = new ArrayList<>();

//        adRecipe = new AdRecipe(this) {
//            @Override
//            public void onClickItem(int position, View view) {
//                ActivityRecipe.recipe = tmpRecipes.get(position);
//                startActivity(new Intent(MainActivity.this, ActivityRecipe.class));
//            }
//        };


        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }


    private void exitFromApp() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Utils.showToast(Global.MSG_EXIT);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    private void getLocalDataOfSubCat() {
        subCategories = DBManager.getInstance().getList(DBManager.getRecipeQuery(Global.categoriId), new MCategory());
    }

    private void setupNavigation() {
        state = Utils.getPref("profile2", Global.STATE_TUT);
        Menu menu = navigationView.getMenu();
        MenuItem target = menu.findItem(R.id.mnuLogout);
        if (state.equals(Global.STATE_PROFILE2)) {
            target.setVisible(true);

        } else {
            target.setVisible(false);

        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.mnuAbout:
                        Utils.showDialog(MainActivity.this, "App Version", "Version 1.0.2");
                        break;
                    case R.id.mnuFav:
                        footerTab.getTabAt(2).select();
                        break;
                    case R.id.mnuHome:
                        footerTab.getTabAt(0).select();
//                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                        finish();
                        break;
                    case R.id.mnuCategory:
                        footerTab.getTabAt(1).select();
                        break;
                    case R.id.mnuAsk:
                        footerTab.getTabAt(3).select();

                        break;
                    case R.id.mnuHalnagad:
                        //halnagad();
                        break;
                    case R.id.mnuProfile:
//                        state = Utils.getPref("profile2", Global.STATE_TUT);
//                        MyLog.e("state", " is " + state);
                        Utils.savePref("profile", Global.STATE_PROFILE);
                        if (state.equals(Global.STATE_PROFILE2)) {
                            ProfileActivity.start(MainActivity.this);
                        } else {
                            RegistrationActivity.start(MainActivity.this);
                        }

                        break;
                    case R.id.mnuFavorite:
                        FavoriteActivity.start(MainActivity.this);
                        break;
                    case R.id.mnuLogout:
                        Utils.clearPref();
                        LoginActivity.start(MainActivity.this);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    private void halnagad() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Global.API_NOTIFICATION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    MNotification notification = gson.fromJson(response.getJSONArray("notification").get(0).toString(), MNotification.class);
                    openDialog(notification);
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


    private void setupFooter() {
        adapterFooter = new AdFooterTab(getSupportFragmentManager());
        adapterFooter.addFragment(FragHomePage.newInstance(), Global.TAB_HOME_PAGE);
        adapterFooter.addFragment(FragCategoryList.newInstance(), Global.TAB_CATEGORY);
        adapterFooter.addFragment(FragOrder.newInstance(), Global.TAB_ORDER);
        adapterFooter.addFragment(FragRecipeAsk.getInstance(), Global.TAB_RECIPE);
        viewPager.setAdapter(adapterFooter);
        footerTab.setupWithViewPager(viewPager);
        footerTab.setSelectedTabIndicatorHeight(0);
        footerTab.setTabTextColors(Color.WHITE, Color.parseColor(Global.COLOR_MAIN));


        for (int i = 0; i < footerTab.getTabCount(); i++) {
            footerTab.getTabAt(i).setIcon(footerIcon(i, false));
        }
//        for (int i = 0; i < footerTab.getTabCount(); i++) {
//            //noinspection ConstantConditions
//            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
////            tv.setTextColor(Color.WHITE);
//            Utils.setFont("AvenirNext-Regular", tv);
//            footerTab.getTabAt(i).setCustomView(tv);
//
//        }
        footerTab.getTabAt(selectTabPos).select();
        footerTab.getTabAt(selectTabPos).setIcon(footerIcon(selectTabPos, true));
        footerTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tab.setIcon(footerIcon(tab.getPosition(), true));

                viewPager.setCurrentItem(tab.getPosition());
                footerTab.setTabTextColors(Color.WHITE, Color.parseColor(Global.COLOR_MAIN));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(footerIcon(tab.getPosition(), false));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private int footerIcon(int pos, boolean isActive) {
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

        return isActive ? footerIcons.get(pos + 4) : footerIcons.get(pos);

    }

//    public void setData(String q) {
//        tmpRecipes = new ArrayList<>();
//        for (MRecipe ca : allRecipes) {
//            if (ca.getSearchTag().contains(q) || ca.getTitle().contains(q))
//                tmpRecipes.add(ca);
//        }
//        adRecipe.setData(tmpRecipes);
//    }

    public void setVideoNo(int no) {
        tvVideoNo.setText(Utils.convertNum(no + ""));
    }

    private void openDialog(MNotification mNotification) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_dialog);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvDetails = (TextView) dialog.findViewById(R.id.tvDetails);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

//        tvTitle.setText(mNotification.getText());
//        tvDetails.setText(mNotification.getDetails());

        Utils.setFont(tvTitle, tvDetails);

        btnOk.setText(Global.MSG_OK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}
