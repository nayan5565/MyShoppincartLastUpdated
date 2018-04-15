package com.swapnopuri.shopping.cart.my.tools;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.swapnopuri.shopping.cart.my.model.MApiSendFav;
import com.swapnopuri.shopping.cart.my.model.MApiTimeTracker;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MSendFavRecipe;
import com.swapnopuri.shopping.cart.my.model.MTimeTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEWEL on 10/2/2016.
 */

public class API {
    private RequestQueue requestQueue;
    private Gson gson;
    private Context context;

    public API(Context context) {
        gson = new Gson();
        this.context = context;
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
    }


    public void sendTimeTracker() {
        if (!Utils.isInternetOn()) return;
        String appId = Utils.getPref("app_id", "0");
        if (appId.equals("0")) return;

        final ArrayList<MTimeTracker> timeTrackers = DBManager.getInstance().getList(DBManager.getQueryTimeTracker(), new MTimeTracker());
        MApiTimeTracker t = new MApiTimeTracker();
        t.setUid(Integer.parseInt(appId));
        t.setEmail(Utils.getPhoneGmailAcc());
        t.setTimetrack(timeTrackers);
        String json = gson.toJson(t);

        if (timeTrackers == null || timeTrackers.size() < 1)
            return;

        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        Map<String, String> params = new HashMap<>();
        params.put("timeTracker", json);
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_TIME_TRACKING, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && response.has("message")) {
                    MyLog.e("RES", response.toString());
                    for (MTimeTracker timeTracker : timeTrackers)
                        timeTracker.setStatus(1);
                    DBManager.getInstance().addAllData(DBManager.TABLE_TIME_TRACKER, timeTrackers, "id");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(customRequest);
    }

    public void favRecipeSync() {
        if (!Utils.isInternetOn()) return;
        String appId = Utils.getPref("app_id", "0");
        if (appId.equals("0")) {
            getAppId();
            return;
        }


        Map<String, String> params = new HashMap<>();
        params.put("appId", appId);
        params.put("userEmail", Utils.getPhoneGmailAcc());
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_FAV_RECIPE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && response.has("recipes")) {
                    try {
                        ArrayList<MRecipe> recipes = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("recipes").toString(), MRecipe[].class)));
                        for (MRecipe r : recipes) {
                            r.setFav(1);
                        }
                        MyLog.e("FAV", "s:" + recipes.get(0).getFav());
                        DBManager.getInstance().addAllData(DBManager.TABLE_RECEIPE, recipes, "Id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLog.e("FAV-err", error.toString());
            }
        });

        ArrayList<MSendFavRecipe> fRecipes = DBManager.getInstance().getList(DBManager.getQueryFav(DBManager.TABLE_RECEIPE), new MSendFavRecipe());

        for (MSendFavRecipe s : fRecipes) {
            s.setType(1);
        }


        MApiSendFav s = new MApiSendFav();
        s.setUid(Integer.parseInt(appId));
        s.setEmail(Utils.getPhoneGmailAcc());
        s.setRecipeId(fRecipes);
        String json = gson.toJson(s);

        MyLog.e("JS", json);
        params = new HashMap<>();
        params.put("favorite", json);

        CustomRequest send = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_SEND_FAV_RECIPE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyLog.e("RES-send", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLog.e("RES-err", error.toString());
            }
        });


        requestQueue.add(send);
        requestQueue.add(customRequest);
    }

    public void getAppId() {
        HashMap params = new HashMap<>();
        params.put("deviceId", Utils.getDeviceId(context));
        params.put("email", Utils.getPhoneGmailAcc());

        CustomRequest send = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_FAV_RECIPE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int appId = response.getJSONObject("user").getInt("id");
                    Utils.savePref("app_id", appId + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLog.e("RES-err", error.toString());
            }
        });


        requestQueue.add(send);
    }

}
