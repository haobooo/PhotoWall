package com.photowall.bean;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncVideoItemImageLoader {

    private static String TAG = "AsyncImageLoader";
    private HashMap<String, SoftReference<Drawable>> imageCache;
    private static Context mContext;

    public AsyncVideoItemImageLoader(Context context) {
        if (imageCache != null && !imageCache.isEmpty()) {
            imageCache.clear();
        }
        mContext = context;
        imageCache = new HashMap<String, SoftReference<Drawable>>();
        Log.d(TAG, "imageCache=====¹¹Ôìº¯Êý===" + imageCache.size());
    }

    public Drawable loadDrawable(final String imageUrl,
            final ImageCallback imageCallback) {
        Log.d(TAG, "imageCache========" + imageCache.size());
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    public static Drawable loadImageFromUrl(String url) {
        URL m;
        InputStream inputStream = null;
        Drawable d =null;
        Log.d(TAG, "loadImageFromUrl==url=========" + url);
        try {
            m = new URL(url);
            inputStream = (InputStream) m.getContent();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream!=null)
        {
        	 BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();  
             bmpFactoryOptions.inSampleSize = 2; 
             d = Drawable.createFromResourceStream(mContext.getResources(),
                     null, inputStream, "src", bmpFactoryOptions);
        }
       
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return d;
    }

    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable, String imageUrl);
    }

}
