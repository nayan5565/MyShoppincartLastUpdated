package com.swapnopuri.shopping.cart.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.model.MAds;
import com.swapnopuri.shopping.cart.my.tools.AdsManager;
import com.swapnopuri.shopping.cart.my.tools.DBManager;
import com.swapnopuri.shopping.cart.my.tools.Global;
import com.swapnopuri.shopping.cart.my.tools.MyApp;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by JEWEL on 9/23/2016.
 */

public class ActivityVideoPlayer extends AppCompatActivity implements View.OnTouchListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private View controller;
    private Runnable runControll, runTimeTracker;
    private LinearLayout layHeader;
    private Handler handler;
    private SeekBar seekBar;
    private TextView tvTimeTotal, tvTimeRunning, tvTitle;
    private ImageView imgPlayPause, imgBack;
    private String path, title;
    private int recipeId;

    public static void start(Context context, String title, String path,int recipeId) {
        context.startActivity(new Intent(context, ActivityVideoPlayer.class).putExtra("path", path)
                .putExtra("title", title).putExtra("productId",recipeId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.lay_video_player2);

        if (getIntent().hasExtra("path"))
            path = getIntent().getStringExtra("path");

        if (getIntent().hasExtra("title"))
            title = getIntent().getStringExtra("title");
        if (getIntent().hasExtra("title"))
            recipeId = getIntent().getIntExtra("productId",0);

        MyApp.getInstance().setupAnalytics("Video Player");

        controller = findViewById(R.id.controller);
        layHeader = (LinearLayout) findViewById(R.id.layHeader);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.getProgressDrawable().setColorFilter(Color.parseColor(Global.COLOR_MAIN), PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        imgPlayPause = (ImageView) findViewById(R.id.imgPlayPause);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        tvTimeRunning = (TextView) findViewById(R.id.tvTimeRuning);
        tvTimeTotal = (TextView) findViewById(R.id.tvTimeTotal);
        tvTitle = (TextView) findViewById(R.id.tvTitle);


        controller.setVisibility(View.VISIBLE);
        layHeader.setVisibility(View.VISIBLE);


        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceView.setOnTouchListener(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        surfaceHolder.addCallback(this);
        seekBar.setOnSeekBarChangeListener(this);
        imgPlayPause.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        fullScreen();


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();


        } catch (Exception e) {
            Log.e("TEST", e.toString());
        }

        handler = new Handler();
        runTimeTracker = new Runnable() {
            @Override
            public void run() {
                updateCounter();
                handler.postDelayed(this, 200);
            }
        };
        handler.postDelayed(runTimeTracker, 200);

        if (title != null)
            tvTitle.setText(title);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        handler.removeCallbacks(runTimeTracker);
        handler = null;
        if (mediaPlayer != null) {
            mediaPlayer.setDisplay(null);
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    private void showControll() {
        if (runControll == null) {
            runControll = new Runnable() {
                @Override
                public void run() {
                    controller.setVisibility(View.INVISIBLE);
                    layHeader.setVisibility(View.INVISIBLE);
                    imgPlayPause.setVisibility(View.INVISIBLE);
                }
            };
        }
        controller.removeCallbacks(runControll);
        controller.setVisibility(View.VISIBLE);
        layHeader.setVisibility(View.VISIBLE);
        imgPlayPause.setVisibility(View.VISIBLE);
        controller.postDelayed(runControll, 2000);
    }


    private void fullScreen() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        controller.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int w = size.x;
        int h = size.y;

        lp.width = w;
        lp.height = h;
        surfaceView.setLayoutParams(lp);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            showControll();
        }
        return false;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        seekBar.setMax(mp.getDuration());
        tvTimeTotal.setText("" + mp.getDuration());
        int total = mp.getDuration();

        total = total / 1000;
        long s = total % 60;
        long m = (total / 60) % 60;
        long h = (total / (60 * 60)) % 24;
        if (h > 0) {
            tvTimeTotal.setText(String.format(Locale.US, "%d:%02d:%02d", h, m, s));
        } else {
            tvTimeTotal.setText(String.format(Locale.US, "%02d:%02d", m, s));
        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e("SEEK", "progress:" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e("SEEK", "seek touch start");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e("SEEK", "seek touch end");
        mediaPlayer.seekTo(seekBar.getProgress());
    }

    private void updateCounter() {
        if (mediaPlayer == null) return;

        int elapsed = mediaPlayer.getCurrentPosition();
        // getCurrentPosition is a little bit buggy :(
        if (elapsed > 0 && elapsed < mediaPlayer.getDuration()) {
            seekBar.setProgress(elapsed);

            elapsed = Math.round(elapsed / 1000.f);
            long s = elapsed % 60;
            long m = (elapsed / 60) % 60;
            long h = (elapsed / (60 * 60)) % 24;

            if (h > 0)
                tvTimeRunning.setText(String.format(Locale.US, "%d:%02d:%02d", h, m, s));
            else
                tvTimeRunning.setText(String.format(Locale.US, "%02d:%02d", m, s));
        }
    }

    private void playPause() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imgPlayPause.setImageResource(R.drawable.play);
        } else {
            mediaPlayer.start();
            imgPlayPause.setImageResource(R.drawable.pause);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showAd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlayPause:
                playPause();
                break;
            case R.id.imgBack:
                showAd();
                stop();
                finish();
                break;
        }
    }

    private void stop() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
    private void showAd() {

        ArrayList<MAds> adList = DBManager.getInstance().getList("select * from " + DBManager.TABLE_ADS + " where productId='" + recipeId + "'", new MAds());
        if (adList != null && adList.size() > 0) {
            if (adList.size() < Global.LIMIT_VIDEO_INTERSTITAL) {
                AdsManager.getInstance(this).showInterstisial();
                DBManager.getInstance().addData(DBManager.TABLE_ADS,new MAds(recipeId),"");
            }
        }else{
            AdsManager.getInstance(this).showInterstisial();
            DBManager.getInstance().addData(DBManager.TABLE_ADS,new MAds(recipeId),"");
        }
    }
}
