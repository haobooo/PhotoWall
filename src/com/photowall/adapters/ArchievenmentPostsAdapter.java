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

import com.photowall.bean.AsyncVideoItemImageLoader;
import com.photowall.lazyload.ImageLoader;
import com.photowall.net.HomePostInfo;
import com.photowall.photowallcommunity.R;
import com.photowall.tools.ImageDownloadTask;



public class ArchievenmentPostsAdapter extends BaseAdapter{

	public static String selectType = "";
    private List<HomePostInfo> list;
    private LayoutInflater mInflater;
    private int resource;
    private Context mContext;
    private AsyncVideoItemImageLoader asyncImageLoader;
  //by black crystal
    public ImageLoader imageLoader; 
    
    public  ArchievenmentPostsAdapter(Context context,List<HomePostInfo> mList,int resource) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = mList;
        this.resource = resource;
        this.mContext = context;
        this.asyncImageLoader = new AsyncVideoItemImageLoader(context);
        imageLoader=new ImageLoader(context);
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
            convertView = mInflater.inflate(R.layout.achm_list_item, null);
            holder.postlike = (ImageView) convertView.findViewById(R.id.postlike);
            holder.userName = (TextView) convertView.findViewById(R.id.achm_user_name);
            holder.imageInfo = (TextView) convertView.findViewById(R.id.achm_comment_text);
            holder.time = (TextView) convertView.findViewById(R.id.achm_time_text);
            holder.achieveImage = (ImageView) convertView.findViewById(R.id.achm_image);
            holder.comment = (TextView) convertView.findViewById(R.id.achm_comment);
            holder.achm_like_text = (TextView) convertView.findViewById(R.id.achm_like_text);
            convertView.setTag(holder); 
        }else{  
            holder = (GridHolder) convertView.getTag();
        }
        final String pid = list.get(index).getPid();
        final int pos = index;
        holder.postlike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(listener!=null)
				{
					listener.onclickBypostID(pos,pid);
				}
			}
		});
        holder.userName.setText(list.get(index).getUsernickname());
        holder.imageInfo.setText(list.get(index).getPcontent());
        holder.time.setText(list.get(index).getPcreatedate());
        
        //by black crystal
        String imageUrl =  list.get(index).getPimgname();
        ImageView imageView = holder.achieveImage; 
//        ImageDownloadTask imgtask =new ImageDownloadTask(); 
//        imgtask.setDisplayWidth(150); 
//        imgtask.setDisplayHeight(200); 
//        imgtask.execute(imageUrl,imageView);
        
        imageLoader.DisplayImage(imageUrl, imageView);
        
        holder.achm_like_text.setText(list.get(index).getPlikecount()+" likes");
        holder.comment.setText(list.get(index).getPcommentcount()+" comments");
        return convertView;
    }
    
    
    public class GridHolder {  
    	ImageView postlike;
        ImageView userImage;
        ImageView achieveImage;
        TextView userName; 
        TextView imageInfo;
        TextView time;
        TextView comment;
        TextView achm_like_text;
    }

    private LikeClickListener listener;
    public void setLikeListener(LikeClickListener l)
    {
    	this.listener = l;
    }
    public interface LikeClickListener
    {
    	public void onclickBypostID(int pos,String id);
    }
}