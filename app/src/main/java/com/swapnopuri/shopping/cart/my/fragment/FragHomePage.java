package com.swapnopuri.shopping.cart.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.BrandAdapter;
import com.swapnopuri.shopping.cart.my.adapter.CategoriesAdapter;
import com.swapnopuri.shopping.cart.my.adapter.FlashAdapter;
import com.swapnopuri.shopping.cart.my.adapter.Sliding_Images_Adapter;
import com.swapnopuri.shopping.cart.my.adapter.TopWeekAdapter;
import com.swapnopuri.shopping.cart.my.adapter.TrendAdapter;
import com.swapnopuri.shopping.cart.my.model.MBanner;
import com.swapnopuri.shopping.cart.my.model.MBrand;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MHomeList;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MTimeTracker;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.EndlessScrollListener;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Dev on 11/28/2017.
 */

public class FragHomePage extends Fragment {
    private View view;
    private CategoriesAdapter categoriesAdapter;
    private TopWeekAdapter topWeekAdapter;
    private BrandAdapter brandAdapter;
    private FlashAdapter flashAdapter;
    private TrendAdapter trendAdapter;
    private Sliding_Images_Adapter sliding_images_adapter;
    public static int selectTabPos;
    private static ArrayList<Integer> footerIcons;
    public static DrawerLayout drawerLayout;
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
    private RecyclerView recBrand, recTrend, recCat, recFlash, recTopWeek;
    private TextView tvTop, tvTrendy, tvFlash, tvTime;
    private Button btnViewAll;
    private ImageView imgShoppingCart, imgSearch;
    private LinearLayout lnFrgBannerLink;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;

    public static FragHomePage newInstance() {
//
        return new FragHomePage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        init();
        getLoaclData();
        prepareDisplay();

        MyApp.getInstance().setupAnalytics("Home Screen");

        timeTracker = new MTimeTracker();
        timeTracker.setLogin(Utils.getDateTime());
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        return view;
    }

    private void init() {
        lnFrgBannerLink = (LinearLayout)view.findViewById(R.id.frgBanner);
        lnFrgBannerLink.setVisibility(View.GONE);
        tvTop = (TextView) view.findViewById(R.id.tvTop);
        tvTrendy = (TextView) view.findViewById(R.id.tvTrendy);
        tvFlash = (TextView) view.findViewById(R.id.tvFlash);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        btnViewAll = (Button) view.findViewById(R.id.btnViewAll);
        imgShoppingCart = (ImageView) view.findViewById(R.id.imgShopping);
        imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        gson = new Gson();
        mBanner = new MBanner();
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        Utils.setFont("AvenirNext-Regular");
        Utils.setFont("AvenirNextCondensed-Medium", tvTop, tvTrendy, tvFlash, tvTime, btnViewAll);

        topWeekAdapter = new TopWeekAdapter(getContext());
        brandAdapter = new BrandAdapter(getContext());
        categoriesAdapter = new CategoriesAdapter(getContext());
        flashAdapter = new FlashAdapter(getContext());
        trendAdapter = new TrendAdapter(getContext());


        recBrand = (RecyclerView) view.findViewById(R.id.recBrand);
        recTrend = (RecyclerView) view.findViewById(R.id.recNewTrend);
        recCat = (RecyclerView) view.findViewById(R.id.recCat);
        recFlash = (RecyclerView) view.findViewById(R.id.recFlash);
        recTopWeek = (RecyclerView) view.findViewById(R.id.recTopWeek);


    }


    private void slideBannerImage() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerBanner);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        if (bannerArrayList != null && bannerArrayList.size() > 0)
            sliding_images_adapter = new Sliding_Images_Adapter(getContext(), bannerArrayList) {
                @Override
                public void onClickItem(int position, View view) {
                    Toast.makeText(getContext(), " showSizeColor", Toast.LENGTH_LONG).show();
                    Global.bannerLink = bannerArrayList.get(position).getLink();
//                HomeActivity.getInstance().openFragment();
                    startActivity(new Intent(getContext(), FragBannerLink.class));
                }
            };
        viewPager.setAdapter(sliding_images_adapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void prepareDisplay() {

        slideBannerImage();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager7
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recBrand.setLayoutManager(layoutManager);
        recTrend.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recCat.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recFlash.setLayoutManager(new GridLayoutManager(getContext(), 4));
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

}
