package com.swapnopuri.shopping.cart.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.AdReview;
import com.swapnopuri.shopping.cart.my.model.MReview;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by JEWEL on 5/8/2017.
 */

public class FragReview extends Fragment implements View.OnClickListener {
    private static FragReview instance;
    private View view;
    private ArrayList<MReview> list;
    private RecyclerView recyclerView;
    private AdReview adapter;
    private int productId;
    private Button btnRate;
    private MReview review;
    private LinearLayout llRating;
    private EditText etTitle;
    private RatingBar ratingBar;
    private Gson gson = new Gson();

    public static FragReview newInstance(int productId) {
        instance = new FragReview();
        instance.productId = productId;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lay_review, container, false);
        init();
        prepareDisplay();
        getOnlineData();
        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        llRating = (LinearLayout) view.findViewById(R.id.layRating);
        btnRate = (Button) view.findViewById(R.id.btnRate);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        btnRate.setOnClickListener(this);
        llRating.setVisibility(View.GONE);
        Utils.setFont("AvenirNext-Regular", etTitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdReview(getContext()) {
            @Override
            public void onClickItem(int itemPositon, View view) {

            }
        };
        recyclerView.setAdapter(adapter);
        list = new ArrayList<>();
    }

    private void getOnlineData() {
        if (!Utils.isInternetOn())
            return;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", Utils.getPref(Global.USER_ID, "0"));
        params.put("productid", productId);
        client.post(Global.BASE + Global.API_REVIEW, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.has("isPurchased") && response.getString("isPurchased").equals("true")) {
                        llRating.setVisibility(View.VISIBLE);
                    }
                    list = new ArrayList<MReview>(Arrays.asList(gson.fromJson(response.getJSONArray("review").toString(), MReview[].class)));
                    if (list != null && list.size() > 0)
                        DBManager.getInstance().addAllData(DBManager.TABLE_REVIEW, list, "Id");
                    prepareDisplay();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void prepareDisplay() {
        list = DBManager.getInstance().getList(DBManager.getQueryReview(productId), new MReview());
        if (list != null && list.size() > 0)
            adapter.addData(list);
    }

    private void sendReview() {
        if (!Utils.isInternetOn())
            return;

        review = new MReview();
        review.setDate(Utils.getDateTime());
        review.setFullname(Utils.getPref(Global.USER, "GUEST"));
        review.setStar((int) ratingBar.getRating());
        review.setTitle(etTitle.getText().toString().trim());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", Utils.getPref(Global.USER_ID, "0"));
        params.put("productid", productId);
        params.put("title", review.getTitle());
        params.put("star", review.getStar());
        params.put("datetime ", review.getDate());
        client.post(Global.BASE + Global.API_ADD_REVIEW, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.has("status") && response.getString("status").equals("success")) {
                        Utils.showToast(Global.MSG_CHECKOUT_CONFIRIM);
                        getOnlineData();
                    } else {
                        Utils.showToast(Global.MSG_EXIST_REVIEW);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRate:
                sendReview();
                break;
        }
    }
}
