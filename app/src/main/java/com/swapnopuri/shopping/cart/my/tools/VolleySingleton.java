package com.swapnopuri.shopping.cart.my.tools;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by JEWEL on 7/14/2016.
 */
public class VolleySingleton {private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApp.getInstance().getContext());
        imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String,Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                MyLog.e("IMAGELOADERR",url);
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);

            }
        });
    }

    public static VolleySingleton getInstance() {
        if(instance==null)
            instance=new VolleySingleton();
        return instance;

    }
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}