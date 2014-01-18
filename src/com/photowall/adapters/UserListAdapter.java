package com.photowall.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photowall.bean.ArchievementPostBean;
import com.photowall.photowallcommunity.R;



public class UserListAdapter extends BaseAdapter implements OnClickListener{

    private List<ArchievementPostBean> list;
    private LayoutInflater mInflater;
    private int resource;
    private Context mContext;
    public  UserListAdapter(Context context,List<ArchievementPostBean> mList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = mList;
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

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        GridHolder holder;
        if (convertView == null) {
            holder = new GridHolder(); 
            convertView = mInflater.inflate(R.layout.user_list_item, null);
            holder.userName = (TextView) convertView.findViewById(R.id.ul_name);
            holder.followMark = (Button) convertView.findViewById(R.id.ul_iamge_mark);
            holder.userImage = (ImageView) convertView.findViewById(R.id.ul_user_image);
            holder.comment = (TextView) convertView.findViewById(R.id.ul_says);
            convertView.setTag(holder); 
        }else{  
            holder = (GridHolder) convertView.getTag();
        }
        ArchievementPostBean bean = list.get(index);

        holder.userName.setText(bean.getUsername());
        holder.comment.setText(bean.getCommentString());
        
        
        return convertView;
    }
    
    
    public class GridHolder {
        ImageView userImage;
        Button followMark;
        TextView userName; 
        TextView imageInfo;
        TextView comment;
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        
    }
}