package com.swapnopuri.shopping.cart.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MShipment;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Utils;

/**
 * Created by Dev on 10/3/2017.
 */

public class SubmitActivity extends AppCompatActivity {
    private EditText etStreetAdd1, etStreetAdd2, etCity, etState, etCountry, etZipcode, etPhone;
    private MShipment shipment;
    private Button bnSave;
    private Toolbar toolbar;
    private TextView tvShippment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_submit_activity);
        init();
        prepareDisplay();

    }

    public void init() {
        shipment = new MShipment();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tvShippment = (TextView) findViewById(R.id.txtShipmentAddress);
        bnSave = (Button) findViewById(R.id.btnDone);
        Utils.setFont("AvenirNext-Regular", mTitle, tvShippment, bnSave);
        etStreetAdd1 = (EditText) findViewById(R.id.etStreetAdd1);
        etStreetAdd2 = (EditText) findViewById(R.id.etStreetAdd2);
        etState = (EditText) findViewById(R.id.etState);
        etCity = (EditText) findViewById(R.id.etCity);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etZipcode = (EditText) findViewById(R.id.etZipCode);
        etPhone = (EditText) findViewById(R.id.etPhone);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDb();
                CheckOutAcitivity.tmpShipments = DBManager.getInstance().getData(DBManager.TABLE_SHIPMENT, new MShipment());
                finish();
            }
        });
    }

    public void prepareDisplay() {
        if (CheckOutAcitivity.tmpShipments != null && CheckOutAcitivity.tmpShipments.size() > 0) {
            Log.e("TEST", "GOT:" + CheckOutAcitivity.tmpShipments.size());
            shipment = CheckOutAcitivity.tmpShipments.get(0);

        } else {
            shipment = new MShipment();
        }
       
        etState.setText(shipment.getState());
        etCountry.setText(shipment.getCountry());
        etZipcode.setText(shipment.getZipCode());
        etCity.setText(shipment.getCity());
        etStreetAdd1.setText(shipment.getStreetAddress1());
        etStreetAdd2.setText(shipment.getStreetAddress2());
        etPhone.setText(shipment.getPhone());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveToDb() {
        shipment.setCountry(etCountry.getText().toString());
        shipment.setState(etState.getText().toString());
        shipment.setZipCode(etZipcode.getText().toString());
        shipment.setCity(etCity.getText().toString());
        shipment.setStreetAddress1(etStreetAdd1.getText().toString());
        shipment.setStreetAddress2(etStreetAdd2.getText().toString());
        shipment.setPhone(etPhone.getText().toString());


        DBManager.getInstance().addData(DBManager.TABLE_SHIPMENT, shipment, "_id");

    }

}
