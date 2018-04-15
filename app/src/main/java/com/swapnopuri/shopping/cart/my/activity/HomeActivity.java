package com.swapnopuri.shopping.cart.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.BannerAdapter;
import com.swapnopuri.shopping.cart.my.adapter.BrandAdapter;
import com.swapnopuri.shopping.cart.my.adapter.CategoriesAdapter;
import com.swapnopuri.shopping.cart.my.adapter.ColorAdapter;
import com.swapnopuri.shopping.cart.my.adapter.FlashAdapter;
import com.swapnopuri.shopping.cart.my.adapter.Sliding_Images_Adapter;
import com.swapnopuri.shopping.cart.my.adapter.TopWeekAdapter;
import com.swapnopuri.shopping.cart.my.adapter.TrendAdapter;
import com.swapnopuri.shopping.cart.my.fragment.FragBannerLink;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.fragment.FragTutorial;
import com.swapnopuri.shopping.cart.my.model.MBanner;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MFlash;
import com.swapnopuri.shopping.cart.my.model.MHomeList;
import com.swapnopuri.shopping.cart.my.model.MBrand;
import com.swapnopuri.shopping.cart.my.model.MColor;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MTimeTracker;
import com.swapnopuri.shopping.cart.my.model.MTopWeek;
import com.swapnopuri.shopping.cart.my.model.MTrendy;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.EndlessScrollListener;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Dev on 10/14/2017.
 */

public class HomeActivity extends AppCompatActivity {
    public TabLayout footerTab;
    private CategoriesAdapter categoriesAdapter;
    private TopWeekAdapter topWeekAdapter;
    private BrandAdapter brandAdapter;
    private FlashAdapter flashAdapter;
    private TrendAdapter trendAdapter;
    private Sliding_Images_Adapter sliding_images_adapter;
    public static int selectTabPos;
    private static ArrayList<Integer> footerIcons;
    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Toolbar toolbar;
    private Gson gson;
    private MHomeList mHomeList;
    public static MBanner mBanner;
    private ArrayList<MBrand> brandArrayList;
    public static ArrayList<MBanner> bannerArrayList;
    private ArrayList<MRecipe> flashArrayList;
    private ArrayList<MCategory> categoryArrayList;
    public static ArrayList<MRecipe> trendyArrayList;
    public static ArrayList<MRecipe> topWeekArrayList;
    private MTimeTracker timeTracker;
    String state;
    private boolean doubleBackToExitPressedOnce;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private RecyclerView recBrand, recTrend, recCat, recFlash, recTopWeek;
    private TextView tvTop, tvTrendy, tvFlash, tvTime;
    private Button btnViewAll;
    private ImageView imgShoppingCart, imgSearch;
    private LinearLayout lnFrgBannerLink;
    private static HomeActivity instance;

