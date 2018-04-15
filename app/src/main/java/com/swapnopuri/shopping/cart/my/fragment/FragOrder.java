package com.swapnopuri.shopping.cart.my.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityRecipe;
import com.swapnopuri.shopping.cart.my.activity.CheckOutAcitivity;
import com.swapnopuri.shopping.cart.my.activity.MainActivity;
import com.swapnopuri.shopping.cart.my.adapter.AdAllOrder;
import com.swapnopuri.shopping.cart.my.adapter.AdCurrentOrder;
import com.swapnopuri.shopping.cart.my.adapter.MyRecyclerAdapter;
import com.swapnopuri.shopping.cart.my.model.ContentItem;
import com.swapnopuri.shopping.cart.my.model.MNotification;
import com.swapnopuri.shopping.cart.my.model.MOrder;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.model.MPush;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MSendOrder;
import com.swapnopuri.shopping.cart.my.tools.ConfirmationDialog;
import com.swapnopuri.shopping.cart.my.tools.CustomRequest;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.NumberTextWatcherForThousand;
import com.swapnopuri.shopping.cart.my.tools.Utils;
import com.swapnopuri.shopping.cart.my.tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

/**
 * Created by JEWEL on 2/11/2017.
 */

public class FragOrder extends Fragment implements View.OnClickListener {
    public static int orderId;
    public static ArrayList<Integer> currentOrderIds = new ArrayList<>();
    public static ArrayList<MOrderedItem> currentItems;
    private static FragOrder instance;
    private static MOrder order;
    private ArrayList<com.swapnopuri.shopping.cart.my.model.Header> headers;
    private ArrayList<com.swapnopuri.shopping.cart.my.model.Header> uniqeHeaders;
    private MOrder currentOrder;
    private View view;
    private RecyclerView recyclerView;
    private AdCurrentOrder adapter;
    //    private MyRecyclerAdapter adapter;
    private AdAllOrder adapterAll;
    private int totalPrice = 0, totalQuantity = 0;
    private TextView tvTotalAmount, tvTotalItems;
    private Button btnAccept, btnCancel, btnCurrentOrder, btnAllOrder;
    private LinearLayout llFooter, llTop;
    private RelativeLayout relAllorder;
    public static int viewDismiss;
    private String date;
    private TextView tvOrder, tvOrderHistory, tvTitleTotalItems, tvTitleTotalAmount;
    public static TextView tvDate;

    public static FragOrder newInstance() {
        instance = new FragOrder();
        return instance;
    }


