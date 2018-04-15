package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MBanner;
import com.swapnopuri.shopping.cart.my.model.MTopWeek;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/16/2017.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    private ArrayList<MBanner> bannerArrayList;
    private Context context;
    private LayoutInflater inflater;
    MBanner mBanner;

    public BannerAdapter(Context context) {
        bannerArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MBanner> recipes) {
        this.bannerArrayList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public BannerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_top_week, parent, false);
        BannerAdapter.MyViewHolder holder = new BannerAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(BannerAdapter.MyViewHolder holder, int position) {
        mBanner = bannerArrayList.get(position);
//        holder.tvShrtDes.setText(recipe.getS());
        Utils.setFont("AvenirNext-Regular", holder.tvTitle);
        Utils.setFont("AvenirNextCondensed-Regular", holder.tvPastPrice, holder.tvDiscount, holder.tvPrice);
        if (mBanner.getImageUrl() != null && !mBanner.getImageUrl().equals("") && !mBanner.getImageUrl().equals("null")) {
            Picasso.with(context).load(mBanner.getImageUrl()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return bannerArrayList.size();
    }

//    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvPrice, tvPastPrice, tvDiscount;
        public ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvPastPrice = (TextView) itemView.findViewById(R.id.tvPastPrice);
            imgThumb = (ImageView) itemView.findViewById(R.id.img);


            Utils.setFont(tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "position" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                    if (bannerArrayList.size() <= 0) {
//                        Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        bannerArrayList.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                    }


                }
            });

        }


    }
}
