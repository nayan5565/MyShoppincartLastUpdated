package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdFav;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/23/2016.
 */
public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<MRecipe> recipes;
    private AdFav adapter;
    private Toolbar toolbar;

    public static void start(Context context) {
        context.startActivity(new Intent(context, FavoriteActivity.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        prepareDisplay();

    }

    @Override
    public void onResume() {
        super.onResume();
        prepareDisplay();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {
        setContentView(R.layout.frag_fav);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont("AvenirNextCondensed-Regular", mTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdFav(this) {
            @Override
            public void onClickItem(int position, View view) {
                MRecipe recipe = recipes.get(position);

                ActivityRecipe.id = position;
                ActivityRecipeList.showSizeColor = 0;

                ActivityRecipe.recipe = recipe;
                Global.products = recipes;
                startActivity(new Intent(FavoriteActivity.this, ActivityRecipe.class));
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void prepareDisplay() {
//        recipes = DBManager.getInstance().getList(DBManager.getQueryFav(DBManager.TABLE_RECEIPE), new MRecipe());
        recipes = DBManager.getInstance().getFavData();
        MyLog.e("REC", "sss:" + recipes.size());
        if (recipes.size() <= 0) {
            return;
        }
        adapter.setData(recipes);
    }
}
