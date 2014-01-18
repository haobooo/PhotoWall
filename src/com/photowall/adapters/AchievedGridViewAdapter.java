package com.photowall.adapters;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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
import com.photowall.net.HomePostInfo;
import com.photowall.photowallcommunity.AchievementsInfoActivity;
import com.photowall.photowallcommunity.ProfileActivity;
import com.photowall.photowallcommunity.R;



public class AchievedGridViewAdapter extends BaseAdapter implements OnClickListener{

    private List<HomePostInfo> list;
    private LayoutInflater mInflater;
    private int resource;
    private Context mContext;
    private AsyncVideoItemImageLoader asyncImageLoader;
    private HashMap<String, Drawable> ret;
    public  AchievedGridViewAdapter(Context context,List<HomePostInfo> list,int resource) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.resource = resource;
        this.mContext = context;
        this.asyncImageLoader = new AsyncVideoItemImageLoader(context);
        setData();
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
            
            convertView.setTag(holder); 
        }else{
            holder = (GridHolder) convertView.getTag();
        }
        
        String imageUrl =  list.get(index).getPimgname();
        ImageView imageView = holder.achieveImage; //这样imageview没有和listview联系起来，导致后续listview.findviewwithTag方法失效
        imageView.setTag(imageUrl);  
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() { 
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) holder.achieveImage.findViewWithTag(imageUrl);  
                if (imageViewByTag != null) {  
                    imageViewByTag.setImageDrawable(imageDrawable);  
                }  
            }  
        });  
        if (null!=cachedImage) {
            imageView.setImageDrawable(cachedImage);  
        } else {
        }
//        if (list.get(index).getImageBitmap() != null) {
//            holder.achieveImage.setImageBitmap(list.get(index).getImageBitmap());
//        }
        String string = list.get(index).getLifestylename();
        holder.achiLayout.setBackgroundDrawable(ret.get(string));
        holder.achievement_name.setText(list.get(index).getAchiname());
        holder.achieved_num.setText(list.get(index).getPlikecount());
        holder.challenging_num.setText(list.get(index).getPcommentcount());
        return convertView;
    }
    
    
    public class GridHolder {
        LinearLayout achiLayout;
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