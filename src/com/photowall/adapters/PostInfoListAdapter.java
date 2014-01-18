package com.photowall.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.photowall.bean.ArchievementPostBean;
import com.photowall.photowallcommunity.R;



public class PostInfoListAdapter extends BaseAdapter implements OnClickListener{

    private List<ArchievementPostBean> list;
    private LayoutInflater mInflater;
    private Context mContext;
    public  PostInfoListAdapter(Context context,List<ArchievementPostBean> mList) {
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
            convertView = mInflater.inflate(R.layout.post_info_list_item, null);
            holder.userName = (TextView) convertView.findViewById(R.id.pi_name);
            holder.userImage = (ImageView) convertView.findViewById(R.id.pi_user_picture);
            holder.comment = (TextView) convertView.findViewById(R.id.pi_other_comment);
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
        TextView userName; 
        TextView comment;
    }


    @Override
    public void onClick(View arg0) {
        
    }
}