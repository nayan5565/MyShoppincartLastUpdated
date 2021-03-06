package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdCurrentOrder extends RecyclerView.Adapter<AdCurrentOrder.MyViewHolder> {
    private ArrayList<MOrderedItem> recipes;
    private Context context;
    private LayoutInflater inflater;
    private int userType;
    private int totalPrice, price;
    int prevQuantity;

    public AdCurrentOrder(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
        userType = Utils.getUser().getType();
    }

    public void setData(ArrayList<MOrderedItem> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_current, parent, false);
//        TextView tv_nz = new TextView(context.getApplicationContext());
//
//        LinearLayout zv = (LinearLayout) view.findViewById(R.id.lnO);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MOrderedItem recipe = recipes.get(position);
//        prevQuantity = recipes.get(position).getQuantity();
        FragOrder.tvDate.setText(recipe.getDate());
//        if (!recipe.getDate().equals(Utils.getDateTime())) {
////            holder.tv_nz.setText(recipe.getDate());
//            holder.tvDate.setVisibility(View.VISIBLE);
//            holder.tvDate.setText(" row " + recipe.getDate());
//        } else {
//            holder.tvDate.setVisibility(View.GONE);
//        }
        holder.tvName.setText(recipe.getTitle());
        holder.tvQantiy.setText("" + recipe.getQuantity());
        totalPrice = (int) (recipe.getAmount() * recipe.getQuantity());
        price = (int) (recipe.getAmount());
        String str = String.format("%,d", totalPrice);
        String str2 = String.format("%,d", price);
        holder.tvTotalAmount.setText(String.format(str + "€"));
        holder.tvPrice.setText(String.format(str2 + "€"));
//        Utils.setFont("AvenirNextCondensed-Bold", holder.tvTotalAmount, holder.tvPrice, holder.tvQantiy);
        Utils.setFont("AvenirNextCondensed-Regular", holder.tvName, holder.tvTotalAmount, holder.tvPrice, holder.tvQantiy);
        if (recipe.getThumb() != null && !recipe.getThumb().equals("") && !recipe.equals("null")) {
            Picasso.with(context).load(userType == Global.USER_ADMIN ? recipe.getThumb() : recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.img);
        } else holder.img.setImageResource(R.drawable.placeholder);


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPrice, tvTotalAmount, tvQantiy, tvDate;
        public ImageView img, tvPlus, tvMinus;
        TextView tv_nz;
        LinearLayout zv;

        public MyViewHolder(View itemView) {
            super(itemView);
//            zv = (LinearLayout) itemView.findViewById(R.id.lnO);
//            tv_nz = new TextView(context.getApplicationContext());
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvTotalAmount = (TextView) itemView.findViewById(R.id.tvTotalAmount);
            tvQantiy = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvPlus = (ImageView) itemView.findViewById(R.id.tvPlus);
            tvMinus = (ImageView) itemView.findViewById(R.id.tvMinus);
            img = (ImageView) itemView.findViewById(R.id.img);
//            MOrderedItem recipe = recipes.get(getAdapterPosition());
            if (Utils.getUser().getType() == Global.USER_ADMIN || FragOrder.viewDismiss == 1) {
                tvPlus.setVisibility(View.GONE);
                tvMinus.setVisibility(View.GONE);
            } else {
//                FragOrder.tvDate.setText(recipe.getDate());
                tvPlus.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.VISIBLE);
            }
            Utils.setFont(tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    MOrderedItem recipe = recipes.get(getAdapterPosition());
//                    context.startActivity(new Intent(context, ActivityRecipe.class).putExtra("productId", recipe.getId()));
                    onClickItem(getAdapterPosition(), v);
                }
            });
            tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    if (prevQuantity > 0) {
//                        recipes.get(getAdapterPosition()).setQuantity(prevQuantity - 1);
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), getItemCount());
//
//                    } else {
//                        recipes.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), recipes.size());
//                        FragOrder.addCurrentItems(recipes.get(getAdapterPosition()));
//                    }
//                    FragOrder.newInstance().prepareCurrentOrder();
                    onClickItem(getAdapterPosition(), v);

                }
            });
            tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    recipes.get(getAdapterPosition()).setQuantity(prevQuantity + 1);
//                    // stay last pos
//                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
////                        currentItems.get(position).setAmount(currentItems.get(position).getQuantity() * 100);
//                    FragOrder.newInstance().prepareCurrentOrder();
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
