package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MBanner;
import com.swapnopuri.shopping.cart.my.model.MRecipe;

import java.util.ArrayList;

/**
 * Created by Dev on 11/11/2017.
 */

public abstract class AdSlideDetailsImage extends PagerAdapter {
    //    private String[] IMAGES;
    private LayoutInflater inflater;
    private Context context;
    ArrayList<MRecipe> bannerArrayList;
    MRecipe mBanner = new MRecipe();

    public AdSlideDetailsImage(Context context, ArrayList<MRecipe> bannerArrayList) {
        this.context = context;
        this.bannerArrayList = bannerArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bannerArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        mBanner = bannerArrayList.get(position);
        // Declare Variables

        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_photo_view_adapter, container,
                false);
        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.image_full);
        // Capture position and set to the ImageView
//        Picasso.with(context).load("xxx" + mBanner.getImageUrl()).into(imgflag);
        if (mBanner.getThumb() != null && !mBanner.getThumb().equals("") && !mBanner.getThumb().equals("null")) {
            Picasso.with(context).load(mBanner.getThumb()).placeholder(R.drawable.placeholder).into(imgflag);
        } else {
            imgflag.setImageResource(R.drawable.placeholder);
        }

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(position, v);
//
//                Toast.makeText(context, " showSizeColor", Toast.LENGTH_LONG).show();
//                Global.bannerLink = mBanner.getLink();
////                HomeActivity.getInstance().openFragment();
//                context.startActivity(new Intent(context, FragBannerLink.class));
            }
        });


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    public abstract void onClickItem(int position, View view);
}
