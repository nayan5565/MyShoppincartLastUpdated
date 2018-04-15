package com.swapnopuri.shopping.cart.my.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.firebase.Config;
import com.swapnopuri.shopping.cart.my.firebase.NotificationUtils;
import com.swapnopuri.shopping.cart.my.model.MUser;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by JEWEL on 2/11/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<MUser> users;
    private Gson gson;
    TextView tvSign, tvReset;
    Button btnLets;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        prepareUsers();

        listenFirebase();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    private void init() {
        setContentView(R.layout.lay_login_new);

        tvReset = (TextView) findViewById(R.id.tvReset);
        tvSign = (TextView) findViewById(R.id.tvSign);
        btnLets = (Button) findViewById(R.id.btnLets);
        btnLets.setOnClickListener(this);
        Utils.setFont("AvenirNext-Regular", tvReset, tvSign, btnLets);

        if (users == null)
            users = new ArrayList<>();
        gson = new Gson();

    }

    private void listenFirebase() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    Log.e("TEST", "reg done");

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    Log.e("TEST", "new msg");
//                    String message = intent.getStringExtra("message");

//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };
    }
//
//    public void onLogin(View view) {
//        String user = getEdtString(R.id.edtEmail);
//        String pass = getEdtString(R.id.edtPass);
//        if (user.length() > 0 && pass.length() > 0) {
//            getOnlineData(user, pass);
//
//        } else {
//            Utils.showToast("Username or pass will not be empty !!1");
//            return;
//        }
//
//
//    }

//    public void onRegis(View view) {
//        Intent intent = new Intent(this, RegistrationActivity.class);
//        startActivity(intent);
//        finish();
//    }


    public void getOnlineData(String user, String pass) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", user);
        params.put("password", pass);
//        params.put("deviceId", "7");
        params.put("deviceId", Utils.getDeviceId(this));
        MyLog.e("response", params.toString());
        client.get(Global.BASE + Global.API_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                MyLog.e("response", response.toString());

                try {
                    if (response.has("message") && response.getString("message").equals("success")) {
                        Utils.log("userId::" + response.getString("id"));
                        Utils.savePref("profile2", Global.STATE_PROFILE2);
                        Utils.savePref(Global.STATE, Global.STATE_SPLASH);
                        String profile = Utils.getPref("profile", Global.STATE_TUT);

                        Utils.savePref(Global.USER_ID, response.getString("id"));
                        MyLog.e("response_user_id", response.getString("id"));
                        Utils.savePref(Global.USER, response.getString("email"));
//                        Utils.savePref(Global.USER_TYPE, ""+Global.USER_ADMIN);
                        Utils.savePref(Global.USER_TYPE, response.getString("userType"));
                        if (profile.equals(Global.STATE_PROFILE)) {
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                        }
                        finish();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utils.showToast("Wrong username or password!!");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void prepareUsers() {
        if (users.size() > 1) return;
        users.add(new MUser(1, 2, "candyparty", "123456", "Candy Party"));
        users.add(new MUser(2, 1, "Hasan", "123456", "Mohammad Hasanur Rahaman"));
        users.add(new MUser(3, 1, "Imti", "123456", "Sheikh Imtiaz Hossain"));
        users.add(new MUser(4, 1, "Jewel", "123456", "Md. Zahidul Islam"));

    }

    private String getEdtString(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }

    @Override
    public void onClick(View v) {
        String user = getEdtString(R.id.edtEmail).trim();
        String pass = getEdtString(R.id.edtPass).trim();
        MyLog.e("user " + user, " pass " + pass);
        if (user.length() > 0 && pass.length() > 0) {
            getOnlineData(user, pass);

        } else {
            Utils.showToast("Username or pass will not be empty !!1");
            return;
        }
//        getOnlineData("","");
    }
}
