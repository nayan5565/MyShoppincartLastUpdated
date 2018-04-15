package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.BrandActivity;
import com.swapnopuri.shopping.cart.my.model.MBrand;
import com.swapnopuri.shopping.cart.my.model.MTopWeek;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/16/2017.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {
    private ArrayList<MBrand> brandArrayList;
    private Context context;
    private LayoutInflater inflater;
    MBrand mBrand;

    public BrandAdapter(Context context) {
        brandArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MBrand> recipes) {
        this.brandArrayList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public BrandAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_brand, parent, false);
        BrandAdapter.MyViewHolder holder = new BrandAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(BrandAdapter.MyViewHolder holder, int position) {
        mBrand = brandArrayList.get(position);
//        holder.tvTitle.setText(mBrand.getLink());
//        holder.tvShrtDes.setText(recipe.getS());
        GradientDrawable drawable = (GradientDrawable) holder.imgThumb.getBackground();
        MyLog.e("Image", " brand " + mBrand.getImageUrl());
        if (mBrand.getImageUrl() != null && !mBrand.getImageUrl().equals("") && !mBrand.getImageUrl().equals("null")) {
            Picasso.with(context).load(mBrand.getImageUrl()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return brandArrayList.size();
    }

//    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgThumb = (ImageView) itemView.findViewById(R.id.img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.brandLogo = brandArrayList.get(getAdapterPosition()).getImageUrl();
                    Intent intent = new Intent(context, BrandActivity.class);
                    context.startActivity(intent);
                }
            });

        }


    }
}
