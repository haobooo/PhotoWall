package com.photowall.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.photowall.baseclass.ImageInfo;
import com.photowall.net.HomePostInfo;
import com.photowall.photowallcommunity.AchievementsInfoActivity;
import com.photowall.photowallcommunity.ProfileActivity;
import com.photowall.photowallcommunity.R;



public class HelpGridViewAdapter extends BaseAdapter implements OnClickListener{

    private List<ImageInfo> list;
    private LayoutInflater mInflater;
    private int resource;
    private Context mContext;
    private GridHolder holder;
    private int mPosition;
   
    public  HelpGridViewAdapter(Context context,List<ImageInfo> mList,int resource) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = mList;
        this.resource = resource;
        this.mContext = context;
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
    
    public int getPosition() {
        return mPosition;
    }
    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }
    
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        
        if (convertView == null) {
            holder = new GridHolder(); 
            convertView = mInflater.inflate(resource, null);
            holder.image = (ImageView) convertView.findViewById(R.id.ht_image);
            holder.userName = (TextView) convertView.findViewById(R.id.ht_user_name_text);
            holder.imageInfo = (TextView) convertView.findViewById(R.id.ht_info_text);
            holder.local = (TextView) convertView.findViewById(R.id.ht_location_text);
            holder.achieveImage = (ImageView) convertView.findViewById(R.id.ht_image);
            holder.userImage = (ImageView) convertView.findViewById(R.id.ht_user_iamge);
            convertView.setTag(holder); 
        }else{  
            holder = (GridHolder) convertView.getTag();
        }
        
        if (list.get(index).getImageBitmap() != null) {
            holder.image.setImageBitmap(list.get(index).getImageBitmap());
        }
       
        
        holder.userName.setText(list.get(index).getUserName());
        holder.imageInfo.setText(list.get(index).getImageInfo());
        holder.local.setText(list.get(index).getLocal());
        holder.achieveImage.setOnClickListener(new OnBtnClickListener(list.get(index)));
        holder.userImage.setOnClickListener(this);
        return convertView;
    }
    
    
    public class GridHolder {
        ImageView image;
        ImageView userImage;
        ImageView achieveImage;
        TextView userName; 
        TextView imageInfo;
        TextView local;
    }
    
    class OnBtnClickListener implements OnClickListener {
        private ImageInfo imageInfo;
        public OnBtnClickListener (ImageInfo imageInfo){
            this.imageInfo = imageInfo; 
        }
        public void onClick(View v) {
            Intent imageIntent = new Intent();
            imageIntent.setClass(mContext, AchievementsInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageInfo", imageInfo);
            imageIntent.putExtras(bundle);
            ((Activity)mContext).startActivity(imageIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ht_user_iamge:
            Intent userIntent = new Intent();
            userIntent.setClass(mContext, ProfileActivity.class);
            userIntent.putExtra("title_mark", "other");
            userIntent.putExtra("user_name", holder.userName.getText().toString());
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
