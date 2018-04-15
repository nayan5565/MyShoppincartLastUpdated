package com.swapnopuri.shopping.cart.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdRecipe;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SearchView searchView;
    private ArrayList<MRecipe> allRecipes, tmpRecipes;
    private AdRecipe adRecipe;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        getLocalData();
        setupSearchView();
        MyApp.getInstance().setupAnalytics("Search");
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // while open search view
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }

        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setData(newText);
                return false;
            }
        });
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        searchView = (SearchView) findViewById(R.id.searchView);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont("AvenirNext-Regular", mTitle);

        toolbar.setBackgroundColor(Color.parseColor("#333333"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView.setQueryHint(Global.MENU_SEARCH);
        searchView.onActionViewExpanded();

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setAdapter(adRecipe);


        adRecipe = new AdRecipe(this) {
            @Override
            public void onClickItem(int position, View view) {
                ActivityRecipe.recipe = tmpRecipes.get(position);
                MRecipe recipe = ActivityRecipe.recipe;
//                startActivity(new Intent(SearchActivity.this, ActivityRecipe.class));
                MOrderedItem orderedItem = new MOrderedItem();
                orderedItem.setId(recipe.getId());
                orderedItem.setAmount(100);  //todo set unit price
//                    orderedItem.setOrderId(FragOrder.getOrderId());
                orderedItem.setQuantity(1);
                orderedItem.setThumb(recipe.getThumb());
                orderedItem.setTitle(recipe.getTitle());
                orderedItem.setUserId(Utils.getUser().getId());

                FragOrder.addCurrentItems(orderedItem);


                adRecipe.notifyDataSetChanged();
            }
        };
        recyclerView.setAdapter(adRecipe);
    }

    public void setData(String q) {
        tmpRecipes = new ArrayList<>();
        for (MRecipe ca : allRecipes) {
            if (ca.getSearchTag().toLowerCase().contains(q.toLowerCase()) || ca.getTitle().toLowerCase().contains(q.toLowerCase()))
                tmpRecipes.add(ca);
        }
        if (tmpRecipes != null && tmpRecipes.size() > 0)
            adRecipe.setData(tmpRecipes);
    }

    private void getLocalData() {
        allRecipes = DBManager.getInstance().getRecipe(DBManager.TABLE_RECEIPE);
        if (allRecipes != null && allRecipes.size() > 0) {
            adRecipe.setData(allRecipes);
            tmpRecipes = allRecipes;
        }

    }
}
