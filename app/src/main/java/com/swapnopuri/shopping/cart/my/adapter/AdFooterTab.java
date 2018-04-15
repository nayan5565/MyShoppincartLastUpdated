package com.swapnopuri.shopping.cart.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/12/2016.
 */
public class AdFooterTab extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<String>();

    public AdFooterTab(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }


}