    public static HomeActivity getInstance() {
        instance = new HomeActivity();
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        init();
        setupNavigation();
        setupFooter();
//        callForBrand();
        getLoaclData();
        prepareDisplay();

        MyApp.getInstance().setupAnalytics("Home Screen");

        timeTracker = new MTimeTracker();
        timeTracker.setLogin(Utils.getDateTime());
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void init() {
        lnFrgBannerLink = (LinearLayout) findViewById(R.id.frgBanner);
        lnFrgBannerLink.setVisibility(View.GONE);
        tvTop = (TextView) findViewById(R.id.tvTop);
        tvTrendy = (TextView) findViewById(R.id.tvTrendy);
        tvFlash = (TextView) findViewById(R.id.tvFlash);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        imgShoppingCart = (ImageView) findViewById(R.id.imgShopping);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        gson = new Gson();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mBanner = new MBanner();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont("AvenirNext-Regular", mTitle);
        Utils.setFont("AvenirNextCondensed-Medium", tvTop, tvTrendy, tvFlash, tvTime, btnViewAll);
        toolbar.setBackgroundColor(Color.parseColor("#333333"));
        footerTab = (TabLayout) findViewById(R.id.footerTab);

        topWeekAdapter = new TopWeekAdapter(this);
        brandAdapter = new BrandAdapter(this);
        categoriesAdapter = new CategoriesAdapter(this);
        flashAdapter = new FlashAdapter(this);
        trendAdapter = new TrendAdapter(this);


        recBrand = (RecyclerView) findViewById(R.id.recBrand);
        recTrend = (RecyclerView) findViewById(R.id.recNewTrend);
        recCat = (RecyclerView) findViewById(R.id.recCat);
        recFlash = (RecyclerView) findViewById(R.id.recFlash);
        recTopWeek = (RecyclerView) findViewById(R.id.recTopWeek);

        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imgShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerTab.getTabAt(1).select();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
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
        setupNavigation();
//        if (footerTab != null) {
//            footerTab.getTabAt(selectTabPos).select();
//            footerTab.getTabAt(selectTabPos).setIcon(footerIcon(selectTabPos, true));
//        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        exitFromApp();
//        if (footerTab.getSelectedTabPosition() == 0) {
//            exitFromApp();
//        } else {
//            footerTab.getTabAt(0).select();
//            footerTab.getTabAt(0).setIcon(footerIcon(0, true));
//        }

    }

//    public void openFragment() {
//        drawerLayout.setVisibility(View.GONE);
//        lnFrgBannerLink.setVisibility(View.VISIBLE);
//        Fragment fragment = new FragBannerLink();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frgBanner, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
//        transaction.addToBackStack(null);  // this will manage backstack
//        transaction.commit();
//    }

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

    @Override
    protected void onDestroy() {
        timeTracker.setLogout(Utils.getDateTime());
        DBManager.getInstance().addData(DBManager.TABLE_TIME_TRACKER, timeTracker, "id", true);
        super.onDestroy();
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
                        Utils.showDialog(HomeActivity.this, "App Version", "Version 1.0.2");
                        break;
                    case R.id.mnuFav:
                        footerTab.getTabAt(1).select();
                        break;
                    case R.id.mnuHome:

                        break;
                    case R.id.mnuCategory:
                        footerTab.getTabAt(0).select();
                        break;
                    case R.id.mnuAsk:
                        footerTab.getTabAt(2).select();

                        break;
                    case R.id.mnuHalnagad:
                        //halnagad();
                        break;
                    case R.id.mnuProfile:

                        MyLog.e("state", " is " + state);
                        Utils.savePref("profile", Global.STATE_PROFILE);
                        if (state.equals(Global.STATE_PROFILE2)) {
                            ProfileActivity.start(HomeActivity.this);
                        } else {
                            LogSignActivity.start(HomeActivity.this);
                        }

                        break;
                    case R.id.mnuFavorite:
                        FavoriteActivity.start(HomeActivity.this);
                        break;
                    case R.id.mnuLogout:
                        Utils.clearPref();
                        LoginActivity.start(HomeActivity.this);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    private void callForBrand() {
        if (!Utils.isInternetOn()) return;
//        progressBar.setVisibility(View.VISIBLE);
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
                getLoaclData();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
//                progressBar.setVisibility(View.GONE);
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

    private void slideBannerImage() {
        viewPager = (ViewPager) findViewById(R.id.viewpagerBanner);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        if (bannerArrayList != null && bannerArrayList.size() > 0)
            sliding_images_adapter = new Sliding_Images_Adapter(this, bannerArrayList) {
                @Override
                public void onClickItem(int position, View view) {
                    Toast.makeText(HomeActivity.this, " showSizeColor", Toast.LENGTH_LONG).show();
                    Global.bannerLink = bannerArrayList.get(position).getLink();
//                HomeActivity.getInstance().openFragment();
                    startActivity(new Intent(HomeActivity.this, FragBannerLink.class));
                }
            };
        viewPager.setAdapter(sliding_images_adapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void prepareDisplay() {

        slideBannerImage();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager7
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recBrand.setLayoutManager(layoutManager);
        recTrend.setLayoutManager(new GridLayoutManager(this, 3));
        recCat.setLayoutManager(new GridLayoutManager(this, 5));
        recFlash.setLayoutManager(new GridLayoutManager(this, 4));
        recTopWeek.setLayoutManager(layoutManager7);
        recTopWeek.addOnScrollListener(new EndlessScrollListener(layoutManager7) {
            @Override
            public void onLoadMore(int current_page) {

            }
        });


    }

    private void setDataAdapter() {
        recBrand.setAdapter(brandAdapter);

        recTrend.setAdapter(trendAdapter);
        recCat.setAdapter(categoriesAdapter);
        recFlash.setAdapter(flashAdapter);
        recTopWeek.setAdapter(topWeekAdapter);

        if (topWeekArrayList != null && topWeekArrayList.size() > 0)
            topWeekAdapter.setData(topWeekArrayList);
        if (brandArrayList != null && brandArrayList.size() > 0)
            brandAdapter.setData(brandArrayList);
        if (trendyArrayList != null && trendyArrayList.size() > 0)
            trendAdapter.setData(trendyArrayList);
        if (flashArrayList != null && flashArrayList.size() > 0)
            flashAdapter.setData(flashArrayList);
        if (categoryArrayList != null && categoryArrayList.size() > 0)
            categoriesAdapter.setData(categoryArrayList);
    }

    private void getLoaclData() {
        brandArrayList = DBManager.getInstance().getData(DBManager.TABLE_BRAND, new MBrand());
        bannerArrayList = DBManager.getInstance().getData(DBManager.TABLE_BANNER, new MBanner());
        flashArrayList = DBManager.getInstance().getRecipe(DBManager.TABLE_FLASH);
        trendyArrayList = DBManager.getInstance().getRecipe(DBManager.TABLE_TRENDY);
        topWeekArrayList = DBManager.getInstance().getRecipe(DBManager.TABLE_TOP_WEEK);
        categoryArrayList = DBManager.getInstance().getData(DBManager.TABLE_CATEGORY2, new MCategory());

        MyLog.e("List ", " Flash size " + flashArrayList.size());
        MyLog.e("List ", " brand size " + brandArrayList.size());
        MyLog.e("List ", " Banner size " + bannerArrayList.size());
        MyLog.e("List ", " Trendy size " + trendyArrayList.size());
        MyLog.e("List ", " TopWeek size " + topWeekArrayList.size());
        MyLog.e("List ", " Cat size " + categoryArrayList.size());


        if (flashArrayList.size() > 0) {
            listGenerate(flashArrayList);
//            for (int i = 0; i < flashArrayList.size(); i++) {
//
//                for (int j = 0; j < flashArrayList.get(i).getSize().size(); j++) {
//                    if (j == 0)
//                        flashArrayList.get(i).getSize().get(j).setClick(1);
//                    else
//                        flashArrayList.get(i).getSize().get(j).setClick(0);
//                }
//            }
//            for (int i = 0; i < flashArrayList.size(); i++) {
//
//                for (int j = 0; j < flashArrayList.get(i).getColors().size(); j++) {
//                    if (j == 0)
//                        flashArrayList.get(i).getColors().get(j).setClick(1);
//                    else
//                        flashArrayList.get(i).getColors().get(j).setClick(0);
//                }
//            }
        }
        if (trendyArrayList.size() > 0) {
            listGenerate(trendyArrayList);
//            for (int i = 0; i < trendyArrayList.size(); i++) {
//
//                for (int j = 0; j < trendyArrayList.get(i).getSize().size(); j++) {
//                    if (j == 0)
//                        trendyArrayList.get(i).getSize().get(j).setClick(1);
//                    else
//                        trendyArrayList.get(i).getSize().get(j).setClick(0);
//                }
//            }
//            for (int i = 0; i < trendyArrayList.size(); i++) {
//
//                for (int j = 0; j < trendyArrayList.get(i).getColors().size(); j++) {
//                    if (j == 0)
//                        trendyArrayList.get(i).getColors().get(j).setClick(1);
//                    else
//                        trendyArrayList.get(i).getColors().get(j).setClick(0);
//                }
//            }
        }
        if (topWeekArrayList.size() > 0) {
            listGenerate(topWeekArrayList);
//            for (int i = 0; i < topWeekArrayList.size(); i++) {
//
//                for (int j = 0; j < topWeekArrayList.get(i).getSize().size(); j++) {
//                    if (j == 0)
//                        topWeekArrayList.get(i).getSize().get(j).setClick(1);
//                    else
//                        topWeekArrayList.get(i).getSize().get(j).setClick(0);
//                }
//            }
//            for (int i = 0; i < topWeekArrayList.size(); i++) {
//
//                for (int j = 0; j < topWeekArrayList.get(i).getColors().size(); j++) {
//                    if (j == 0)
//                        topWeekArrayList.get(i).getColors().get(j).setClick(1);
//                    else
//                        topWeekArrayList.get(i).getColors().get(j).setClick(0);
//                }
//            }
        }

        setDataAdapter();
    }

    public void listGenerate(ArrayList<MRecipe> mRecipes) {
        for (int i = 0; i < mRecipes.size(); i++) {

            for (int j = 0; j < mRecipes.get(i).getSize().size(); j++) {
                if (j == 0)
                    mRecipes.get(i).getSize().get(j).setClick(1);
                else
                    mRecipes.get(i).getSize().get(j).setClick(0);
            }
        }
        for (int i = 0; i < mRecipes.size(); i++) {

            for (int j = 0; j < mRecipes.get(i).getColors().size(); j++) {
                if (j == 0)
                    mRecipes.get(i).getColors().get(j).setClick(1);
                else
                    mRecipes.get(i).getColors().get(j).setClick(0);
            }
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

                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                        finish();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
                MainActivity.selectTabPos = tab.getPosition();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                    finish();
//                }
            }
        });
    }

    private int footerIcon(int id, boolean isActive) {
        if (footerIcons == null) {
            footerIcons = new ArrayList<>();
            footerIcons.add(R.drawable.product_icon_white);
            footerIcons.add(R.drawable.delivery_icon_white);
            footerIcons.add(R.drawable.my_account_icon_white);

            footerIcons.add(R.drawable.product_icon_black);
            footerIcons.add(R.drawable.delivery_icon_black);
            footerIcons.add(R.drawable.my_account_icon_black);
        }

        return isActive ? footerIcons.get(id + 3) : footerIcons.get(id);

    }

}