    public static void addCurrentItems(MOrderedItem item) {
        if (currentOrderIds == null) currentOrderIds = new ArrayList<>();
        if (currentItems == null) currentItems = new ArrayList<>();
        if (!currentOrderIds.contains(item.getId())) {
            currentItems.add(item);
            currentOrderIds.add(item.getId());

        } else {

            for (int i = 0; i < currentOrderIds.size(); i++) {
                if (currentOrderIds.get(i) == item.getId()) {
                    currentOrderIds.remove(i);
                }
            }
            for (int i = 0; i < currentItems.size(); i++) {
                if (currentItems.get(i).getId() == item.getId()) {
                    currentItems.remove(i);
                }
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lay_order, container, false);

        init();
        if (Utils.getUser().getType() == Global.USER_ADMIN) {
            Utils.log("admin");
            prepareCurrentOrderAdmin();
        } else {
            Utils.log("normal user");
            prepareCurrentOrder();
        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (Utils.getUser().getType() == Global.USER_ADMIN) {
                prepareCurrentOrderAdmin();
            } else {
                prepareCurrentOrder();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
//                btnCurrentOrder.setBackgroundColor(Color.TRANSPARENT);
//                btnCurrentOrder.setTextColor(Color.parseColor("#000000"));
//                btnAllOrder.setBackgroundColor(Color.TRANSPARENT);
//                btnAllOrder.setTextColor(Color.parseColor("#333333"));
                if (Utils.getUser().getType() == Global.USER_ADMIN) {
                    prepareCurrentOrderAdmin();
                } else {
                    prepareCurrentOrder();
                }
            } catch (Exception ex) {

            }

        }
    }

    private void init() {
        date = Utils.getDateTime();
        MyLog.e("Time ", "now " + date);
        headers = new ArrayList<>();
        uniqeHeaders = new ArrayList<>();
        view.findViewById(R.id.btnCurentOrder).setOnClickListener(this);
        llTop = (LinearLayout) view.findViewById(R.id.llTop);
        tvOrder = (TextView) view.findViewById(R.id.tvOrderTitle);
        tvTitleTotalAmount = (TextView) view.findViewById(R.id.tvTitleTotalAmount);
        tvTitleTotalItems = (TextView) view.findViewById(R.id.tvTitleTotalItems);
        tvOrderHistory = (TextView) view.findViewById(R.id.tvOrderHistory);
        tvOrder.setBackgroundColor(Color.parseColor("#4D4D4D"));
        tvOrderHistory.setBackgroundColor(Color.TRANSPARENT);
        llFooter = (LinearLayout) view.findViewById(R.id.llaFooter);
        relAllorder = (RelativeLayout) view.findViewById(R.id.relAllorder);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setVisibility(View.GONE);
        relAllorder.setVisibility(View.GONE);
        tvTotalItems = ((TextView) view.findViewById(R.id.tvTotalItems));
        tvTotalAmount = ((TextView) view.findViewById(R.id.tvTotalAmount));

        btnAccept = (Button) view.findViewById(R.id.btnAccept);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        GradientDrawable drawable = (GradientDrawable) btnCancel.getBackground();
        drawable.setColor(Color.parseColor("#4D4D4D"));
        GradientDrawable drawable2 = (GradientDrawable) btnAccept.getBackground();
        drawable2.setColor(Color.parseColor("#000000"));
        btnCurrentOrder = (Button) view.findViewById(R.id.btnCurentOrder);
        btnAllOrder = (Button) view.findViewById(R.id.btnAllOrder);

        btnCurrentOrder.setBackgroundColor(Color.TRANSPARENT);
        btnCurrentOrder.setTextColor(Color.parseColor("#000000"));
        btnAllOrder.setBackgroundColor(Color.TRANSPARENT);
        btnAllOrder.setTextColor(Color.parseColor("#e0e0d1"));
//        Utils.setFont("AvenirNextCondensed-Bold", tvTotalItems, tvTotalAmount);
        Utils.setFont("AvenirNextCondensed-Regular", tvTotalItems, tvTotalAmount, tvOrder, tvTitleTotalAmount, tvTitleTotalItems, tvOrderHistory, btnAccept, btnCancel);
        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnCurrentOrder.setOnClickListener(this);
        btnAllOrder.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                }
            }
        });

        adapter = new AdCurrentOrder(getContext()) {
            @Override
            public void onClickItem(int position, View view) {
                if (position > currentItems.size() || position < 0) return;
                switch (view.getId()) {
                    case R.id.tvMinus:

                        int prevQuantity = currentItems.get(position).getQuantity();
                        if (prevQuantity > 0) {
                            currentItems.get(position).setQuantity(prevQuantity - 1);

                            //stay last pos listview
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, currentItems.size());
//                            currentItems.get(position).setAmount(currentItems.get(position).getQuantity() * 100);
                        } else {
                            addCurrentItems(currentItems.get(position));
                        }
                        prepareCurrentOrder();
                        break;
                    case R.id.tvPlus:
                        prevQuantity = currentItems.get(position).getQuantity();
                        currentItems.get(position).setQuantity(prevQuantity + 1);
                        // stay last pos
                        notifyItemRangeChanged(position, getItemCount());
//                        currentItems.get(position).setAmount(currentItems.get(position).getQuantity() * 100);
                        prepareCurrentOrder();
                        break;
                    default:
//                        MRecipe recipe = new MRecipe();
//
////                        ActivityRecipe.id = Global.productPos;
//                        recipe.setId(currentItems.get(position).getId());
//                        recipe.setTitle(currentItems.get(position).getTitle());
//                        recipe.setThumb(currentItems.get(position).getThumb());
//                        recipe.setPrice(currentItems.get(position).getAmount());
//                        Global.products.add(recipe);
//                        ActivityRecipe.id = currentItems.get(position).getPos();
//                        MyLog.e("id ", +recipe.getId() + " order " + currentItems.get(position).getId());
//                        ActivityRecipe.recipe = Global.products.get(ActivityRecipe.id);
////                        if (Global.orderLayoutDissmiss == 1) {
////                            ActivityRecipe.lnRecipe.setVisibility(View.VISIBLE);
////                            ActivityRecipe.lnFrag.setVisibility(View.GONE);
////                        } else {
////                            startActivity(new Intent(getContext(), ActivityRecipe.class).putExtra("productId", recipe.getId()));
////                        }
////                        if (ActivityRecipe.getInstance() != null) {
////                            ActivityRecipe.getInstance().finish();
////                            startActivity(new Intent(getContext(), ActivityRecipe.class).putExtra("productId", recipe.getId()));
////                        }
//                        ActivityRecipe.getInstance().finish();
//                        startActivity(new Intent(getContext(), ActivityRecipe.class).putExtra("productId", recipe.getId()));
                        startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        };
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCurentOrder:
                tvOrder.setBackgroundColor(Color.parseColor("#4D4D4D"));
                tvOrderHistory.setBackgroundColor(Color.TRANSPARENT);
                relAllorder.setVisibility(View.GONE);
//                btnCurrentOrder.setBackgroundColor(Color.WHITE);
//                btnCurrentOrder.setBackgroundColor(Color.parseColor("#6C4878"));
                btnCurrentOrder.setTextColor(Color.parseColor("#000000"));
//                btnAllOrder.setBackgroundColor(Color.parseColor("#333333"));
                btnAllOrder.setTextColor(Color.parseColor("#e0e0d1"));

                if (Utils.getUser().getType() == Global.USER_ADMIN) {
                    prepareCurrentOrderAdmin();

                } else {
                    prepareCurrentOrder();
                    recyclerView.setAdapter(adapter);
                }

                break;
            case R.id.btnAllOrder:
//                relAllorder.setVisibility(View.VISIBLE);
                tvOrder.setBackgroundColor(Color.TRANSPARENT);
                tvOrderHistory.setBackgroundColor(Color.parseColor("#4D4D4D"));
//                btnCurrentOrder.setBackgroundColor(Color.parseColor("#6C4878"));
                btnCurrentOrder.setTextColor(Color.parseColor("#e0e0d1"));
//                btnAllOrder.setBackgroundColor(Color.WHITE);
                btnAllOrder.setTextColor(Color.parseColor("#000000"));
                prepareAllOrder(Utils.getUser().getType());

                break;

            case R.id.btnAccept:
                if (currentItems == null || currentItems.size() < 1) {
                    Utils.showToast("Empty order !!!");
                    return;
                }
//                new ConfirmationDialog(getContext(), new ConfirmationDialog.IYesNo() {
//                    @Override
//                    public void onYes() {
//                        accept();
//                    }
//
//                    @Override
//                    public void onNo() {
//
//                    }
//                });
                accept();
                CheckOutAcitivity.start(getContext(), totalQuantity, totalPrice);

                break;
            case R.id.btnCancel:
                cancelOrder();
                break;
        }
    }

    private void showDialog(int orderId, int userId) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_order);

        LinearLayout llFooter = (LinearLayout) dialog.findViewById(R.id.llaFooter);
        LinearLayout llTop = (LinearLayout) dialog.findViewById(R.id.llTop);
        TextView tvTotalItems = (TextView) dialog.findViewById(R.id.tvTotalItems);
        TextView tvTotalAmount = (TextView) dialog.findViewById(R.id.tvTotalAmount);
        TextView tvTitleAmount = (TextView) dialog.findViewById(R.id.tvTitleTotalAmount);
        TextView tvTitleItems = (TextView) dialog.findViewById(R.id.tvTitleTotalItems);

        Button btnAccept = (Button) dialog.findViewById(R.id.btnAccept);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);


        llFooter.setVisibility(Utils.getUser().getType() == Global.USER_ADMIN ? View.VISIBLE : View.GONE);

        llTop.setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdCurrentOrder adapter = new AdCurrentOrder(getContext()) {
            @Override
            public void onClickItem(int position, View view) {

            }
        };
        ArrayList<MOrderedItem> items = DBManager.getInstance().getList(DBManager.getCurrentOrderedItem(orderId, userId), new MOrderedItem());
