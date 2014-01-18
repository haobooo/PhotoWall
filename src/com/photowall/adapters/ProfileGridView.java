package com.photowall.adapters;


import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.photowall.bean.AsyncVideoItemImageLoader;
import com.photowall.bean.AsyncVideoItemImageLoader.ImageCallback;
import com.photowall.bean.PhotoWallTools;
import com.photowall.lazyload.ImageLoader;
import com.photowall.net.AchmInfo;
import com.photowall.photowallcommunity.AchievementsInfoActivity;
import com.photowall.photowallcommunity.ProfileActivity;
import com.photowall.photowallcommunity.R;
import com.photowall.tools.ImageDownloadTask;


public class ProfileGridView extends BaseAdapter implements OnClickListener{

	
	public static String selectType = "";
	private final int GET_IMG= 10;
    private List<AchmInfo> list;
    private LayoutInflater mInflater;
    private int resource;
    private Context mContext;
    private AsyncVideoItemImageLoader asyncImageLoader;
    private HashMap<String, Drawable> ret;
    
  //by black crystal
    public ImageLoader imageLoader; 
    
    public  ProfileGridView(Context context,List<AchmInfo> mList,int resource) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = mList;
        this.resource = resource;
        this.mContext = context;
        asyncImageLoader= new AsyncVideoItemImageLoader(context);
        setData();
        
        imageLoader=new ImageLoader(context);
    }
    public void setData() {
        ret = new HashMap<String, Drawable>();
        String[] mSpinnerArrayStr = mContext.getResources().getStringArray(R.array.photo_classify_name);
        TypedArray images = mContext.getResources().obtainTypedArray(R.array.photo_classify_drawable_mark);
        for (int i = 0; i < mSpinnerArrayStr.length; i++) {
            ret.put(mSpinnerArrayStr[i], images.getDrawable(i));
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        final GridHolder holder;
        
        if (convertView == null) {
            holder = new GridHolder(); 
            convertView = mInflater.inflate(R.layout.achieve_gridview_item, null);
            holder.achiLayout = (LinearLayout) convertView.findViewById(R.id.ag_achievement_layout);
            holder.achieveImage = (ImageView) convertView.findViewById(R.id.ag_image);
            holder.achievement_name = (TextView) convertView.findViewById(R.id.ag_achievement_name);
            holder.achieved_num = (TextView) convertView.findViewById(R.id.ag_achieved_text_num);
            holder.challenging_num = (TextView) convertView.findViewById(R.id.ag_challenging_text_num);
            holder.achieveUserIcon = (ImageView) convertView.findViewById(R.id.ag_achievement_pic);
            convertView.setTag(holder); 
        }else{
            holder = (GridHolder) convertView.getTag();
        }
        
        //by black crystal
        String imageUrl =  list.get(index).getAchiimg();
        ImageView imageView = holder.achieveImage; 
//        if(imageView.getTag(R.id.img_key)==null)
//        {
//        	imageView.setTag(R.id.img_key, imageUrl);
//	        ImageDownloadTask imgtask =new ImageDownloadTask(); 
//	        imgtask.setDisplayWidth(200); 
//	        imgtask.setDisplayHeight(400); 
//	        imgtask.execute(imageUrl,imageView);
//        }
        
        imageLoader.DisplayImage(imageUrl, imageView);
        
        String typestr = list.get(index).getLsname();
        if(typestr == null)
        {
        	typestr = selectType;
        }
        holder.achiLayout.setBackgroundDrawable(ret.get(typestr));
        holder.achievement_name.setText(list.get(index).getAchiname());
        holder.achieved_num.setText(list.get(index).getAchiviewcount());
        holder.challenging_num.setText(list.get(index).getAchifollowcount());
        
        return convertView;
    }
    Bitmap bm = null;
    public Bitmap getData(final int index)
    {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   byte[] data;
                try {
                    data = PhotoWallTools.getImage(list.get(index).getAchiimg());
                    Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) {
                        bm = bitmap;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               }
           }).start();
           return bm;
       }
    
    public class GridHolder {
        LinearLayout achiLayout;
        ImageView achieveUserIcon;
        ImageView achieveImage;
        TextView achievement_name; 
        TextView achieved_num;
        TextView challenging_num;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ht_user_iamge:
            Intent userIntent = new Intent();
            userIntent.setClass(mContext, ProfileActivity.class);
            ((Activity)mContext).startActivity(userIntent);
            break;
            
        case R.id.ht_image:
             Intent imageIntent = new Intent();
             imageIntent.setClass(mContext, AchievementsInfoActivity.class);
             ((Activity)mContext).startActivity(imageIntent);
            break;

        default:
            break;
        }
    }
}