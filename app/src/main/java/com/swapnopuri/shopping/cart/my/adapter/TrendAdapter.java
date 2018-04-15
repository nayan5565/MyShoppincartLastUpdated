package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipeList;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/16/2017.
 */

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.MyViewHolder> {
    private ArrayList<MRecipe> trendyArrayList;
    private Context context;
    private LayoutInflater inflater;
    MRecipe mTrendy;

    public TrendAdapter(Context context) {
        trendyArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MRecipe> recipes) {
        this.trendyArrayList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public TrendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_trend, parent, false);
        TrendAdapter.MyViewHolder holder = new TrendAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(TrendAdapter.MyViewHolder holder, int position) {
        mTrendy = trendyArrayList.get(position);
        holder.tvTitle.setText(mTrendy.getTitle());
//        holder.tvShrtDes.setText(recipe.getS());
        Utils.setFont("AvenirNext-Regular", holder.tvTitle);
        Utils.setFont("AvenirNextCondensed-Medium", holder.tvPastPrice, holder.tvDiscount, holder.tvPrice);
        if (mTrendy.getDiscountRate() == 0) {
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvDiscount.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.tvPastPrice.setText(mTrendy.getPrice() + " €");
        } else {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText("(" + mTrendy.getDiscountRate() + "%" + ")");
//            holder.tvDiscount.setTextColor(Color.GREEN);
            holder.tvPrice.setText(String.format("%.2f", getRealPrice()) + " €");
//        holder.tvPrice.setText(recipe.getPrice() + " €");
            holder.tvPastPrice.setText(mTrendy.getPrice() + " €");
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (mTrendy.getThumb() != null && !mTrendy.getThumb().equals("") && !mTrendy.getThumb().equals("null")) {
            Picasso.with(context).load(mTrendy.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return trendyArrayList.size();
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
                    Global.orderLayoutDissmiss =0;
                    MRecipe recipe = trendyArrayList.get(getAdapterPosition());
//                    DatabaseHelper db = new DatabaseHelper(context);
                    recipe.setIsNew(1);
//                    recipe.setCategoryId(Global.categoriId);
//                    recipe.setId(trendyArrayList.get(getAdapterPosition()).getId());
                    DBManager.getInstance().addRecipeData(recipe, false, DBManager.TABLE_TRENDY);
                    ActivityRecipe.id = getAdapterPosition();
                    ActivityRecipeList.showSizeColor = 0;
                    MyLog.e("id "," trend " +recipe.getId());
                    ActivityRecipe.recipe = recipe;
                    Global.products = trendyArrayList;
//                    if (Global.products.size() > 0) {
//                        for (int i = 0; i < Global.products.size(); i++) {
//
//                            for (int j = 0; j < Global.products.get(i).getSize().size(); j++) {
//                                if (j == 0)
//                                    Global.products.get(i).getSize().get(j).setClick(1);
//                                else
//                                    Global.products.get(i).getSize().get(j).setClick(0);
//                            }
//                        }
//                        for (int i = 0; i < Global.products.size(); i++) {
//
//                            for (int j = 0; j < Global.products.get(i).getColors().size(); j++) {
//                                if (j == 0)
//                                    Global.products.get(i).getColors().get(j).setClick(1);
//                                else
//                                    Global.products.get(i).getColors().get(j).setClick(0);
//                            }
//                        }
//                    }
                    context.startActivity(new Intent(context, ActivityRecipe.class).putExtra("productId", recipe.getId()));
                }
            });

        }


    }

    private float getRealPrice() {
        return mTrendy.getPrice() - mTrendy.getPrice() * (mTrendy.getDiscountRate() / 100f);
    }
}
