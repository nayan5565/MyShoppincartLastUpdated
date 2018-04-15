package com.swapnopuri.shopping.cart.my.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.adapter.ColorAdapter;
import com.swapnopuri.shopping.cart.my.adapter.SizeAdapter;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

/**
 * Created by JEWEL on 5/8/2017.
 */

public class FragProductDetails extends Fragment implements View.OnClickListener {
    private static FragProductDetails instance;
    private MRecipe recipe;
    private View view;
    private RecyclerView recColor, recSize;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    private Button btnBuy;
    private boolean isAddToCart;
    private int selectedPos;
    //    public static boolean isAddToCart;
    private int productId;
    public static TextView tvTitle,
            tvFav, tvShare, tvVideo, tvVideoNo, tvSource, tvPrice, tvDisPrice, tvDiscount, tvDcTitle, tvDiscripsion;
    private ImageView imgShopping;
    private TextView tvTitleMat, tvTitleProcess, tvMaterial, tvProcess;

    public static FragProductDetails newInstance(MRecipe recipe) {
        instance = new FragProductDetails();
        instance.recipe = recipe;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lay_product_details_new, container, false);
        init();
        prepareDisplay();
        showProductDetails();
        setAdapter();


        return view;

    }

    private void init() {
        tvMaterial = ((TextView) view.findViewById(R.id.tvMaterials));
        tvProcess = ((TextView) view.findViewById(R.id.tvProcess));
        tvTitleMat = ((TextView) view.findViewById(R.id.tvTitleMat));
        tvTitleProcess = ((TextView) view.findViewById(R.id.tvTitleProcess));

        recColor = (RecyclerView) view.findViewById(R.id.recColor);
        recSize = (RecyclerView) view.findViewById(R.id.recSize);
        colorAdapter = new ColorAdapter(getContext());
        sizeAdapter = new SizeAdapter(getContext());
        btnBuy = (Button) view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this);
        tvPrice = ((TextView) view.findViewById(R.id.tvPrice));
        tvDisPrice = ((TextView) view.findViewById(R.id.tvDisPrice));

        tvDiscount = ((TextView) view.findViewById(R.id.tvDiscount));
        tvDcTitle = ((TextView) view.findViewById(R.id.tvDcTitle));

        Utils.setFont("AvenirNext-Regular", tvPrice, btnBuy, tvDcTitle);
        Utils.setFont("AvenirNext-DemiBold", tvDiscount, tvDisPrice);
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra("productId")) {
            productId = getActivity().getIntent().getIntExtra("productId", 0);
        }
        if (FragOrder.currentOrderIds != null)
            isAddToCart = FragOrder.currentOrderIds.contains(productId);
//        imgShopping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void addToCart() {
        MOrderedItem orderedItem = new MOrderedItem();
        orderedItem.setId(recipe.getId());
        orderedItem.setAmount(recipe.getPrice());
        orderedItem.setPos(ActivityRecipe.id);
//        orderedItem.setColor(Color.RED);
//                    orderedItem.setOrderId(FragOrder.getOrderId());
        orderedItem.setQuantity(1);
        orderedItem.setThumb(recipe.getThumb());
        orderedItem.setTitle(recipe.getTitle());
        orderedItem.setUserId(Utils.getUser().getId());
        orderedItem.setDate(Utils.getDateTime());

        FragOrder.addCurrentItems(orderedItem);
        isAddToCart = !isAddToCart;
        btnBuy.setText(isAddToCart ? "REMOVE TO CART" : "ADD TO CART");
        btnBuy.setTextColor(isAddToCart ? Color.BLACK : Color.WHITE);
        btnBuy.setBackgroundColor(isAddToCart ? Color.parseColor("#F8E71C") : Color.parseColor("#222222"));
        Utils.showToast(isAddToCart ? "Successfully added to cart" : "Successfully removed from cart");
    }

    private void prepareDisplay() {


        if (recipe != null) {
            selectedPos = recipe.getSelectedPos();
            String first = recipe.getDescription();
            if (first != null) {
                char firstCharacter = first.charAt(0);
                if (firstCharacter == '<') {
                    Spanned htmlAsSpanned = Html.fromHtml(recipe.getDescription());
                    tvMaterial.setText(htmlAsSpanned);
                } else {
                    tvMaterial.setText(recipe.getDescription());
                }
            }

            MyLog.e("productDetails", " discription " + recipe.getDescription());
            Utils.setFont("AvenirNext-Regular", tvMaterial);
//            tvProcess.setText(recipe.getProcess());


        }
    }

    private void setAdapter() {
        MyLog.e("list ", " size " + Global.products.get(ActivityRecipe.id).getSize().size());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recSize.setLayoutManager(layoutManager);
        recColor.setLayoutManager(layoutManager2);
        recColor.setAdapter(colorAdapter);
        recSize.setAdapter(sizeAdapter);
//        ActivityRecipeList.products.get(id).getSize().get(pos).setClick(1);
//        ActivityRecipeList.products.get(id).getColors().get(pos).setClick(1);

        if (recipe.getColors().size() > 0)
            colorAdapter.setData(recipe.getColors());

        if (recipe.getSize().size() > 0)
            sizeAdapter.setData(recipe.getSize());


    }

    public float calculateDiscountPrice() {
        return recipe.getSize().get(selectedPos).getPrice() - recipe.getSize().get(selectedPos).getPrice() * (recipe.getSize().get(selectedPos).getDiscountRate() / 100f);
    }

    private void showProductDetails() {
//        Utils.setFont(tvTitleMat, tvTitleProcess, tvTitle, tvMaterial, tvProcess, tvShare, tvFav, tvVideo, tvVideoNo);
//        selectedPos = Global.products.get(ActivityRecipe.id).getSelectedPos();
//        getSupportActionBar().setTitle("");
//        tvVideoNo.setText(Utils.convertNum(getVideoNo() + ""));
        if (Global.products.get(ActivityRecipe.id).getSize().size() > 0) {
            MyLog.e("pos", " is " + ActivityRecipe.pos);
            if (recipe.getSize().get(ActivityRecipe.pos).getDiscountRate() == 0) {
                tvDcTitle.setVisibility(View.GONE);
                tvDiscount.setVisibility(View.GONE);
                tvDisPrice.setVisibility(View.GONE);
                tvPrice.setText(recipe.getSize().get(selectedPos).getPrice() + " €");
            } else {
                tvDcTitle.setVisibility(View.VISIBLE);
                tvDiscount.setVisibility(View.VISIBLE);
                tvDisPrice.setVisibility(View.VISIBLE);
                tvDiscount.setText(recipe.getSize().get(selectedPos).getDiscountRate() + "%");
                tvDisPrice.setText(String.format("%.2f", calculateDiscountPrice()) + " €");
                tvPrice.setText(recipe.getSize().get(selectedPos).getPrice() + " €");
                tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            btnBuy.setText(isAddToCart ? "REMOVE TO CART" : "ADD TO CART");
            btnBuy.setTextColor(isAddToCart ? Color.BLACK : Color.WHITE);
            btnBuy.setBackgroundColor(isAddToCart ? Color.parseColor("#F8E71C") : Color.parseColor("#222222"));
//            MyLog.e("Image", " " + recipe.getThumb());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnBuy:
                addToCart();
                break;
        }
    }
}
