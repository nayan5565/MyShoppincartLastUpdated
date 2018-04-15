package com.swapnopuri.shopping.cart.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.model.ContentItem;
import com.swapnopuri.shopping.cart.my.model.Header;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nayan on 10/30/2017.
 */

public abstract class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    //Header header;
    List<MOrderedItem> recipes;
    private Context context;
    private int userType;

    public MyRecyclerAdapter(Context context) {
        recipes = new ArrayList<>();
        userType = Utils.getUser().getType();
        this.context = context;
    }

    public void setData(ArrayList<MOrderedItem> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            return new VHHeader(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_current, parent, false);
            return new VHItem(v);
        }
        // return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VHHeader) {
            // VHHeader VHheader = (VHHeader)holder;
            Header currentItem = (Header) recipes.get(position);
            VHHeader VHheader = (VHHeader) holder;
            VHheader.txtTitle.setText(currentItem.getHeader());
        }
        //list items
        else if (holder instanceof VHItem) {
            ContentItem recipe = (ContentItem) recipes.get(position);
            VHItem VHitem = (VHItem) holder;
//        prevQuantity = recipes.get(position).getQuantity();
//            FragOrder.tvDate.setText(recipe.getDate());
//            if (!recipe.getDate().equals(Utils.getDateTime())) {
////            holder.tv_nz.setText(recipe.getDate());
//                VHitem.tvDate.setVisibility(View.VISIBLE);
//                VHitem.tvDate.setText(" row " + recipe.getDate());
//            } else {
//                VHitem.tvDate.setVisibility(View.GONE);
//            }
            VHitem.tvName.setText(recipe.getTitle());
            VHitem.tvQantiy.setText("" + recipe.getQuantity());
            VHitem.tvTotalAmount.setText((recipe.getAmount() * recipe.getQuantity()) + "€");
            VHitem.tvPrice.setText(recipe.getAmount() + "€");
//        Utils.setFont("AvenirNextCondensed-Bold", holder.tvTotalAmount, holder.tvPrice, holder.tvQantiy);
            Utils.setFont("AvenirNextCondensed-Regular", VHitem.tvName, VHitem.tvTotalAmount, VHitem.tvPrice, VHitem.tvQantiy);
            if (recipe.getThumb() != null && !recipe.getThumb().equals("") && !recipe.equals("null")) {
                Picasso.with(context).load(userType == Global.USER_ADMIN ? recipe.getThumb() : recipe.getThumb()).placeholder(R.drawable.placeholder).into(VHitem.img);
            }
        }
    }

    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {

        return recipes.get(position) instanceof Header;

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public abstract void onClickItem(int position, View view);

    class VHHeader extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtHeader);
        }
    }

    class VHItem extends RecyclerView.ViewHolder {
        public TextView tvName, tvPrice, tvTotalAmount, tvQantiy, tvDate;
        public ImageView img, tvPlus, tvMinus;
        TextView tv_nz;
        LinearLayout zv;

        public VHItem(View itemView) {
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
