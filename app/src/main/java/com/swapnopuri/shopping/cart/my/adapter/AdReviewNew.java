package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MReview;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Dev on 10/11/2017.
 */

public abstract class AdReviewNew extends RecyclerView.Adapter<AdReviewNew.MyViewHolder> {
    private ArrayList<MReview> recipes;
    private Context context;
    private LayoutInflater inflater;

    public AdReviewNew(Context context) {
        recipes = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(ArrayList<MReview> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public AdReviewNew.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_review_new, parent, false);
        AdReviewNew.MyViewHolder holder = new AdReviewNew.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdReviewNew.MyViewHolder holder, int position) {
        MReview review = recipes.get(position);
        GradientDrawable drawable = (GradientDrawable) holder.img.getBackground();
        drawable.setColor(Color.RED);
        Drawable progress = holder.ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.RED);
        if (review != null) {
            holder.tvTitle.setText(review.getTitle());
            holder.tvFullName.setText(review.getFullname());
            holder.tvStar.setText("" + review.getStar());
            holder.ratingBar.setRating(review.getStar());

        }
        Utils.setFont("AvenirNextCondensed-DemiBold", holder.tvFullName);
        Utils.setFont("AvenirNext-Regular", holder.tvTitle, holder.tvReview, holder.tvStar);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int itemPositon, View view);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvFullName, tvStar, tvDate, tvReview;
        public ImageView img;
        public RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvReview);
            tvFullName = (TextView) itemView.findViewById(R.id.tvName);
            tvStar = (TextView) itemView.findViewById(R.id.tvStar);
            tvReview = (TextView) itemView.findViewById(R.id.tvReview);
            img = (ImageView) itemView.findViewById(R.id.img);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), v);
                }
            });
        }
    }
}
