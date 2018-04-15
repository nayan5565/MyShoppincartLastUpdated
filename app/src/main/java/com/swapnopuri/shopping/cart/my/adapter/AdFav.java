package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdFav extends RecyclerView.Adapter<AdFav.MyViewHolder> {
    private ArrayList<MRecipe> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdFav(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MRecipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_fav, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MRecipe recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        Utils.setFont("AvenirNextCondensed-Regular", holder.tvTitle);
        if (recipe.getThumb() != null && !recipe.getThumb().equals("") && !recipe.getThumb().equals("null")) {
            Picasso.with(context).load(recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }
//            Picasso.with(context).load(Global.API_BASE_BACKEND +recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgThumb = (ImageView) itemView.findViewById(R.id.img);

            Utils.setFont(tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }

    public abstract void onClickItem(int position, View view);
}
