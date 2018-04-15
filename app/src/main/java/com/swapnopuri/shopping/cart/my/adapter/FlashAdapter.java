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

public class FlashAdapter extends RecyclerView.Adapter<FlashAdapter.MyViewHolder> {
    private ArrayList<MRecipe> flashArrayList;
    private Context context;
    private LayoutInflater inflater;
    MRecipe mFlash;

    public FlashAdapter(Context context) {
        flashArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MRecipe> recipes) {
        this.flashArrayList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public FlashAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_flash, parent, false);
        FlashAdapter.MyViewHolder holder = new FlashAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(FlashAdapter.MyViewHolder holder, int position) {
//
        mFlash = flashArrayList.get(position);
        holder.tvTitle.setText(mFlash.getTitle());
//        holder.tvShrtDes.setText(recipe.getS());
        Utils.setFont("AvenirNext-Regular", holder.tvTitle);
        Utils.setFont("AvenirNextCondensed-Medium", holder.tvPastPrice, holder.tvDiscount, holder.tvPrice);
        if (mFlash.getDiscountRate() == 0) {
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvDiscount.setVisibility(View.INVISIBLE);
            holder.tvPrice.setVisibility(View.INVISIBLE);
            holder.tvPastPrice.setText(mFlash.getPrice() + " €");
        } else {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText("(" + mFlash.getDiscountRate() + "%" + ")");
//            holder.tvDiscount.setTextColor(Color.GREEN);
            holder.tvPrice.setText(String.format("%.2f", getRealPrice()) + " €");
//        holder.tvPrice.setText(recipe.getPrice() + " €");
            holder.tvPastPrice.setText(mFlash.getPrice() + " €");
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (mFlash.getThumb() != null && !mFlash.getThumb().equals("") && !mFlash.getThumb().equals("null")) {
            Picasso.with(context).load(mFlash.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return flashArrayList.size();
    }

//    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvPrice, tvPastPrice, tvDiscount;
        public ImageView imgThumb;
//        int scaledSize = context.getResources().getDimensionPixelSize(R.dimen.sp14);
        public MyViewHolder(View itemView) {
            super(itemView);

            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvPastPrice = (TextView) itemView.findViewById(R.id.tvPastPrice);
            imgThumb = (ImageView) itemView.findViewById(R.id.img);
//            tvPastPrice.setTextSize(scaledSize);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.orderLayoutDissmiss =0;
                    MRecipe recipe = flashArrayList.get(getAdapterPosition());
//                    DatabaseHelper db = new DatabaseHelper(context);
                    recipe.setIsNew(1);
//                    recipe.setCategoryId(Global.categoriId);
//                    recipe.setId(flashArrayList.get(getAdapterPosition()).getId());
                    DBManager.getInstance().addRecipeData(recipe, false, DBManager.TABLE_FLASH);
                    ActivityRecipe.id = getAdapterPosition();
                    ActivityRecipeList.showSizeColor = 0;

                    ActivityRecipe.recipe = recipe;
                    Global.products = flashArrayList;
                    MyLog.e("id "," flash " +recipe.getId());
                    context.startActivity(new Intent(context, ActivityRecipe.class).putExtra("productId", recipe.getId()));
                }
            });

        }


    }

    private float getRealPrice() {
        return mFlash.getPrice() - mFlash.getPrice() * (mFlash.getDiscountRate() / 100f);
    }

//    DisplayMetrics displayMetrics = new DisplayMetrics();
//    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//    switch(displayMetrics.densityDpi){
//        case DisplayMetrics.DENSITY_LOW:
//            //set text-size for low-density devices.
//            break;
//        case DisplayMetrics.DENSITY_MEDIUM:
//            //set text-size for medium-density devices.
//            break;
//        case DisplayMetrics.DENSITY_HIGH:
//            //set text-size for high-density devices.
//            break;
//    }
}