//        headers = DBManager.getInstance().getList(DBManager.getQueryDate(DBManager.TABLE_ORDERED_ITEM, ))
        adapter.setData(items);
        recyclerView.setAdapter(adapter);

        if (Utils.getUser().getType() == Global.USER_ADMIN) {
            tvTitleAmount.setVisibility(View.GONE);
            tvTitleItems.setVisibility(View.GONE);
            tvTotalAmount.setVisibility(View.GONE);
            tvTotalItems.setVisibility(View.GONE);

            btnAccept.setText(Global.BTN_TXT_ACCEPT_ORDER);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ConfirmationDialog(getContext(), new ConfirmationDialog.IYesNo() {
                        @Override
                        public void onYes() {
                            if (currentOrder != null) {
                                approve(currentOrder.getOrderId(), currentOrder.getUserId());
                            } else {
                                Utils.showToast("ERROR !!");
                            }
                            dialog.dismiss();

                        }

                        @Override
                        public void onNo() {
                            dialog.dismiss();
                        }
                    });


                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLog.e("CLICK", "cancel");
                }
            });
        }

        dialog.show();

    }

    private void showDialogForAdmin(int status, ArrayList<MOrderedItem> items) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_order);
//        int w=Utils.getWindowWidth(getContext());
//        int h=Utils.getWindowHeight(getContext());
//        dialog.getWindow().setLayout(w,h-400);

        MyLog.e("HEight", Utils.getWindowHeight(getContext()) + "");

        LinearLayout llFooter = (LinearLayout) dialog.findViewById(R.id.llaFooter);
        LinearLayout llTop = (LinearLayout) dialog.findViewById(R.id.llTop);
        TextView tvTotalItems = (TextView) dialog.findViewById(R.id.tvTotalItems);
        TextView tvTotalAmount = (TextView) dialog.findViewById(R.id.tvTotalAmount);
        TextView tvTitleAmount = (TextView) dialog.findViewById(R.id.tvTitleTotalAmount);
        TextView tvTitleItems = (TextView) dialog.findViewById(R.id.tvTitleTotalItems);

        Button btnAccept = (Button) dialog.findViewById(R.id.btnAccept);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);


        llFooter.setVisibility(Utils.getUser().getType() == Global.USER_ADMIN ? View.VISIBLE : View.GONE);
        llFooter.setVisibility(status == 2 ? View.GONE : View.VISIBLE);

        llTop.setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdCurrentOrder adapter = new AdCurrentOrder(getContext()) {
            @Override
            public void onClickItem(int position, View view) {

            }
        };
