package com.swapnopuri.shopping.cart.my.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.adapter.ColorAdapter;
import com.swapnopuri.shopping.cart.my.adapter.SizeAdapter;
import com.swapnopuri.shopping.cart.my.fragment.FragOrder;
import com.swapnopuri.shopping.cart.my.model.MOrder;
import com.swapnopuri.shopping.cart.my.model.MShipment;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NAYAN on 5/8/2017.
 */
public class CheckOutAcitivity extends AppCompatActivity implements View.OnClickListener {
    public static int totalItems, totalPrice;
    private Toolbar toolbar;
    private ImageView imgClose, imgAdd;
    String s, s2, s3, s4, s5, s6, s7, s8, s9, s10;
    public static MShipment shipment;
    public static ArrayList<MShipment> tmpShipments;
    //    private EditText etStreetAdd1, etStreetAdd2, etCity, etState, etCountry, etZipcode, etPhone;
    private Button bnSave;
    public static RelativeLayout relProgress;
    public static ProgressBar progressBar;
    private TextView tvTotalItem, tvTotalPrice, tvHome2,
            tvHome, tvCashDelv, tvCity, tvCountry, tvZipcode,
            tvMobile, tvAddNew, tvState, tvTitleTotal, tvTitleAmoun, tvShipping, tvPayMethod, tvOrderTitle, tvSelectFromPrevious;

