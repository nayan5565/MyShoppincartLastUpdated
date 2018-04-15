package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.model.MColor;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;

import java.util.ArrayList;

/**
 * Created by Nayan on 10/2/2017.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MColor> colorArrayList;
    private MColor mColor;
    private int click = 0;


    public ColorAdapter(Context context) {
        this.context = context;
        colorArrayList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MColor> mProducts) {
        this.colorArrayList = mProducts;
//        if (showSizeColor == 0) {
//            for (int i = 0; i < colorArrayList.size(); i++) {
//                colorArrayList.get(i).setClick(0);
//            }
//            colorArrayList.get(0).setClick(1);
//        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_color, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        mColor = colorArrayList.get(position);
        GradientDrawable drawable = (GradientDrawable) holder.btnColor.getBackground();
        drawable.setColor(Color.parseColor("#" + mColor.getHex()));
        if (mColor.getClick() == 1) {
            holder.imgTick.setVisibility(View.VISIBLE);
        } else {
            holder.imgTick.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return colorArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView btnColor;
        ImageView imgTick;


        public MyViewHolder(View itemView) {
            super(itemView);
            Log.e("pos", " is showSizeColor");
            btnColor = (ImageView) itemView.findViewById(R.id.btnColor);
            imgTick = (ImageView) itemView.findViewById(R.id.imgTick);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = 1;
                    mColor = colorArrayList.get(getAdapterPosition());
                    Global.products.get(ActivityRecipe.id).setSelectedColor("#" + mColor.getHex());
                    MyLog.e("Resume", "color " + Global.products.get(ActivityRecipe.id).getSelectedColor());
                    MyLog.e("Resume", "pos " + ActivityRecipe.id);
                    Log.e("pos", " is showSizeColor item");
                    for (int i = 0; i < colorArrayList.size(); i++) {
                        colorArrayList.get(i).setClick(0);
                    }
                    colorArrayList.get(getAdapterPosition()).setClick(1);
                    Log.e("pos", " is showSizeColor" + colorArrayList.get(getAdapterPosition()).getClick());
                    notifyDataSetChanged();
                }
            });

        }
    }
}
