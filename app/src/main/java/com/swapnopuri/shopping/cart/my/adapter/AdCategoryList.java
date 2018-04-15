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
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdCategoryList extends RecyclerView.Adapter<AdCategoryList.MyViewHolder> {
    private ArrayList<MCategory> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdCategoryList(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(ArrayList<MCategory> recipes) {
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
        MCategory recipe = recipes.get(position);
        if (recipe != null) {
            holder.tvTitle.setText(recipe.getCategoryTitle());
//            holder.tvShortDes.setText(recipe.getCategoryDetails());
//            holder.tvNo.setText(recipe.getCategoryTotalRecipe() + "");
            if (recipe.getCategoryThumb() != null || !recipe.getCategoryThumb().equals("") || !recipe.getCategoryThumb().equals("null"))

//                if (ActivityRecipeList.showSizeColor == 1)
//                    Picasso.with(context).load(recipe.getCategoryThumb()).placeholder(R.drawable.placeholder).into(holder.img);
//                else
                    Picasso.with(context).load(Global.API_BASE_BACKEND + recipe.getCategoryThumb()).placeholder(R.drawable.placeholder).into(holder.img);

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
