package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipeList;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdRecipe extends RecyclerView.Adapter<AdRecipe.MyViewHolder> {
    private ArrayList<MRecipe> recipes;
    private Context context;
    private LayoutInflater inflater;
    MRecipe recipe;

    public AdRecipe(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MRecipe> recipes) {
        this.recipes = recipes;
        if (ActivityRecipeList.showSizeColor == 1)
            for (int i = 0; i < recipes.size(); i++) {
                recipes.get(i).setSelectedColor("");
                recipes.get(i).setSelectedSize("");
            }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_recipe_new, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
//        holder.tvShrtDes.setText(recipe.getS());
        Utils.setFont("AvenirNextCondensed-Bold", holder.tvTitle);
        Utils.setFont("AvenirNextCondensed-Regular", holder.tvPastPrice, holder.tvDiscount, holder.tvPrice);
        if (recipe.getDiscountRate() == 0) {
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvDiscount.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.tvPastPrice.setText(recipe.getPrice() + " €");
        } else {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText("(" + recipe.getDiscountRate() + "%" + ")");
//            holder.tvDiscount.setTextColor(Color.GREEN);
            holder.tvPrice.setText(String.format("%.2f", getRealPrice()) + " €");
//        holder.tvPrice.setText(recipe.getPrice() + " €");
            holder.tvPastPrice.setText(recipe.getPrice() + " €");
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        MyLog.e("selected", " color " + recipe.getSelectedColor());
        MyLog.e("selected", " size " + recipe.getSelectedSize());
        GradientDrawable drawable = (GradientDrawable) holder.imgColor.getBackground();
        GradientDrawable drawable2 = (GradientDrawable) holder.tvSize.getBackground();
        if (recipe.getSelectedSize() == null || recipe.getSelectedSize().equals("") || recipe.getSelectedSize().equals("null")) {
            holder.tvSize.setVisibility(View.GONE);
            drawable2.setColor(Color.TRANSPARENT);
            holder.tvSize.setTextColor(Color.TRANSPARENT);

        } else {
            drawable2.setColor(Color.parseColor("#222222"));
            holder.tvSize.setVisibility(View.VISIBLE);
            holder.tvSize.setTextColor(Color.WHITE);
            holder.tvSize.setText(recipe.getSelectedSize());
        }
//        holder.imgHalnagad.setVisibility(recipe.getView() == 2 ? View.INVISIBLE : View.VISIBLE);
        if (recipe.getView() == 1)
            holder.imgHalnagad.setImageResource(R.drawable.icon_halnagad);
        holder.imgCart.setImageResource(FragOrder.currentOrderIds.contains(recipe.getId()) ? R.drawable.icon_buy_after : R.drawable.icon_buy_before);

//        Picasso.with(context).load(Global.API_BASE_BACKEND + recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        if (recipe.getThumb() != null && !recipe.getThumb().equals("") && !recipe.getThumb().equals("null")) {
            Picasso.with(context).load(recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }
//
        if (recipe.getIsNew() == 1) {
            holder.tvNew.setVisibility(View.GONE);
        } else {
            holder.tvNew.setVisibility(View.VISIBLE);
        }
        if (recipe.getSelectedColor() == null || recipe.getSelectedColor().equals("") || recipe.getSelectedColor().equals("null")) {
            holder.imgColor.setVisibility(View.GONE);
        } else {
            holder.imgColor.setVisibility(View.VISIBLE);
            drawable.setColor(Color.parseColor(recipe.getSelectedColor()));

//            holder.imgColor.setBackgroundColor(recipe.getColor());
        }

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvPrice, tvNew, tvSize, tvPastPrice, tvDiscount;
        public ImageView imgThumb, imgHalnagad, imgCart, imgColor;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSize = (TextView) itemView.findViewById(R.id.tvSize);
            tvNew = (TextView) itemView.findViewById(R.id.tvNew);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvPastPrice = (TextView) itemView.findViewById(R.id.tvPastPrice);
            imgThumb = (ImageView) itemView.findViewById(R.id.img);
            imgHalnagad = (ImageView) itemView.findViewById(R.id.imgHalanagad);
            imgCart = (ImageView) itemView.findViewById(R.id.imgCart);
            imgColor = (ImageView) itemView.findViewById(R.id.imgColor);


            Utils.setFont(tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });

            if (Utils.getUser().getType() != Global.USER_ADMIN)
                imgCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickItem(getAdapterPosition(), v);
                    }
                });
        }


    }

    private float getRealPrice() {
        return recipe.getPrice() - recipe.getPrice() * (recipe.getDiscountRate() / 100f);
    }
}
