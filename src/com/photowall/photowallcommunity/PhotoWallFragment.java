package com.photowall.photowallcommunity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class PhotoWallFragment extends Fragment{
    
    private static final String TEXT_CHAT = "CHAT";
    
    public PhotoWallFragment newInstance(String chat) {
        final PhotoWallFragment f = new PhotoWallFragment();

        final Bundle args = new Bundle();
        args.putString(TEXT_CHAT, chat);
        f.setArguments(args);
        return f;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_wall_fragment, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv_fragment_text);
        String str = getArguments() != null ? getArguments().getString(TEXT_CHAT) : null;
        if(str != null){
            tv.setText(str);
        }else{
            tv.setText("获取字段出错了，求指导");
        }
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}