package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipeList;
import com.swapnopuri.shopping.cart.my.fragment.FragProductDetails;
import com.swapnopuri.shopping.cart.my.model.MSize;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/2/2017.
 */
public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MSize> sizeArrayList;
    private MSize mSize;
    private int click = 0, selectPos;


    public SizeAdapter(Context context) {
        this.context = context;
        sizeArrayList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MSize> mProducts) {
        this.sizeArrayList = mProducts;

//        if (showSizeColor == 0) {
//            for (int i = 0; i < sizeArrayList.size(); i++) {
//                sizeArrayList.get(i).setClick(0);
//            }
//            sizeArrayList.get(0).setClick(1);
//        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_size, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        mSize = sizeArrayList.get(position);
        MyLog.e("size", " list " + sizeArrayList.size());


        GradientDrawable drawable = (GradientDrawable) holder.txtSize.getBackground();
        if (mSize.getNotation() == null || mSize.getNotation().equals("") || mSize.getNotation().equals("null")) {
            holder.txtSize.setVisibility(View.GONE);
            drawable.setColor(Color.TRANSPARENT);
        } else {

            holder.txtSize.setText(mSize.getNotation());
            if (mSize.getClick() == 1) {
                drawable.setColor(Color.parseColor("#222222"));
                holder.txtSize.setTextColor(Color.WHITE);

            } else {
                drawable.setColor(Color.WHITE);
                holder.txtSize.setTextColor(Color.BLACK);
            }

        }


    }

    @Override
    public int getItemCount() {
        return sizeArrayList.size();
    }

//    public abstract void onClickItem(int position, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtSize;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSize = (TextView) itemView.findViewById(R.id.txtSize);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = 1;
//                    onClickItem(getAdapterPosition(), v);
                    mSize = sizeArrayList.get(getAdapterPosition());

                    ActivityRecipe.pos = getAdapterPosition();
                    Global.sizePos = getAdapterPosition();
                    Global.products.get(ActivityRecipe.id).setSelectedSize(mSize.getNotation());
                    Global.products.get(ActivityRecipe.id).setSelectedPos(getAdapterPosition());

//                    DBManager.getInstance().addRecipeData(Global.products.get(ActivityRecipe.id), true, DBManager.TABLE_RECEIPE);
                    selectPos = Global.products.get(ActivityRecipe.id).getSelectedPos();
                    MyLog.e("Resume", "color " + Global.products.get(ActivityRecipe.id).getSelectedSize());
                    MyLog.e("Resume", "pos " + ActivityRecipe.id);
                    for (int i = 0; i < sizeArrayList.size(); i++) {
                        sizeArrayList.get(i).setClick(0);
                    }
                    sizeArrayList.get(getAdapterPosition()).setClick(1);

                    if (ActivityRecipeList.tv == 0) {
                    FragProductDetails.tvPrice.setText(mSize.getPrice() + " €");
                    FragProductDetails.tvDisPrice.setText(String.format("%.2f", getRealPrice()) + " €");
                    FragProductDetails.tvDiscount.setText(mSize.getDiscountRate() + "%");
//            FragProductDetails.tvDiscount.setText(Global.products.get(ActivityRecipe.id).getSize().get(ActivityRecipe.pos).getDiscountRate() + "%");
//            FragProductDetails.tvDisPrice.setText(String.format("%.2f", ActivityRecipe.calculateDiscountPrice()) + " €");
//                    FragProductDetails.tvPrice.setText(Global.products.get(ActivityRecipe.id).getSize().get(selectPos).getPrice() + " €");
                    }
                    notifyDataSetChanged();
                }
            });

        }
    }

    public float getRealPrice() {
        return Global.products.get(ActivityRecipe.id).getSize().get(selectPos).getPrice() - Global.products.get(ActivityRecipe.id).getSize().get(selectPos).getPrice() * (Global.products.get(ActivityRecipe.id).getSize().get(selectPos).getDiscountRate() / 100f);
    }
}
