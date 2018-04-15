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
import com.swapnopuri.shopping.cart.my.activity.MainActivity;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/16/2017.
 */

public class TopWeekAdapter extends RecyclerView.Adapter<TopWeekAdapter.MyViewHolder> {
    private ArrayList<MRecipe> topWeekArrayList;
    private Context context;
    private LayoutInflater inflater;
    MRecipe mTopWeek;

    public TopWeekAdapter(Context context) {
        topWeekArrayList = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MRecipe> recipes) {
        this.topWeekArrayList = recipes;

        notifyDataSetChanged();
    }

    @Override
    public TopWeekAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_top_week_new, parent, false);
        TopWeekAdapter.MyViewHolder holder = new TopWeekAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(TopWeekAdapter.MyViewHolder holder, int position) {
        mTopWeek = topWeekArrayList.get(position);
        holder.tvTitle.setText(mTopWeek.getTitle());
//        holder.tvShrtDes.setText(recipe.getS());
        Utils.setFont("AvenirNext-Regular", holder.tvTitle);
        Utils.setFont("AvenirNextCondensed-Medium", holder.tvPastPrice, holder.tvDiscount, holder.tvPrice);
        if (mTopWeek.getDiscountRate() == 0) {
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvDiscount.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.tvPastPrice.setText(mTopWeek.getPrice() + " €");
        } else {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText("(" + mTopWeek.getDiscountRate() + "%" + ")");
//            holder.tvDiscount.setTextColor(Color.GREEN);
            holder.tvPrice.setText(String.format("%.2f", getRealPrice()) + " €");
//        holder.tvPrice.setText(recipe.getPrice() + " €");
            holder.tvPastPrice.setText(mTopWeek.getPrice() + " €");
            holder.tvPastPrice.setPaintFlags(holder.tvPastPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (mTopWeek.getThumb() != null && !mTopWeek.getThumb().equals("") && !mTopWeek.getThumb().equals("null")) {
            Picasso.with(context).load(mTopWeek.getThumb()).placeholder(R.drawable.placeholder).into(holder.imgThumb);
        }else {
            holder.imgThumb.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return topWeekArrayList.size();
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
                    MRecipe recipe = topWeekArrayList.get(getAdapterPosition());
//                    DatabaseHelper db = new DatabaseHelper(context);
                    recipe.setIsNew(1);

//                    recipe.setCategoryId(Global.categoriId);
//                    recipe.setId(topWeekArrayList.get(getAdapterPosition()).getId());
                    DBManager.getInstance().addRecipeData(recipe, false, DBManager.TABLE_TOP_WEEK);
                    ActivityRecipe.id = getAdapterPosition();
                    ActivityRecipeList.showSizeColor = 0;
                    MainActivity.selectTabPos=0;
                    ActivityRecipe.recipe = recipe;
                    Global.products =topWeekArrayList;
                    MyLog.e("id "," top " +recipe.getId());
                    context.startActivity(new Intent(context, ActivityRecipe.class).putExtra("productId", recipe.getId()));
                }
            });

        }


    }

    private float getRealPrice() {
        return mTopWeek.getPrice() - mTopWeek.getPrice() * (mTopWeek.getDiscountRate() / 100f);
    }
}