    public static void start(Context context, int _totalItem, int _totalPrice) {
        totalItems = _totalItem;
        totalPrice = _totalPrice;
        context.startActivity(new Intent(context, CheckOutAcitivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getLocalData();
        prepareDisplay();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
////        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//    }
//
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//    }


    private void init() {

        setContentView(R.layout.check_out_activity_new);
        relProgress = (RelativeLayout) findViewById(R.id.relProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        imgAdd.setOnClickListener(this);
        tvTitleAmoun = (TextView) findViewById(R.id.tvTitleTotalAmount);
        tvTitleTotal = (TextView) findViewById(R.id.tvTitleTotalItems);
        tvCashDelv = (TextView) findViewById(R.id.tvCashDelv);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvState = (TextView) findViewById(R.id.tvState);
        tvAddNew = (TextView) findViewById(R.id.tvAddNew);
        tvAddNew.setOnClickListener(this);
        tvHome = (TextView) findViewById(R.id.tvHome);
        tvHome2 = (TextView) findViewById(R.id.tvHome2);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        tvZipcode = (TextView) findViewById(R.id.tvZipCode);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvTotalItem = (TextView) findViewById(R.id.tvTotalItems);
        tvPayMethod = (TextView) findViewById(R.id.tvPayMethod);
        tvShipping = (TextView) findViewById(R.id.tvShipping);
        tvOrderTitle = (TextView) findViewById(R.id.tvOrderTitle);
        tvSelectFromPrevious = (TextView) findViewById(R.id.tvSelectFromPrevious);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        toolbar.setBackgroundColor(Color.parseColor("#333333"));
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        bnSave = (Button) findViewById(R.id.btnDone);
        bnSave.setOnClickListener(this);
        Utils.setFont("AvenirNextCondensed-Regular", tvHome2,
                tvHome, tvCashDelv, tvCity, tvCountry, mTitle, tvZipcode,
                tvMobile, tvAddNew, tvState, tvTitleTotal, tvTitleAmoun,
                tvShipping, tvPayMethod, tvOrderTitle, tvSelectFromPrevious);
        Utils.setFont("AvenirNextCondensed-DemiBold", tvTotalItem, tvTotalPrice);
//

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTitleTotal.setText(Html.fromHtml("Total" + "<br>" + "Items"));
        tvTitleAmoun.setText(Html.fromHtml("Total" + "<br>" + "Amounts"));


//        // Spinner element
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//
//        // Spinner showSizeColor listener
//        spinner.setOnItemSelectedListener(this);
//
//        // Spinner Drop down elements
//        List<String> categories = new ArrayList<String>();
//        categories.add("Cash on Delivery");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        prepareDisplay();
//    }

    private void diaSizeColor() {
        final Dialog dialog = new Dialog(CheckOutAcitivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.lay_dia_check_out);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getLocalData() {
        tmpShipments = DBManager.getInstance().getData(DBManager.TABLE_SHIPMENT, new MShipment());
    }

    private void prepareDisplay() {
//        tmpShipments = DBManager.getInstance().getData(DBManager.TABLE_SHIPMENT, new MShipment());

        if (tmpShipments != null && tmpShipments.size() > 0) {
            Log.e("TEST", "GOT:" + tmpShipments.size());
            shipment = tmpShipments.get(0);

        } else {
            shipment = new MShipment();
        }
        setText();


    }

    public void setText() {

        tvTotalItem.setText("" + totalItems);
//        tvTotalPrice.setText("" + totalPrice);
        String str = String.format("%,d", totalPrice);
        tvTotalPrice.setText(String.format("€" + str));
//        tvCity.setText(shipment.getCity());
//        tvCountry.setText(shipment.getCountry());
        tvZipcode.setText(shipment.getZipCode());
//        tvHome2.setText(shipment.getStreetAddress2());
//        tvHome.setText(shipment.getStreetAddress1());
        tvMobile.setText(shipment.getPhone());
//        tvState.setText(shipment.getState());
        s = shipment.getStreetAddress2();
        s9 = shipment.getStreetAddress1();
        s2 = shipment.getCity();
        s3 = shipment.getState();
        s4 = shipment.getCountry();
        MyLog.e("s " + s + " s2 " + s2 + " s3 " + s3, " s4 " + s4);
        if (s == null && s2 == null && s3 == null && s4 == null && s9 == null) {
            tvHome2.setVisibility(View.GONE);
        } else {
            if (s9 == null || s9.equals("") || s9.equals("null")) {
                s10 = "";
            } else {
                s10 = s9 + ",";
            }
            if (s == null || s.equals("") || s.equals("null")) {
                s5 = "";
            } else {
                s5 = s + ",";
            }
            if (s2 == null || s2.equals("") || s2.equals("null")) {
                s6 = "";
            } else {
                s6 = s2 + ",";
            }
            if (s3 == null || s3.equals("") || s3.equals("null")) {
                s7 = "";
            } else {
                s7 = s3 + ",";
            }
            if (s4 == null || s4.equals("") || s4.equals("null")) {
                s8 = "";
            } else {
                s8 = s4 + ",";
            }
            tvHome2.setVisibility(View.VISIBLE);
            String s11 = s10 + s5 + s6 + s7 + s8;
            String s12;
            if (s11 != null && s11.length() > 0) {
                MyLog.e("str ", s11 + " last is " + s11.charAt(s11.length() - 1));
                if (s11.charAt(s11.length() - 1) == ',') {
                    s12 = s11.substring(1, s11.length() - 1);
                } else {
                    s12 = s11;
                }
                tvHome2.setText(s12);
            }
        }


//        etState.setText(shipment.getState());
//        etCountry.setText(shipment.getCountry());
//        etZipcode.setText(shipment.getZipCode());
//        etCity.setText(shipment.getCity());
//        etStreetAdd1.setText(shipment.getStreetAddress1());
//        etStreetAdd2.setText(shipment.getStreetAddress2());
//        etPhone.setText(shipment.getPhone());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocalData();
        prepareDisplay();
    }

    private void sendToServer() {
//        shipment.setCountry(etCountry.getText().toString());
//        shipment.setState(etState.getText().toString());
//        shipment.setZipCode(etZipcode.getText().toString());
//        shipment.setCity(etCity.getText().toString());
//        shipment.setStreetAddress1(etStreetAdd1.getText().toString());
//        shipment.setStreetAddress2(etStreetAdd2.getText().toString());
//        shipment.setPhone(etPhone.getText().toString());
//        shipment.setCountry(tvCountry.getText().toString());
//        shipment.setState(tvState.getText().toString());
//        shipment.setZipCode(tvZipcode.getText().toString());
//        shipment.setCity(tvCity.getText().toString());
//        shipment.setStreetAddress1(tvHome.getText().toString());
//        shipment.setStreetAddress2(tvHome2.getText().toString());
//        shipment.setPhone(tvMobile.getText().toString());

//        DBManager.getInstance().addData(DBManager.TABLE_SHIPMENT, shipment, "_id");
        if (!Utils.isInternetOn()) {
            Toast.makeText(CheckOutAcitivity.this, " Need to internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        relProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", Utils.getUser().getUsername());
        params.put("order_id", "");
        params.put("full_name", Utils.getUser().getFullName());
        params.put("phone", shipment.getPhone());
        params.put("street_address", shipment.getStreetAddress1());
        params.put("street_address_2", shipment.getStreetAddress2());
        params.put("city", shipment.getCity());
        params.put("zip_code", shipment.getZipCode());
        params.put("state", shipment.getState());
        params.put("country", shipment.getCountry());

        client.post(Global.API_SHIPMENT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Utils.log(response.toString());
                relProgress.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                try {
                    if (response.has("message") && response.getString("message").equals("success")) {
//                        Utils.showDialog(CheckOutAcitivity.this, Global.TITLE_CONFIROMATION, Global.MSG_CHECKOUT_CONFIRIM, new IOnCallback() {
//                            @Override
//                            public void onCall(String mgs) {
//                                finish();
//                            }
//                        });
                        Intent intent = new Intent(CheckOutAcitivity.this, ThankYouActivity.class);
                        startActivity(intent);

                        finish();
                        relProgress.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                relProgress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Log.e("TEST-ERR", responseString);
            }
        });
    }

    private void accept() {
        MOrder order = new MOrder();
        order.setUserId(Integer.parseInt(Utils.getPref(Global.USER_ID, "1")));
        order.setUserName(Utils.getPref(Global.USER, ""));
        order.setDateTime(Utils.getToday());
        order.setTotalAmount(totalPrice);
        order.setTotalQuantity(totalItems);
        order.setStatus(1);


        int orderId = (int) DBManager.getInstance().addOrder(order);
        order.setOrderId(orderId);
        DBManager.getInstance().addOrder(order);

        for (int i = 0; i < FragOrder.currentItems.size(); i++) {
            FragOrder.currentItems.get(i).setOrderId(orderId);
            DBManager.getInstance().addOrderedItems(FragOrder.currentItems.get(i));
        }
        FragOrder.currentOrderIds.clear();
        FragOrder.currentItems.clear();
        sendToServer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAdd:
                startActivity(new Intent(CheckOutAcitivity.this, SubmitActivity.class));
                break;
            case R.id.btnDone:
                if (s == null || s.length() <= 0 || s2 == null || s2.length() <= 0 || s3 == null || s3.length() <= 0 || s4 == null || s4.length() <= 0 || s9 == null || s9.length() <= 0) {
                    diaSizeColor();
                    return;
                } else {
                    accept();
                }
                break;
            case R.id.tvAddNew:
                startActivity(new Intent(CheckOutAcitivity.this, SubmitActivity.class));
                break;
            case R.id.imgClose:
                finish();
                break;
        }
    }
}
