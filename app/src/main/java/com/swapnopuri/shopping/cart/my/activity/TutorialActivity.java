package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdFooterTab;
import com.swapnopuri.shopping.cart.my.fragment.FragTutorial;
import com.swapnopuri.shopping.cart.my.fragment.FragmentOnbording;
import com.swapnopuri.shopping.cart.my.fragment.FragmentOnbording2;
import com.swapnopuri.shopping.cart.my.fragment.FragmentOnbording3;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private AdFooterTab adapterFooter;
    private Button btnStart;
    private TextView tvSkip;


    private static ArrayList<Integer> images;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TutorialActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_tutorial);
        init();
        setupFooter();
        MyApp.getInstance().setupAnalytics("Tutorial");
//        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    private void init() {
        tvSkip = (TextView) findViewById(R.id.tvSkip);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        tvSkip.setOnClickListener(this);

//        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
//        circleIndicator.setViewPager(viewPager);

//        addImages();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStart) {
            Utils.savePref(Global.STATE, Global.STATE_REGIS);
            startActivity(new Intent(TutorialActivity.this, LogSignActivity.class));
            finish();
        } else if (v.getId() == R.id.tvSkip) {
            Utils.savePref(Global.STATE, Global.STATE_SPLASH);
            startActivity(new Intent(TutorialActivity.this, SplashActivity.class));
            finish();
        }

    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragTutorial.newInstance(position);
        }

        @Override
        public int getCount() {
            addImages();
            return images.size();
        }
    }

    public static int getImage(int pos) {
        addImages();
        return images.get(pos);
    }

    private void setupFooter() {
        adapterFooter = new AdFooterTab(getSupportFragmentManager());
        adapterFooter.addFragment(FragmentOnbording.newInstance(), "Onbording1");
        adapterFooter.addFragment(FragmentOnbording2.newInstance(), "Onbording2");
        adapterFooter.addFragment(FragmentOnbording3.newInstance(), "Onbording3");
        viewPager.setAdapter(adapterFooter);
        circleIndicator.setViewPager(viewPager);

    }

    private static void addImages() {
        if (images == null) {
            images = new ArrayList<>();
            images.add(R.drawable.tut_1);
            images.add(R.drawable.tut_2);
            images.add(R.drawable.tut_3);
            images.add(R.drawable.tut_4);
            images.add(R.drawable.tut_5);
        }
    }
}
