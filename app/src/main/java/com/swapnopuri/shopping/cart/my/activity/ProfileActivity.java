package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MProfile;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NAYAN on 5/5/2017.
 */
public class ProfileActivity extends AppCompatActivity {
    private Gson gson;
    private MProfile profile;
    private Toolbar toolbar;
    TextView tvFullName, tvMail;
    ImageView imgProfile, imgBack;
    private Button btnAllOrder, btnUpdate;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ProfileActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_new);
        init();
        getProfileFromOnline();
        prepareDisplay();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(ProfileActivity.this, MainActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        gson = new Gson();
        btnAllOrder = (Button) findViewById(R.id.btnAllOrder);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });
        GradientDrawable drawable = (GradientDrawable) imgProfile.getBackground();
        drawable.setColor(Color.WHITE);
        tvFullName = (TextView) findViewById(R.id.txtFullName);
        tvMail = (TextView) findViewById(R.id.txtMail);
        profile = new MProfile();

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void getProfileFromOnline() {
        if (!Utils.isInternetOn()) {
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", Utils.getPref(Global.USER, "m@gmail.com"));

        client.post(Global.BASE + Global.API_PROFILE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("PROFILE", response.toString());
                try {
                    if (response.has("message") && response.getString("message").equals("success")) {
                        profile = gson.fromJson(response.toString(), MProfile.class);
                        long id = DBManager.getInstance().addData(DBManager.TABLE_PROFILE, profile, "id");
                        prepareDisplay();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("PROFILE", "ERR");
            }
        });
    }

    private void prepareDisplay() {
        ArrayList<MProfile> tmpProfiles = DBManager.getInstance().getData(DBManager.TABLE_PROFILE, new MProfile());
        if (tmpProfiles != null && tmpProfiles.size() > 0) {
            profile = tmpProfiles.get(0);
        }
        if (profile == null) return;
        setString(R.id.txtFullName, profile.getFullname());
        setString(R.id.txtMail, "" + profile.getEmail());
//        setString(R.id.txtMale, "" + profile.getGender());
//        setString(R.id.txtBirthDate, "" + profile.getBirthDate());
//        setString(R.id.txtCountry, "" + profile.getCountry());
//        setString(R.id.txtPhone, "" + profile.getPhone());
        Utils.setFont("AvenirNext-Regular", tvMail, tvFullName, btnAllOrder, btnUpdate);
    }

    private void setString(int id, String value) {
        if (value == null) return;
        ((TextView) findViewById(id)).setText(value);
    }
}
