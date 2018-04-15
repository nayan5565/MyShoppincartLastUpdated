package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MVideo;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdVideo extends RecyclerView.Adapter<AdVideo.MyViewHolder> {
    private ArrayList<MVideo> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdVideo(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(ArrayList<MVideo> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_video, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MVideo recipe = recipes.get(position);
        if (recipe != null) {
            holder.tvTitle.setText(recipe.getMenuTitle());
            Picasso.with(context).load(Global.API_BASE_BACKEND + recipe.getThumb()).placeholder(R.drawable.placeholder).into(holder.img);
        }

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int itemPositon, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            img = (ImageView) itemView.findViewById(R.id.img);
            Utils.setFont(tvTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
