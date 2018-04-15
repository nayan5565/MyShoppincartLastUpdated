package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipeList;
import com.swapnopuri.shopping.cart.my.activity.SubCategoryActivity;
import com.swapnopuri.shopping.cart.my.fragment.FragCategoryList;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/16/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private ArrayList<MCategory> categoryArrayList;
    private Context context;
    private LayoutInflater inflater;
    MCategory mCategory;

    public CategoriesAdapter(Context context) {
        categoryArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MCategory> recipes) {
        this.categoryArrayList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_cat, parent, false);
        CategoriesAdapter.MyViewHolder holder = new CategoriesAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(CategoriesAdapter.MyViewHolder holder, int position) {
        mCategory = categoryArrayList.get(position);
        holder.tvTitle.setText(mCategory.getCategoryTitle());
//        holder.tvShrtDes.setText(recipe.getS());
        Utils.setFont("AvenirNext-Regular", holder.tvTitle);

        if (mCategory.getCategoryThumb() != null && !mCategory.getCategoryThumb().equals("") && !mCategory.getCategoryThumb().equals("null")) {
            Picasso.with(context).load(mCategory.getCategoryThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

//    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgThumb = (ImageView) itemView.findViewById(R.id.img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRecipeList.category = categoryArrayList.get(getAdapterPosition());
                    Global.categoriId = categoryArrayList.get(getAdapterPosition()).getCategoryId();
                    Global.categoryTitle = categoryArrayList.get(getAdapterPosition()).getCategoryTitle();
                    Global.subCatImageURL = categoryArrayList.get(getAdapterPosition()).getCategoryThumb();
                    MyLog.e("HomeCat", " image " + Global.subCatImageURL);
                    ActivityRecipeList.showSizeColor = 1;
                    ActivityRecipeList.fragCatImg = 2;
                    FragCategoryList.image = 0;
                    SubCategoryActivity.image = 2;
                    Intent intent = new Intent(context, ActivityRecipeList.class);
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
            });

        }


    }
}
