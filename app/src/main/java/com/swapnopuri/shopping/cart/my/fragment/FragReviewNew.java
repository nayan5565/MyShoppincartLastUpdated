package com.swapnopuri.shopping.cart.my.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.adapter.AdReviewNew;
import com.swapnopuri.shopping.cart.my.model.MReview;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Dev on 10/11/2017.
 */

public class FragReviewNew extends Fragment implements RatingBar.OnRatingBarChangeListener {
    private static FragReviewNew instance;
    private View view;
    private ArrayList<MReview> list;
    private RecyclerView recyclerView;
    private AdReviewNew adapter;
    private int productId;
    private Button btnRate, btnSave;
    private MReview review;
    //    private LinearLayout llRating;
    private float star;
    private EditText edtReview;
    TextView tvWrite, tvView, tvRating;
    private LinearLayout lnSave, lnEdit;
    private RelativeLayout relWriteReview;
    private String userId;
    private RatingBar ratingBarChose, ratingbar;
    float ratingValue;

    private Gson gson = new Gson();

    public static FragReviewNew newInstance(int productId) {
        instance = new FragReviewNew();
        instance.productId = productId;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lay_frag_review_new, container, false);
        init();
        changeColorOfRatingBar();
        prepareDisplay();

        getReviewFromOnline();
        return view;
    }

    private void init() {
        relWriteReview = (RelativeLayout) view.findViewById(R.id.relWriteReview);
        ratingBarChose = (RatingBar) view.findViewById(R.id.ratingBarChoose);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ratingbar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBarChose.setOnRatingBarChangeListener(this);
        tvRating = (TextView) view.findViewById(R.id.tvRating);
        edtReview = (EditText) view.findViewById(R.id.edtReview);
        lnSave = (LinearLayout) view.findViewById(R.id.lnSave);
        lnSave.setVisibility(View.GONE);
        lnEdit = (LinearLayout) view.findViewById(R.id.lnWrite);
        btnSave = (Button) view.findViewById(R.id.btnSave);


        tvView = (TextView) view.findViewById(R.id.tvViews);
        tvWrite = (TextView) view.findViewById(R.id.tvWrite);
        String never = Utils.getPref(Global.productId + "", Global.STATE_TUT);

        MyLog.e("Never", " Show : " + never);
//        if (never.equals(Global.productId + "")) {
//            relWriteReview.setVisibility(View.GONE);
//            tvRating.setVisibility(View.GONE);
//            tvView.setVisibility(View.GONE);
//            ratingbar.setVisibility(View.GONE);
//
//        } else {
//            relWriteReview.setVisibility(View.VISIBLE);
//            tvRating.setVisibility(View.VISIBLE);
//            tvView.setVisibility(View.VISIBLE);
//            ratingbar.setVisibility(View.VISIBLE);
//
//        }

        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWriteClick();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveClick();

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdReviewNew(getContext()) {
            @Override
            public void onClickItem(int itemPositon, View view) {
                lnSave.setVisibility(View.VISIBLE);
                lnEdit.setVisibility(View.GONE);
                ratingBarChose.setRating(list.get(itemPositon).getStar());
                edtReview.setText(list.get(itemPositon).getTitle());
                edtReview.setSelection(edtReview.getText().length());
            }
        };
        recyclerView.setAdapter(adapter);
        list = new ArrayList<>();
        Utils.setFont("AvenirNext-Regular", edtReview, btnSave, tvWrite, tvView, tvRating);
    }

    private void changeColorOfRatingBar() {
        Drawable progress2 = ratingBarChose.getProgressDrawable();
        DrawableCompat.setTint(progress2, Color.RED);

        Drawable progress = ratingbar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.RED);
    }

    public void tvWriteClick() {
        lnSave.setVisibility(View.VISIBLE);
//                btnSave.setVisibility(View.VISIBLE);
//                edtReview.setVisibility(View.VISIBLE);
        lnEdit.setVisibility(View.GONE);
        ActivityRecipe.lnSize.setVisibility(View.GONE);
        ActivityRecipe.lnColor.setVisibility(View.GONE);
        ActivityRecipe.len.setVisibility(View.GONE);
        tvWrite.setVisibility(View.GONE);
    }

    public void btnSaveClick() {
        Utils.savePref(Global.productId + "", Global.productId + "");
        String never = Utils.getPref(Global.productId + "", Global.STATE_TUT);
        MyLog.e("Never", " save : " + never);
        lnSave.setVisibility(View.GONE);
        lnEdit.setVisibility(View.VISIBLE);
        ActivityRecipe.lnSize.setVisibility(View.VISIBLE);
        ActivityRecipe.lnColor.setVisibility(View.VISIBLE);
        ActivityRecipe.len.setVisibility(View.VISIBLE);

        tvWrite.setVisibility(View.GONE);
        sendReview();
//                edtReview.setVisibility(View.GONE);
//                btnSave.setVisibility(View.GONE);
    }

    private void getReviewFromOnline() {
        if (!Utils.isInternetOn())
            return;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        params.put("userid", Utils.getPref(Global.USER_ID, "0"));
        params.put("userid", Utils.getPref(Global.USER_ID, "1"));
        params.put("productid", productId);
        MyLog.e("Review", " params " + params.toString());
        userId = Utils.getPref(Global.USER_ID, "1");
        client.post(Global.BASE + Global.API_REVIEW, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                MyLog.e("Review", " response " + response.toString());
                try {
                    if (response.has("isPurchased") && response.getString("isPurchased").equals("true")) {
//                        llRating.setVisibility(View.VISIBLE);
                    }
                    list = new ArrayList<MReview>(Arrays.asList(gson.fromJson(response.getJSONArray("review").toString(), MReview[].class)));
                    MyLog.e("Review", " online size"+list.size());
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
//        ratingValue = Utils.getPref(Global.productId + "1", (float) 0.0);
        userId = Utils.getPref(Global.USER_ID, "1");
        list = DBManager.getInstance().getList(DBManager.getQueryReview(productId), new MReview());
        if (list != null && list.size() > 0)
            adapter.addData(list);


        if (list != null && list.size() > 0 && userId.equals(Utils.getPref(Global.USER_ID, "1"))) {
            MyLog.e("ReviewList", " size " + list.size());
            relWriteReview.setVisibility(View.GONE);
//            tvRating.setText(list.get(ActivityRecipe.id).getStar() + "");
            tvRating.setVisibility(View.GONE);
            tvView.setVisibility(View.GONE);
            ratingbar.setVisibility(View.GONE);


        } else {
            ratingbar.setRating(star);
            relWriteReview.setVisibility(View.VISIBLE);
            tvRating.setVisibility(View.VISIBLE);
            tvView.setVisibility(View.VISIBLE);
            ratingbar.setVisibility(View.VISIBLE);

        }

    }

    private void sendReview() {
        if (!Utils.isInternetOn())
            return;

        review = new MReview();
        review.setDate(Utils.getDateTime());
        review.setFullname(Utils.getPref(Global.USER, "GUEST"));
        review.setStar(star);
        review.setTitle(edtReview.getText().toString().trim());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        params.put("userid", Utils.getPref(Global.USER_ID, "0"));
        params.put("userid", Utils.getPref(Global.USER_ID, "1"));
        params.put("productid", productId);
        params.put("title", review.getTitle());
        params.put("star", review.getStar());
        params.put("datetime", review.getDate());
        MyLog.e("Review", " params send " + params.toString());
        client.post(Global.BASE + Global.API_ADD_REVIEW, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                MyLog.e("Review", " response send " + response.toString());
                try {
                    if (response.has("status") && response.getString("status").equals("success")) {
                        Utils.showToast(Global.MSG_CHECKOUT_CONFIRIM);
//                        Utils.savePref("NeverShow", Global.NEVER_SHOW);
                        getReviewFromOnline();
                    }
//                    if (response.has("status") && response.getString("status").equals("success")) {
//                        Utils.showToast(Global.MSG_CHECKOUT_CONFIRIM);
////                        Utils.savePref("NeverShow", Global.NEVER_SHOW);
//                        getOnlineData();
//                    }
                    else {
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
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        star = rating;
        Utils.savePref(Global.productId + "1", star);
        tvRating.setText(star + "");
//        Toast.makeText(getContext(), "New Rating: " + rating,
//                Toast.LENGTH_SHORT).show();
    }
}
