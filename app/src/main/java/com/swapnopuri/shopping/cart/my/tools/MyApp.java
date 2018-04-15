package com.swapnopuri.shopping.cart.my.tools;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.swapnopuri.shopping.cart.my.model.MRecipe;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class MyApp extends Application {
    private static MyApp instance;
    private static ArrayList<MRecipe>recipes;
    private static Tracker mTracker;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public Context getContext(){
        return instance.getApplicationContext();
    }

    public ArrayList<MRecipe>getRecipes(){
        if(recipes==null)
            recipes=DBManager.getInstance().getData(DBManager.TABLE_RECEIPE,new MRecipe());
        return recipes;
    }

    public void setRecipes(ArrayList<MRecipe> _recipes) {
        recipes = _recipes;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(Global.ANALYTICS_ID);
        }
        return mTracker;
    }
    public void setupAnalytics(String tag) {

        Tracker mTracker = getDefaultTracker();
        mTracker.setScreenName(tag);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


}
