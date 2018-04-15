package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MReview;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public abstract class AdReview extends RecyclerView.Adapter<AdReview.MyViewHolder> {
    private ArrayList<MReview> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdReview(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(ArrayList<MReview> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_review, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MReview review = recipes.get(position);
        if (review != null) {
            holder.tvTitle.setText(review.getTitle());
            holder.tvFullName.setText(review.getFullname());
            holder.tvDate.setText(review.getDate());
            holder.tvStar.setText("" + Utils.getIntToStar((int) review.getStar()));

        }

        Utils.setFont("AvenirNextCondensed-DemiBold", holder.tvFullName);
        Utils.setFont("AvenirNext-Regular", holder.tvTitle, holder.tvDate, holder.tvStar);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int itemPositon, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvFullName, tvStar, tvDate;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvReview);
            tvFullName = (TextView) itemView.findViewById(R.id.tvName);
            tvStar = (TextView) itemView.findViewById(R.id.tvStar);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            img = (ImageView) itemView.findViewById(R.id.img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
