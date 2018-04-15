package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipeList;
import com.swapnopuri.shopping.cart.my.model.MsubCategory;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Dev on 10/26/2017.
 */

public abstract class AdSubCat extends RecyclerView.Adapter<AdSubCat.MyViewHolder> {
    private ArrayList<MsubCategory> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdSubCat(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(ArrayList<MsubCategory> recipes) {
        this.recipes = recipes;
        MyLog.e("Click", " is " + ActivityRecipeList.showSizeColor);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_category_new, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MsubCategory recipe = recipes.get(position);
        if (recipe != null) {
            holder.tvTitle.setText(recipe.getCategoryTitle());
//            holder.tvShortDes.setText(recipe.getCategoryDetails());
//            holder.tvNo.setText(recipe.getCategoryTotalRecipe() + "");
            if (recipe.getCategoryThumb() != null || !recipe.getCategoryThumb().equals("") || !recipe.getCategoryThumb().equals("null"))
                Picasso.with(context).load(recipe.getCategoryThumb()).placeholder(R.drawable.placeholder).into(holder.img);

        }

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int itemPositon, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvShortDes, tvNo;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
//            tvShortDes= (TextView) itemView.findViewById(R.id.tvShortDes);
//            tvNo= (TextView) itemView.findViewById(R.id.tvNo);
            img = (ImageView) itemView.findViewById(R.id.img);

            Utils.setFont("AvenirNext-Medium", tvTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
