package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.Utils;

/**
 * Created by Dev on 10/14/2017.
 */

public class LogSignActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLgoin, btnSign;
    TextView tvSkip;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LogSignActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_login_new_2);
        btnLgoin = (Button) findViewById(R.id.btnLogin);
        btnSign = (Button) findViewById(R.id.btnSign);
        tvSkip = (TextView) findViewById(R.id.tvSkip);
        btnLgoin.setOnClickListener(this);
        btnSign.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (v.getId() == R.id.btnSign) {
            startActivity(new Intent(this, RegistrationActivity.class));
        } else if (v.getId() == R.id.tvSkip) {
            Utils.savePref(Global.STATE, Global.STATE_SPLASH);
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}