//        ArrayList<MOrderedItem> items = DBManager.getInstance().getList(DBManager.getCurrentOrderedItem(orderId, userId), new MOrderedItem());
        adapter.setData(items);
        recyclerView.setAdapter(adapter);

        if (Utils.getUser().getType() == Global.USER_ADMIN) {
            tvTitleAmount.setVisibility(View.GONE);
            tvTitleItems.setVisibility(View.GONE);
            tvTotalAmount.setVisibility(View.GONE);
            tvTotalItems.setVisibility(View.GONE);

            btnAccept.setText(Global.BTN_TXT_ACCEPT_ORDER);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ConfirmationDialog(getContext(), new ConfirmationDialog.IYesNo() {
                        @Override
                        public void onYes() {
                            if (currentOrder != null) {
                                MyLog.e("APPROVE", "oId:" + currentOrder.getId());
                                approve(currentOrder.getOrderId(), currentOrder.getUserId());
                            } else {
                                Utils.showToast("ERROR !!");
                            }
                            dialog.dismiss();

                        }

                        @Override
                        public void onNo() {
                            dialog.dismiss();
                        }
                    });


                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLog.e("CLICK", "cancel");
                }
            });
        }

        dialog.show();

    }

    private void getUserBasedItems(final int orderId, final int userId) {

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("order_id", orderId + "");
        params.put("user_id", userId + "");

        client.post(Global.API_ORDER_REQ, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                MyLog.e("VID", response.toString());
                if (response.has("message")) {

                    try {
                        if (response.getString("message").equals("success")) {
                            Gson gson = new Gson();
                            int status = response.getJSONObject("order").getInt("status");
                            MOrderedItem[] items = gson.fromJson(response.getJSONObject("order").getJSONArray("details").toString(), MOrderedItem[].class);
                            for (int i = 0; i < items.length; i++) {
                                DBManager.getInstance().addOrderedItems(items[i]);
                            }
                            showDialogForAdmin(status, new ArrayList<MOrderedItem>(Arrays.asList(items)));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    private void approve(int orderId, int userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderId + "");
        params.put("order_user_id", userId + "");
        CustomRequest reqVideo = new CustomRequest(Request.Method.POST, Global.API_ORDER_APPROVE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                MyLog.e("VID", response.toString());
                if (response.has("message")) {

                    try {
                        if (response.getString("message").equals("success")) {
                            currentOrder.setStatus(2);
                            DBManager.getInstance().addOrderAdmin(currentOrder);
                            prepareCurrentOrderAdmin();
                            MPush push = new MPush();
                            push.setTitle("Order Approve");
                            push.setMsg("Your order has approved");
                            push.setType(Global.TYPE_ORDER);
                            push.setToUser(currentOrder.getUserId());
                            push.setFromUser(Utils.getUser().getId());
                            Utils.callFirebase(push);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private void prepareCurrentOrderAdmin() {
        llFooter.setVisibility(View.GONE);
        relAllorder.setVisibility(View.VISIBLE);
        tvDate.setVisibility(View.GONE);
        final ArrayList<MOrder> orders = DBManager.getInstance().getList(DBManager.getQueryCurrentOrderAdmin(), new MOrder());
        adapterAll = new AdAllOrder(getContext()) {
            @Override
            public void onClickItem(int position, View view) {
                currentOrder = orders.get(position);
                //showDialog(orders.get(position).getOrderId(), orders.get(position).getUserId());
                getUserBasedItems(currentOrder.getId(), currentOrder.getUserId());
            }
        };

        recyclerView.setAdapter(adapterAll);


        adapterAll.setData(orders);
    }

    private void getOrdersInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", Utils.getPref(Global.USER_ID, "1"));
        params.put("sync_datetime", Utils.getDateTime());
        params.put("user_type", Utils.getPref(Global.USER_TYPE, "0"));
        MyLog.e("TEST", "not:" + params.toString());

        client.post(Global.API_ORDER_NOTIFICATION, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
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

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void prepareCurrentOrder() {

        if (adapter == null)
            init();
        if (currentItems == null) currentItems = new ArrayList<>();


        adapter.setData(currentItems);
//        adapter.setData(getList());
//        recyclerView.setAdapter(adapter);
        totalPrice = 0;
        totalQuantity = 0;
        for (MOrderedItem recipe : currentItems) {
            totalPrice += (recipe.getAmount() * recipe.getQuantity());
            totalQuantity += recipe.getQuantity();

        }
        String str = String.format("%,d", totalPrice);
        tvTotalItems.setText("" + currentItems.size());
        tvTotalAmount.setText(String.format(str + "€"));
        tvOrder.setBackgroundColor(Color.parseColor("#4D4D4D"));
        tvOrderHistory.setBackgroundColor(Color.TRANSPARENT);
        tvOrderHistory.setTextColor(Color.parseColor("#e0e0d1"));
        tvOrder.setTextColor(Color.parseColor("#000000"));
        llFooter.setVisibility(View.VISIBLE);
        // trying get unique value
//        for (int i = 0; i < currentItems.size(); i++) {
//            if (!headers.contains(currentItems.get(i))) {
//                com.swapnopuri.shopping.cart.my.model.Header m = new com.swapnopuri.shopping.cart.my.model.Header();
//                m.setHeader(currentItems.get(i).getDate());
//                headers.add(m);
//
//
//            }
//        }
//        for (com.swapnopuri.shopping.cart.my.model.Header s : headers) {
//            if (!uniqeHeaders.contains(s))
//                uniqeHeaders.add(s);
//        }
//        for (int k = 0; k < headers.size(); k++) {
//            if (!uniqeHeaders.contains(headers.get(k).getHeader())) {
//                com.swapnopuri.shopping.cart.my.model.Header m = new com.swapnopuri.shopping.cart.my.model.Header();
//                m.setHeader(headers.get(k).getHeader());
//                uniqeHeaders.add(m);
//            }
//        }
//        Set<com.swapnopuri.shopping.cart.my.model.Header> uniqueGas = new HashSet<com.swapnopuri.shopping.cart.my.model.Header>(headers);
//
//        MyLog.e("CurrentItems", " Size " + currentItems.size());
//        MyLog.e("CurrentItems", " headers Size " + headers.size());
//        MyLog.e("CurrentItems", " Unique Size " + uniqeHeaders.size());
        if (currentItems != null && currentItems.size() > 0) {
            tvDate.setVisibility(View.VISIBLE);
//            tvDate.setText(Utils.getDateTime());
        } else {
            tvDate.setVisibility(View.GONE);
        }

        relAllorder.setVisibility(View.GONE);
    }

    public void afterTextChanged(TextView view) {
        String s = null;
        try {
            // The comma in the format specifier does the trick
            s = String.format("%,d", Long.parseLong(view.toString()));
        } catch (NumberFormatException e) {
        }
        // Set s back to the view after temporarily removing the text change listener
    }

    private ArrayList<MOrderedItem> getList() {
        ArrayList<MOrderedItem> arrayList = new ArrayList<>();
        for (int i = 0; i < currentItems.size(); i++) {
            if (!headers.contains(currentItems.get(i))) {
                com.swapnopuri.shopping.cart.my.model.Header m = new com.swapnopuri.shopping.cart.my.model.Header();
                m.setHeader(currentItems.get(i).getDate());
                headers.add(m);


            }
        }
        MyLog.e("CurrentItems", " headers Size " + headers.size());
        for (int j = 0; j < headers.size(); j++) {
//            com.swapnopuri.shopping.cart.my.model.Header header = new com.swapnopuri.shopping.cart.my.model.Header();
//            header.setHeader(headers.get(j).getHeader());
//            arrayList.addAll(headers);
            for (int i = 0; i < currentItems.size(); i++) {
                ContentItem item = new ContentItem();
                item.set_id(currentItems.get(i).get_id());
                item.setAmount(currentItems.get(i).getAmount());
                item.setOrderId(currentItems.get(i).getOrderId());
                item.setQuantity(currentItems.get(i).getQuantity());
                item.setThumb(currentItems.get(i).getThumb());
                item.setTitle(currentItems.get(i).getTitle());
                item.setUserId(currentItems.get(i).getUserId());
                item.setId(currentItems.get(i).getId());
                arrayList.add(item);
            }
        }
        return arrayList;
    }

    private void prepareAllOrder(int type) {
        llFooter.setVisibility(View.GONE);
        tvDate.setVisibility(View.GONE);

        final ArrayList<MOrder> orders = DBManager.getInstance().getList(type == Global.USER_ADMIN ? DBManager.getQueryAllOrderAdmin() : DBManager.getQueryAllOrder(), new MOrder());
        if (orders.size() > 0) {
            relAllorder.setVisibility(View.VISIBLE);
        } else relAllorder.setVisibility(View.GONE);
        adapterAll = new AdAllOrder(getContext()) {
            @Override
            public void onClickItem(int position, View view) {
                if (Utils.getUser().getType() == Global.USER_ADMIN)
                    getUserBasedItems(orders.get(position).getId(), orders.get(position).getUserId());
                else
                    viewDismiss = 1;
                showDialog(orders.get(position).getOrderId(), orders.get(position).getUserId());
            }
        };

        recyclerView.setAdapter(adapterAll);

        if (orders != null && orders.size() > 0)
            adapterAll.setData(orders);
    }

    private void cancelOrder() {
        if (order == null) return;
        orderId = 0;
        currentOrderIds.clear();
        currentItems.clear();
        currentItems = new ArrayList<>();
        prepareCurrentOrder();
    }

    private void accept() {
        order = new MOrder();
        order.setUserId(Integer.parseInt(Utils.getPref(Global.USER_ID, "1")));
        order.setUserName(Utils.getPref(Global.USER, ""));
        order.setDateTime(Utils.getToday());
        order.setTotalAmount(totalPrice);
        order.setTotalQuantity(totalQuantity);
        order.setStatus(1);


//        orderId = (int) DBManager.getInstance().addOrder(order);
//        order.setOrderId(orderId);
//        DBManager.getInstance().addOrder(order);


        for (int i = 0; i < currentItems.size(); i++) {
            currentItems.get(i).setOrderId(orderId);
//            DBManager.getInstance().addOrderedItems(currentItems.get(i));
        }

        sendToServer();

    }

    private void sendToServer() {

        HashMap<String, String> params = new HashMap<>();

        MSendOrder sendOrder = new MSendOrder();
        sendOrder.setTotal(totalPrice);
        sendOrder.setItems(currentItems);
        sendOrder.setQuantity(totalQuantity);
        sendOrder.setUserId(order.getUserId());
        sendOrder.setDatetime(Utils.getDateTime());


        Gson gson = new Gson();
        String data = gson.toJson(sendOrder);


        params.put("post_order", data);

        MyLog.e("TEST_server", data);


        CustomRequest reqVideo = new CustomRequest(Request.Method.POST, Global.API_ORDER_SEND, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyLog.e("VID", response.toString());
//                currentItems.clear();
//                currentItems = new ArrayList<>();
//                currentOrderIds.clear();
//                currentOrderIds = new ArrayList<>();
//                orderId = 0;
//                prepareCurrentOrder();
                if (response.has("local_order_id")) {
                    try {
                        int orderId = response.getInt("local_order_id");
                        long result = DBManager.getInstance().updateOrderStatus(orderId, 1);
                        MyLog.e("OrderUp", "r:" + result);
                        MPush push = new MPush();
                        push.setMsg("From : " + Utils.getUser().getUsername());
                        push.setTitle("New Order");
                        push.setToUser(Global.NOT_FOR_ADMIN);
                        push.setFromUser(Utils.getUser().getId());


                        Utils.callFirebase(push);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
