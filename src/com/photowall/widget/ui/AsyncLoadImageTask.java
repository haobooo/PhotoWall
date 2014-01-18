package com.photowall.widget.ui;
/**
 * @author yaogang.hao@tct-nj.com
 * This class is asynchronous background task 
 * which is to handle some specific work.
 */
import java.lang.ref.WeakReference;
import java.util.concurrent.ThreadPoolExecutor;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
public class AsyncLoadImageTask extends AsyncTask<Object, Object, Bitmap> {

    private Context mContext;
    private Image mimage;
    private WeakReference<ImageView> mimageViewReference;
    
    //here needs to optimize .
    static{
    	//yaogang.hao
//        ((ThreadPoolExecutor)THREAD_POOL_EXECUTOR).setMaximumPoolSize(1000);
    }
    
    public AsyncLoadImageTask(Context context,Image image,ImageView imageview)
    {
        this.mContext = context;
        this.mimage = image;
        this.mimageViewReference = new WeakReference<ImageView>(imageview);
    }
    public static int getThreadPoolSize()
    {
    	//yaogang.hao
//        return ((ThreadPoolExecutor)THREAD_POOL_EXECUTOR).getActiveCount();
    	return 1000;
    }
    @Override
    protected Bitmap doInBackground(Object... params) {
        
      Bitmap thumbNail = MediaStore.Images.Thumbnails.getThumbnail(
              mContext.getContentResolver(), 
              mimage.getId(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
      
        return thumbNail;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mimage.setBitmap(result);
        if(mimageViewReference != null){
            ImageView imageview = mimageViewReference.get();
            if(imageview != null)
                imageview.setImageBitmap(result);
        }
    }
}
