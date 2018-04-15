package com.swapnopuri.shopping.cart.my.tools;

import android.content.Context;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MNotification;
import com.swapnopuri.shopping.cart.my.model.MOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JEWEL on 2/24/2017.
 */

public class UpdateNotification {

    public static boolean isUpdateAble = true;
    public static UpdateNotification instace;
    private static ArrayList<Integer> orderIds;
    private final int INTERVAL = 1000 * 60 * 1;
    private Context context;
    private Handler handler;
    private boolean isNotifcation;

    public UpdateNotification(Context context) {
        this.context = context;
        handler = new Handler();
        instace = this;
        callOnline();
    }

    public void startListner() {
        if (handler == null) {
            MyLog.e("TEST", "handler null");
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUpdateAble) {
                    MyLog.e("TEST", "handler running");
                    callOnline();
                    startListner();
                }
            }
        }, INTERVAL);
    }

    public void stopListner() {
        if (handler == null) return;
        handler.removeCallbacks(null);
        handler = null;
    }

    private void callOnline() {
        HashMap<String, String> params = new HashMap<>();
//        params.put("user_id", "1");
        params.put("user_id", Utils.getPref(Global.USER_ID, "1"));
        params.put("sync_datetime", Utils.getDateTime());
        params.put("user_type", Utils.getPref(Global.USER_TYPE, "0"));

        MyLog.e("TEST", "not:" + params.toString());
        CustomRequest reqVideo = new CustomRequest(Request.Method.POST, Global.API_ORDER_NOTIFICATION, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyLog.e("VID", response.toString());
                try {
                    if (response.getString("message").equals("success")) {
                        int userType = Integer.parseInt(Utils.getPref(Global.USER_TYPE, "1"));

//                        if (userType != Global.USER_ADMIN)
//                            isUpdateAble = false;
                        Gson gson = new Gson();
                        MNotification[] nots = gson.fromJson(response.getJSONArray("notify").toString(), MNotification[].class);
                        for (int i = 0; i < nots.length; i++) {

                            if (userType != Global.USER_ADMIN)
                                DBManager.getInstance().updateOrderStatus(nots[i].getOrderId(), nots[i].getStatus());
                            else {
                                MOrder o = new MOrder();
                                o.setId(nots[i].getId());
                                o.setOrderId(nots[i].getOrderId());
                                o.setUserId(nots[i].getUserId());
                                o.setTotalQuantity(nots[i].getTotalQuantity());
                                o.setStatus(nots[i].getStatus());
                                o.setDateTime(nots[i].getDateTime());
                                o.setUserName(nots[i].getUserName());
                                o.setUserPic(nots[i].getUserPic());
                                o.setTotalAmount(nots[i].getTotalAmount());

                                DBManager.getInstance().addOrderAdmin(o);



                            }
                            if (orderIds == null)
                                orderIds = new ArrayList<>();
                            if (!orderIds.contains(nots[i].getId())) {
                                isNotifcation = true;
                                orderIds.add(nots[i].getId());
                            } else {

                            }
//                            if (nots[i].getDetails() != null) {
//                                for (int j = 0; j < nots[i].getDetails().size(); j++) {
//                                    MOrderedItem item = nots[i].getDetails().get(j);
//                                    item.setUserId(nots[i].getUserId());
//                                    item.setOrderId(nots[i].getOrderId());
//                                    DBManager.getInstance().addOrderedItems(item);
//                                }
//                            }

                        }
                        if (isNotifcation) {
                            Utils.sendNotfication(context, Global.MSG_NOT_TITLE_USER, Global.MSG_NOT_ORDER_RECEIVED, R.drawable.logo);
                            isNotifcation = false;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLog.e("TEST", "ERR:" + error.toString());
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(reqVideo);
    }
}
