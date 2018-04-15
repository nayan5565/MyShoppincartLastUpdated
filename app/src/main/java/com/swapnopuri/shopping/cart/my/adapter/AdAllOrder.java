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
import com.swapnopuri.shopping.cart.my.model.MOrder;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdAllOrder extends RecyclerView.Adapter<AdAllOrder.MyViewHolder> {
    private ArrayList<MOrder> recipes;
    private Context context;
    private LayoutInflater inflater;
    private int userType;

    public AdAllOrder(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
        userType = Utils.getUser().getType();
    }

    public void setData(ArrayList<MOrder> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_all_order_new, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MOrder recipe = recipes.get(position);
        holder.tvName.setText(recipe.getUserName());
        holder.tvQantiy.setText("" + recipe.getTotalQuantity());
        holder.tvTotalAmount.setText(recipe.getTotalAmount() + "€");
        holder.tvDate.setText(recipe.getDateTime());

        if (userType == Global.USER_ADMIN)
            Picasso.with(context).load(recipe.getUserPic()).placeholder(R.drawable.placeholder).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int position, View view);

    private String getStatus(int statusId) {
        switch (statusId) {
            case 2:
                return "Approved";
        }
        return "Not delivered";
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvTotalAmount, tvQantiy, tvDate, tvStatus;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTotalAmount = (TextView) itemView.findViewById(R.id.tvTotalAmount);
            tvQantiy = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            img = (ImageView) itemView.findViewById(R.id.img);

            Utils.setFont("AvenirNextCondensed-Regular", tvName,tvTotalAmount, tvQantiy, tvDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
