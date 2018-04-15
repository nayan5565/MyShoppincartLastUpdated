package com.swapnopuri.shopping.cart.my.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MShipment;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Dev on 10/3/2017.
 */

public class ThankYouActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgBig;
    private Toolbar toolbar;
    private ImageView imgSearch;
    TextView tvHeader, tvDis;
    MShipment shipment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou_activity);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckOutAcitivity.relProgress.setVisibility(View.GONE);
                CheckOutAcitivity.progressBar.setVisibility(View.GONE);
            }
        }, 500);

        shipment = new MShipment();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#333333"));
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(this);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvDis = (TextView) findViewById(R.id.tvDiscripsion);
        Utils.setFont("AvenirNext-Regular", mTitle, tvHeader, tvDis);
        imgBig = (ImageView) findViewById(R.id.imgBig);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        GradientDrawable drawable = (GradientDrawable) imgBig.getBackground();
        drawable.setColor(Color.GRAY);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                DBManager.getInstance().deleteData(DBManager.TABLE_SHIPMENT, "_id", CheckOutAcitivity.shipment.get_id() + "");
                CheckOutAcitivity.tmpShipments = DBManager.getInstance().getData(DBManager.TABLE_SHIPMENT, new MShipment());
                MyLog.e("Shipment", " size " + CheckOutAcitivity.tmpShipments.size());
//                if (tmpShipments != null && tmpShipments.size() > 0) {
//                    Log.e("TEST", "GOT:" + tmpShipments.size());
//                    shipment = tmpShipments.get(0);
//
//                }

                finish();
            }
        }, 5000);

    }

    @Override
    public void onClick(View v) {
        DBManager.getInstance().deleteData(DBManager.TABLE_SHIPMENT, "_id", CheckOutAcitivity.shipment.get_id() + "");
        MyLog.e("Shipment", " close size " + CheckOutAcitivity.tmpShipments.size());
        CheckOutAcitivity.tmpShipments = DBManager.getInstance().getData(DBManager.TABLE_SHIPMENT, new MShipment());
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
