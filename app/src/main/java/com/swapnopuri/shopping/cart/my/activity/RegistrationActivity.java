package com.swapnopuri.shopping.cart.my.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyLog;
import com.swapnopuri.shopping.cart.my.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NAYAN on 5/3/2017.
 */
public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegis, btnLog;
    ImageView imgBirthDate, imgMale, imgFeMale, imgClose;
    private Toolbar toolbar;
    private int isMale, isFemale;
    private TextView tvMale, tvFemale, tvPhone, tvBirth, tvAge, tvPass, tvEmail, tvFull, tvNic, tvCountry;
    int gender1 = 0;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecoumd;
    private String datePicker;
    GradientDrawable drawable;
    GradientDrawable drawable2;
    String mail, user, surName, pass, phone, age, country;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RegistrationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_new);
        init();
    }


    public void init() {
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvFemale = (TextView) findViewById(R.id.tvFeMale);
        tvMale = (TextView) findViewById(R.id.tvMale);
        tvNic = (TextView) findViewById(R.id.tvNic);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        tvPass = (TextView) findViewById(R.id.tvPass);
        tvFull = (TextView) findViewById(R.id.tvFullName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvBirth = (TextView) findViewById(R.id.tvBirthDate);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        btnRegis = (Button) findViewById(R.id.btnRegistration);
        imgBirthDate = (ImageView) findViewById(R.id.edtBirthDate);
        imgMale = (ImageView) findViewById(R.id.rdMale);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgFeMale = (ImageView) findViewById(R.id.rdFemale);
        imgFeMale.setOnClickListener(this);
        imgMale.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        btnLog = (Button) findViewById(R.id.btnLogin);
        btnLog.setOnClickListener(this);
        btnRegis.setOnClickListener(this);
        imgBirthDate.setOnClickListener(this);
        setSupportActionBar(toolbar);
        drawable = (GradientDrawable) imgMale.getBackground();
        drawable2 = (GradientDrawable) imgFeMale.getBackground();
        Utils.setFont("AvenirNext-Regular", tvAge, tvMale, tvFull, btnRegis, tvNic, tvFemale, tvCountry, tvBirth, tvEmail, tvPass, tvPhone, mTitle);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rdMale:
                if (checked)
                    Toast.makeText(this, "male", Toast.LENGTH_SHORT).show();
                gender1 = 1;
                // Pirates are the best
                break;
            case R.id.rdFemale:
                if (checked)
                    Toast.makeText(this, "female", Toast.LENGTH_SHORT).show();
                gender1 = 2;
                // Ninjas rule
                break;
        }
    }


    private void getRegistraionDataFromOnline() {
        if (!Utils.isInternetOn()) return;
//        String birthDate = getEdtString(R.id.edtBirthDate);
        RadioGroup gender = (RadioGroup) findViewById(R.id.rdGender);
        ImageView image = (ImageView) findViewById(R.id.imgImage);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("fullname", user);
        params.put("sur_name", surName);
        params.put("email", mail);
        params.put("password", pass);
        params.put("gender", gender1);
        params.put("birthDate", datePicker);
        params.put("deviceId", Utils.getDeviceId(this));
        params.put("phone", phone);
        params.put("country", country);
        params.put("age", age);
//        params.put("image", image);
        MyLog.e("response", params.toString());

        client.post(Global.BASE + Global.API_REGISTRATION, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                MyLog.e("response", response.toString());

                try {
                    if (response.has("message") && response.getString("message").equals("success")) {
                        Utils.savePref(Global.USER, mail);

                        Utils.savePref("profile2", Global.STATE_PROFILE2);
                        MyLog.e("state", " save " + Global.STATE_PROFILE2);
                        Utils.savePref(Global.STATE, Global.STATE_SPLASH);
                        String profile = Utils.getPref("profile", Global.STATE_TUT);
                        if (profile.equals(Global.STATE_PROFILE)) {
                            ProfileActivity.start(RegistrationActivity.this);
                        } else {
                            LoginActivity.start(RegistrationActivity.this);
                        }

                        finish();
                    } else {
                        Utils.showToast("This email already exists");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private String getEdtString(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void registration() {
        user = getEdtString(R.id.edtFullName);
        surName = getEdtString(R.id.edtSurName);
        pass = getEdtString(R.id.edtPassWord);
        phone = getEdtString(R.id.edtPhone);
        age = getEdtString(R.id.edtAge);
        country = getEdtString(R.id.edtCountry);
        mail = getEdtString(R.id.edtMail).trim();
        MyLog.e("mail", " : " + mail);
        String emailPattern = "[a-zA-Z0-9._-]+@gmail.com+";
        String yahooPattern = "[a-zA-Z0-9._-]+@Yahoo.com+";

        if (mail == null || mail.length() <= 0 || user == null || user.length() <= 0 || surName == null || surName.length() <= 0 || pass == null || pass.length() <= 0 || country == null || country.length() <= 0) {
            Toast.makeText(getApplicationContext(), "user or pass or mail or surname or country field empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (mail != null && mail.length() > 0) {
                if (mail.matches(emailPattern) || mail.matches(yahooPattern)) {
                    Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                    getRegistraionDataFromOnline();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void getDate() {
        imgBirthDate.setImageResource(R.drawable.ic_calender_select);
//            if (isMale == 0)
//                isMale = 1;
//            if (isMale == 1) {
//
//            } else {
//
//            }
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        imgBirthDate.setImageResource(R.drawable.ic_calender);
                        datePicker = (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Log.e("date", " now " + datePicker);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnRegistration) {

            registration();

        } else if (v.getId() == R.id.edtBirthDate) {
            getDate();
        } else if (v.getId() == R.id.rdMale) {
            gender1 = 1;
            imgMale.setImageResource(R.drawable.ic_male_select);
//            drawable.setColor(Color.YELLOW);
            imgFeMale.setImageResource(R.drawable.ic_female);
//            drawable2.setColor(Color.TRANSPARENT);
//            Utils.showToast("male " + gender1);
        } else if (v.getId() == R.id.rdFemale) {
            gender1 = 2;
//            drawable2.setColor(Color.YELLOW);
            imgFeMale.setImageResource(R.drawable.ic_female_select);
            imgMale.setImageResource(R.drawable.ic_male);
//            drawable.setColor(Color.TRANSPARENT);
//            Utils.showToast("Female " + gender1);
        } else if (v.getId() == R.id.imgClose) {
            finish();
        }
    }
}
