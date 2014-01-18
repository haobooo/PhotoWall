package com.photowall.bean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

public class UpdataBitmap extends AsyncTask<Void, Integer, String[]> {

    private static final String TAG = "UpdataBitmap";
    private ImageView mImageView;
    private String mUrl;
    private Bitmap mBitmap;
    public UpdataBitmap(ImageView img,String url){
        super();
        mImageView = img;
        mUrl = url;
    }
    @Override
    protected String[] doInBackground(Void... arg0) {
        
        mBitmap = returnBitMap(mUrl);
        return null;
    }
    @Override
    protected void onPostExecute(String[] result) {
        if (mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        }
        super.onPostExecute(result);
    }
    
    private Bitmap returnBitMap(String urlString) {
        if (TextUtils.isEmpty(urlString)) {
            return null;
        }
        URL myFileUrl = null;
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
        myFileUrl = new URL(urlString);
        } catch (MalformedURLException e) {
        e.printStackTrace();
        }
        try {
            Log.d(TAG, "-------->myFileUrl="+myFileUrl);
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
        e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        return bitmap; 
    }
}
