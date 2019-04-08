package com.swapnopuri.shopping.cart.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdFooterTab;
import com.swapnopuri.shopping.cart.my.adapter.AdSlideDetailsImage;
import com.swapnopuri.shopping.cart.my.adapter.ColorAdapter;
import com.swapnopuri.shopping.cart.my.adapter.SizeAdapter;
import com.swapnopuri.shopping.cart.my.adapter.Sliding_Images_Adapter;
import com.swapnopuri.shopping.cart.my.fragment.FragHomePage;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.fragment.FragProductDetails;
import com.swapnopuri.shopping.cart.my.fragment.FragReviewNew;
import com.swapnopuri.shopping.cart.my.fragment.FragSuppliment;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.model.MProduct;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MVideo;
import com.swapnopuri.shopping.cart.my.tools.AdsManager;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.DownloadAsync;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jewel on 7/15/2016.
 */
public class ActivityRecipe extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ActivityRecipe.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    public static MRecipe recipe;
    public static int id, pos;
    public TabLayout footerTab, footerTabFrag;
    public static TextView tvTitle, tvTitleMat, tvTitleProcess, tvMaterial, tvProcess,
            tvFav, tvShare, tvVideo, tvVideoNo, tvSource, tvPrice, tvDisPrice, tvDiscount, tvDcTitle, tvDiscripsion;
    private ImageView imgFav, imgShare, imgVideo, imgBack, imgSearch, imgHeader, imgFragShopping;
    private LinearLayout llFav, llShare, llVideo;
    private RelativeLayout rlHeader;
    private Toolbar toolbar;
    private FrameLayout flVideo;
    private ViewPager viewPager, viewPagerImage;
    private AdFooterTab adapterFooter;
    private int productId;
    private Button btnBuy;
    private ArrayList<MProduct> productArrayList;
    MProduct mProduct;
    private boolean isAddToCart;
    public static LinearLayout lnSize, lnColor, len;
    private RecyclerView recColor, recSize;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    private ImageView imgShopping;
    public static LinearLayout lnFrag, lnRecipe;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static ArrayList<Integer> footerIcons;
    String state;
    private static ActivityRecipe instance;
    private Sliding_Images_Adapter sliding_images_adapter;
    private AdSlideDetailsImage adSlideDetailsImage;

    public static ActivityRecipe getInstance() {
        instance = new ActivityRecipe();
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        slideImage();
        setupInterstitialAd();
        setupFooter();
//        prepareDisplay2();
        prepareDisplay();
        saveToDB();
        setupNavigation();
        setupFooter2();
        MyApp.getInstance().setupAnalytics("Recipe");
    }

    private void slideImage() {
        MyLog.e("toppp", " size " + FragHomePage.topWeekArrayList.size());

        if (FragHomePage.topWeekArrayList != null && FragHomePage.topWeekArrayList.size() > 0)
            adSlideDetailsImage = new AdSlideDetailsImage(this, FragHomePage.topWeekArrayList) {
                @Override
                public void onClickItem(int position, View view) {
                    Toast.makeText(ActivityRecipe.this, "details showSizeColor " + position, Toast.LENGTH_LONG).show();
//                    Global.bannerLink = mBanner.getLink();
//                HomeActivity.getInstance().openFragment();

//                    startActivity(new Intent(ActivityRecipe.this, FragBannerLink.class));
                }
            };
        viewPagerImage.setAdapter(adSlideDetailsImage);
//        if (HomeActivity.bannerArrayList != null && HomeActivity.bannerArrayList.size() > 0)
//            sliding_images_adapter = new Sliding_Images_Adapter(this, HomeActivity.bannerArrayList) {
//                @Override
//                public void onClickItem(int position, View view) {
//                    Toast.makeText(ActivityRecipe.this, " showSizeColor", Toast.LENGTH_LONG).show();
////                    Global.bannerLink = mBanner.getLink();
////                HomeActivity.getInstance().openFragment();
//
////                    startActivity(new Intent(ActivityRecipe.this, FragBannerLink.class));
//                }
//            };
//        viewPagerImage.setAdapter(sliding_images_adapter);
    }

    private void openFragment() {
        lnRecipe.setVisibility(View.GONE);
        lnFrag.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Fragment fragment = new FragOrder();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack(null);  // this will manage backstack
        transaction.commit();
    }

    private void prepareDisplay2() {
        MyLog.e("list ", " size " + Global.products.get(id).getSize().size());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recSize.setLayoutManager(layoutManager);
        recColor.setLayoutManager(layoutManager2);
        recColor.setAdapter(colorAdapter);
        recSize.setAdapter(sizeAdapter);
//        ActivityRecipeList.products.get(id).getSize().get(pos).setClick(1);
//        ActivityRecipeList.products.get(id).getColors().get(pos).setClick(1);
        colorAdapter.setData(Global.products.get(id).getColors());
        sizeAdapter.setData(Global.products.get(id).getSize());
    }

    private void setupFooter2() {

        footerTabFrag.setSelectedTabIndicatorHeight(0);


        footerTabFrag.addTab(footerTabFrag.newTab().setText(Global.TAB_HOME_PAGE));
        footerTabFrag.addTab(footerTabFrag.newTab().setText(Global.TAB_CATEGORY));
        footerTabFrag.addTab(footerTabFrag.newTab().setText(Global.TAB_ORDER));
        footerTabFrag.addTab(footerTabFrag.newTab().setText(Global.TAB_RECIPE));
//        for (int i = 0; i < footerTab.getTabCount(); i++) {
//            //noinspection ConstantConditions
//            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//            tv.setTextColor(Color.WHITE);
//            Utils.setFont("AvenirNext-Regular", tv);
//            footerTab.getTabAt(i).setCustomView(tv);
//
//        }
        footerTabFrag.setTabTextColors(Color.WHITE, Color.WHITE);
        for (int i = 0; i < footerTabFrag.getTabCount(); i++) {
            footerTabFrag.getTabAt(i).setIcon(footerIcon(i, false));
        }
        footerTabFrag.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainActivity.selectTabPos = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:

                        startActivity(new Intent(ActivityRecipe.this, MainActivity.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(ActivityRecipe.this, MainActivity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(ActivityRecipe.this, MainActivity.class));
                        finish();
                        break;
                    case 3:
                        startActivity(new Intent(ActivityRecipe.this, MainActivity.class));
                        finish();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                MainActivity.selectTabPos = tab.getPosition();
//                startActivity(new Intent(ActivityRecipe.this, MainActivity.class));
//                finish();
////                }
            }
        });
    }

    private int footerIcon(int id, boolean isActive) {
        if (footerIcons == null) {
            footerIcons = new ArrayList<>();
            footerIcons.add(R.drawable.icon_cat);
            footerIcons.add(R.drawable.icon_cat);
            footerIcons.add(R.drawable.icon_order);
            footerIcons.add(R.drawable.icon_contact);

            footerIcons.add(R.drawable.icon_cat_active);
            footerIcons.add(R.drawable.icon_cat_active);
            footerIcons.add(R.drawable.icon_order_active);
            footerIcons.add(R.drawable.icon_contact_active);
        }

        return isActive ? footerIcons.get(id + 4) : footerIcons.get(id);

    }

    private void setupFooter() {
        adapterFooter = new AdFooterTab(getSupportFragmentManager());
        adapterFooter.addFragment(FragProductDetails.newInstance(recipe), Global.TAB_DETAIL);
        adapterFooter.addFragment(FragSuppliment.newInstance(), Global.TAB_SUPPLIMENT);
        adapterFooter.addFragment(FragReviewNew.newInstance(productId), Global.TAB_REVIEW);
        viewPager.setAdapter(adapterFooter);
        footerTab.setupWithViewPager(viewPager);
        footerTab.setSelectedTabIndicatorHeight(5);
        footerTab.setSelectedTabIndicatorColor(Color.parseColor("#4D4D4D"));
        footerTab.setTabTextColors(Color.WHITE, Color.parseColor("#4D4D4D"));
//        for (int i = 0; i < footerTab.getTabCount(); i++) {
//            //noinspection ConstantConditions
//            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//            tv.setTextColor(Color.WHITE);
//            Utils.setFont("AvenirNext-Regular", tv);
//            footerTab.getTabAt(i).setCustomView(tv);
//
//        }


    }


    private void saveToDB() {
        if (recipe != null && recipe.getView() != 2) {
            recipe.setView(2);
            DBManager.getInstance().addData(DBManager.TABLE_RECEIPE, recipe, "Id");
        }
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

    private void init() {
        setContentView(R.layout.lay_recipe_new);
        lnFrag = findViewById(R.id.fragment_container);
        lnRecipe = findViewById(R.id.lnSingleList);
        lnRecipe.setVisibility(View.VISIBLE);
        lnFrag.setVisibility(View.GONE);
        imgFragShopping = findViewById(R.id.imgOrderShopping);
        imgShopping = findViewById(R.id.imgShopping);
        imgShopping.setOnClickListener(this);
        imgFragShopping.setOnClickListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont("AvenirNext-Regular", mTitle);
        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(this);
        lnColor = findViewById(R.id.lnColor);
        len = findViewById(R.id.len);
        lnSize = findViewById(R.id.lnSize);
        viewPager = findViewById(R.id.viewpager);
        viewPagerImage = findViewById(R.id.viewpagerImage);
        footerTab = findViewById(R.id.footerTab_2);
        footerTabFrag = findViewById(R.id.footerTab);


        tvDiscripsion = findViewById(R.id.tvDiscripsion);


        imgFav = findViewById(R.id.imgFav);
        imgFav.setOnClickListener(this);
//
        imgBack.setOnClickListener(this);
        if (getIntent() != null && getIntent().hasExtra("productId")) {
            productId = getIntent().getIntExtra("productId", 0);
        }
        if (FragOrder.currentOrderIds != null)
            isAddToCart = FragOrder.currentOrderIds.contains(productId);

    }

    private void setupInterstitialAd() {

        int recipeViewCount = Integer.parseInt(Utils.getPref(Global.REF_INTERSTITIAL, "0"));
        recipeViewCount++;
        Utils.savePref(Global.REF_INTERSTITIAL, recipeViewCount + "");
        if (recipeViewCount >= Global.LIMIT_RECIPE_INTERSTITIAL) {
            Utils.savePref(Global.REF_INTERSTITIAL, "0");
            AdsManager.getInstance(this).showInterstisial();

        }

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
                        Utils.showDialog(ActivityRecipe.this, "App Version", "Version 1.0.2");
                        break;
                    case R.id.mnuFav:
                        footerTabFrag.getTabAt(2).select();
                        break;
                    case R.id.mnuHome:
                        footerTabFrag.getTabAt(0).select();
//                        startActivity(new Intent(ActivityRecipe.this, HomeActivity.class));
//                        finish();
                        break;
                    case R.id.mnuCategory:
                        footerTabFrag.getTabAt(1).select();
                        break;
                    case R.id.mnuAsk:
                        footerTabFrag.getTabAt(3).select();

                        break;
                    case R.id.mnuHalnagad:
                        //halnagad();
                        break;
                    case R.id.mnuProfile:
                        Utils.savePref("profile", Global.STATE_PROFILE);
                        if (state.equals(Global.STATE_PROFILE2)) {
                            ProfileActivity.start(ActivityRecipe.this);
                        } else {
                            RegistrationActivity.start(ActivityRecipe.this);
                        }

                        break;
                    case R.id.mnuFavorite:
                        FavoriteActivity.start(ActivityRecipe.this);
                        break;
                    case R.id.mnuLogout:
                        Utils.clearPref();
                        LoginActivity.start(ActivityRecipe.this);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    public static float getRealPrice() {
        return Global.products.get(id).getSize().get(pos).getPrice() - Global.products.get(id).getSize().get(pos).getPrice() * (Global.products.get(id).getSize().get(pos).getDiscountRate() / 100f);
    }

    private void prepareDisplay() {


        if (recipe != null) {
            tvDiscripsion.setText(recipe.getTitle());
            Utils.setFont("AvenirNext-Regular", tvDiscripsion);
            imgFav.setImageResource(recipe.getFav() == 1 ? R.drawable.favorite_active : R.drawable.favorite);
        }
    }

    private int getVideoNo() {
        return DBManager.getInstance().getData(DBManager.TABLE_VIDEO, new MVideo()).size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFav:
                if (recipe.getFav() == 0) {
                    recipe.setFav(1);
                    Utils.showDialog(ActivityRecipe.this, Global.MSG_THANKS, Global.MSG_LIKE_RECIPE);
                } else
                    recipe.setFav(0);
                ((ImageView) findViewById(R.id.imgFav)).setImageResource(recipe.getFav() == 1 ? R.drawable.favorite_active : R.drawable.favorite);
//                DBManager.getInstance().addData(DBManager.TABLE_RECEIPE, recipe, "Id");
                DBManager.getInstance().addRecipeData(recipe, true, DBManager.TABLE_RECEIPE);
                break;
            case R.id.btnBuy:
                addToCart();
                break;
            case R.id.img:
                startActivity(new Intent(ActivityRecipe.this, SlidingImageActivity.class));
                finish();
                break;

            case R.id.imgBack:
                startActivity(new Intent(ActivityRecipe.this, MainActivity.class));
                finish();
                break;
            case R.id.imgShopping:
//                Global.orderLayoutDissmiss = 1;
                openFragment();
                break;
            case R.id.imgOrderShopping:
                footerTabFrag.getTabAt(2).select();
                break;
            case R.id.imgSearch:
                startActivity(new Intent(ActivityRecipe.this, SearchActivity.class));
                finish();
                break;
        }
    }

    private void addToCart() {
        MOrderedItem orderedItem = new MOrderedItem();
        orderedItem.setId(recipe.getId());
        orderedItem.setAmount(recipe.getPrice());
        orderedItem.setQuantity(1);
        orderedItem.setThumb(recipe.getThumb());
        orderedItem.setTitle(recipe.getTitle());
        orderedItem.setDate(Utils.getDateTime());
        orderedItem.setUserId(Utils.getUser().getId());


        FragOrder.addCurrentItems(orderedItem);
        isAddToCart = !isAddToCart;
        btnBuy.setText(isAddToCart ? "REMOVE TO CART" : "ADD TO CART");
        btnBuy.setTextColor(isAddToCart ? Color.BLACK : Color.WHITE);
        btnBuy.setBackgroundColor(isAddToCart ? Color.parseColor("#F8E71C") : Color.parseColor("#222222"));
        Utils.showToast(isAddToCart ? "Successfully added to cart" : "Successfully removed from cart");
    }

    private boolean checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                playVideo();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void playVideo() {
        // if no video is available
        if (recipe.getVideo() == null || recipe.getVideo().equals("")) {
            Utils.showDialog(this, Global.MSG_TITLE_NO_VIDEO, Global.MSG_NO_VIDEO);
            return;
        }

        //getting filename only
        String fileName = recipe.getVideo().substring(recipe.getVideo().lastIndexOf("/") + 1);

        if (recipe.getVideo().startsWith("https")) {
            if (!Utils.isInternetOn()) {
                Utils.showDialog(ActivityRecipe.this, Global.MSG_SORRY, Global.MSG_NO_INTERNET);
                return;
            }
            String videoId = recipe.getVideo().substring(recipe.getVideo().lastIndexOf(".be/") + 4);
            playYoutubeVideo(videoId);


        } else {
            File fDir = null;
            fDir = new File(Utils.getPath(fileName));
            if (!fDir.exists()) {
                MyLog.e("FILE", "file not extists:" + fDir);
                if (!Utils.isInternetOn()) {
                    Utils.showDialog(ActivityRecipe.this, Global.MSG_SORRY, Global.MSG_NO_INTERNET);
                    return;
                }
                if (checkPermission())
                    new DownloadAsync(this, fDir.getAbsolutePath(), recipe.getTitle()).execute(Global.API_BASE_BACKEND + recipe.getVideo());
                else
                    requestStoragePermission();
            } else {

                ActivityVideoPlayer.start(this, recipe.getTitle(), fDir.getAbsolutePath(), recipe.getId());
            }
        }
    }

    private void playYoutubeVideo(String videoId) {
        Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(this, videoId, true, false);
        startActivity(intent);
    }


    private void fbShare() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("image/jpeg");
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        sharingIntent.setType("text/plain");
//        sharingIntent.setType("message/rfc822");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Global.APK_LINK);
        startActivity(Intent.createChooser(sharingIntent, "BR Share"));
    }

    public void shareFB() {

        String text = "Look at my awesome picture";
        Uri pictureUri = Uri.parse("file://my_picture");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share images..."));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lnRecipe.setVisibility(View.VISIBLE);
        lnFrag.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyLog.e("diss", " " + Global.orderLayoutDissmiss);
        lnRecipe.setVisibility(View.VISIBLE);
        lnFrag.setVisibility(View.GONE);

    }

    public void shareOnFB() {
        boolean found = false;
        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/jpeg");
        share.setType("text/plain");
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = MyApp.getInstance().getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains("face") ||
                        info.activityInfo.name.toLowerCase().contains("face")) {
                    share.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    share.putExtra(Intent.EXTRA_TEXT, Global.APK_LINK);
//                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(myPath)) ); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return;


            startActivity(Intent.createChooser(share, "Select"));
        }
    }
}
