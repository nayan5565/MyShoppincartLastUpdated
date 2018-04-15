package com.swapnopuri.shopping.cart.my.tools;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.MainActivity;
import com.swapnopuri.shopping.cart.my.model.MNotification;
import com.swapnopuri.shopping.cart.my.model.MOrder;
import com.swapnopuri.shopping.cart.my.model.MPush;
import com.swapnopuri.shopping.cart.my.model.MUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class Utils {

    private static Typeface typeface;

    public static String getIntToStar(int starCount) {
        String fillStar = "\u2605";
        String blankStar = "\u2606";
        String star = "";
        for (int i = 0; i < starCount; i++) {
            star = star.concat(" " + fillStar);
        }
        for (int j = (3 - starCount); j > 0; j--) {
            star = star.concat(" " + blankStar);
        }
        return star;
    }

    public static String databasePassKey(String emailName, String deviceId) {
        String firstChOfEmail = emailName.substring(0, 1);
        String lastChOfEmail = emailName.substring(emailName.indexOf("@") - 1, emailName.indexOf("@"));
        String firstNumOfDeviceId = deviceId.substring(0, 1);
        String secondNumOfDeviceId = deviceId.substring(deviceId.length() - 1);
        return firstChOfEmail + lastChOfEmail + firstNumOfDeviceId + secondNumOfDeviceId;
    }

    //show notification
    public static void sendNotfication(Context context, String title, String msg, int smallIcon) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pintent)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(smallIcon)
                .build();

        NotificationManager notificationMana = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationMana.notify(0, notification);
    }

    public static boolean isInternetOn() {

        try {
            ConnectivityManager con = (ConnectivityManager) MyApp.getInstance().getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi, mobile;
            wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobile = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnectedOrConnecting() || mobile.isConnectedOrConnecting()) {
                return true;
            }


        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    // get app version name
    public static String getAppVersion(Context context) {
        String version = "";
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    // save data to sharedPreference
    public static void savePref(String name, String value) {
        SharedPreferences pref = MyApp.getInstance().getContext().getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static void savePref(String name, Float value) {
        SharedPreferences pref = MyApp.getInstance().getContext().getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(name, value);
        editor.apply();
    }

    public static void clearPref() {
        SharedPreferences pref = MyApp.getInstance().getContext().getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }

    // get data from shared preference
    public static String getPref(String name, String defaultValue) {
        SharedPreferences pref = MyApp.getInstance().getContext().getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
        return pref.getString(name, defaultValue);
    }

    public static Float getPref(String name, float defaultValue) {
        SharedPreferences pref = MyApp.getInstance().getContext().getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
        return pref.getFloat(name, defaultValue);
    }

    public static void showToast(String msg) {
        Toast.makeText(MyApp.getInstance().getContext(), msg, Toast.LENGTH_LONG).show();
        ;
    }


    /**
     * get active gmail account
     * need permission for this
     **/
    public static String getPhoneGmailAcc() {
        AccountManager accountManager = AccountManager.get(MyApp.getInstance().getContext());
        Account[] accounts = accountManager.getAccountsByType("com.google");
        return accounts.length > 0 ? accounts[0].name.trim().toLowerCase() : "null";

    }

    // get android device id
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * android device imei ID
     * need permission for this (android.permission.READ_PHONE_STATE)
     */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyMgr.getDeviceId();
    }

    public static String getDecryptPass(String key) {
        String device = getDeviceId(MyApp.getInstance().getContext()), email = "mzijewel@gmail.com", response;

        response = email.charAt(0) + key.substring(0, 4) + email.charAt(email.lastIndexOf("@") - 1) + key.substring(4)
                + key.substring(8, 12) + device.charAt(0) + key.substring(12) + device.charAt(device.length() - 1);
        MyLog.e("KEY", response);
        MyLog.e("DEV", device);
        return response;
    }

    public static void sendEmail(Context context, String body) {
        String[] TO = {Global.EMAIL_TO};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Global.EMAIL_SUBJECT_TXT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MyApp.getInstance().getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setFont(TextView... textViews) {
        if (typeface == null)
            typeface = Typeface.createFromAsset(MyApp.getInstance().getAssets(), "fonts/solaimanlipi.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }

    public static void setFont(String font, TextView... textViews) {


        typeface = Typeface.createFromAsset(MyApp.getInstance().getAssets(), "fonts/" + font + ".ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }


    public static String convertNum(String num) {
        if (num.length() > 0) {
            num = num.replace("0", "০").replace("1", "১").replace("2", "২")
                    .replace("3", "৩").replace("4", "৪").replace("5", "৫")
                    .replace("6", "৬").replace("7", "৭").replace("8", "৮")
                    .replace("9", "৯");
        }


        return num;
    }

    public static String getPath(String fileName) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".BR" + File.separator + Global.VIDEO_DOWNLOAD_DIR;
        File file = new File(dir);
        if (!file.exists())
            file.mkdirs();
        return dir + File.separator + fileName;

    }

    // find out the width of phone screen
    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
//  static   Context context;
//
//    public static int scaledSize = context.getResources().getDimensionPixelSize(R.dimen.sp20);

    // get current date
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date_time = sdf.format(new Date());
        MyLog.e("TEST", "date:" + date_time);
        return date_time;
    }

    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String day = sdf.format(new Date());
        MyLog.e("TEST", "datye:" + day);
        return day;
    }

    public static void showDialog(Context context, String title, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.lay_dialog);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvDetails);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        setRounded(btnOk, Color.WHITE, Color.GRAY);

        setFont(tvMsg, tvTitle);
        tvTitle.setText(title);
        tvMsg.setText(msg);
//        btnOk.setText(Global.MSG_OK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showDialog(Context context, String title, String msg, final IOnCallback onCallback) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.lay_dialog);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvDetails);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        setRounded(btnOk, Color.WHITE, Color.GRAY);

        setFont(tvMsg, tvTitle);
        tvTitle.setText(title);
        tvMsg.setText(msg);
//        btnOk.setText(Global.MSG_OK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onCallback != null)
                    onCallback.onCall("OK");
            }
        });
        dialog.show();
    }

    //modify view drawable
    public static void setRounded(View v, int backgroundColor, int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{8, 8, 8, 8, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        shape.setStroke(1, borderColor);
        v.setBackgroundDrawable(shape);
    }

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {

        }

    }

    public static MUser getUser() {
        int id = Integer.parseInt(Utils.getPref(Global.USER_ID, "1"));
        int type = Integer.parseInt(Utils.getPref(Global.USER_TYPE, "0"));
        String userName = Utils.getPref(Global.USER, "");
        return new MUser(id, type, userName, "", "");
    }

    public static void log(String msg) {
        Log.e("CANDY", msg);
    }

    public static void log(String msg, String tag) {
        Log.e(tag, msg);
    }

    public static void callFirebase(MPush push) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("push_type", push.getPushType());
        params.put("type", push.getType());
        params.put("title", push.getTitle());
        params.put("message", push.getMsg());
        params.put("to_user", push.getToUser());
        params.put("from_user", push.getFromUser());

        Utils.log(params.toString());
        client.get(Global.API_FIREBASE_PUSH, params, new JsonHttpResponseHandler());

    }

    public static void callForUpdateInfo() {
        HashMap<String, String> params = new HashMap<>();
//        params.put("user_id", "1");
        params.put("user_id", Utils.getPref(Global.USER_ID, "1"));
        params.put("sync_datetime", Utils.getDateTime());
//        params.put("user_type", "2");
        params.put("user_type", Utils.getPref(Global.USER_TYPE, "0"));

        MyLog.e("TEST", "not:" + params.toString());
        CustomRequest reqVideo = new CustomRequest(Request.Method.POST, Global.API_ORDER_NOTIFICATION, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyLog.e("VID", response.toString());
                try {
                    if (response.getString("message").equals("success")) {
                        int userType = Integer.parseInt(Utils.getPref(Global.USER_TYPE, "1"));

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLog.e("TEST", "ERR:" + error.toString());
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(reqVideo);
    }

}
