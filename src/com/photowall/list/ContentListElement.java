package com.photowall.list;

import com.photowall.photowallcommunity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContentListElement implements ListElement {

    private String userName;
    private String comment;
    
    public void setTitle(String username,String comment) {
        this.userName = username;
        this.comment = comment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_list_item;
    }

    @Override
    public View getViewForListElement(LayoutInflater layoutInflater,
            Context context, View view) {
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(
                getLayoutId(), null);
        TextView username = (TextView) layout.findViewById(R.id.ul_name);
        username.setText(userName);
        TextView userCmt = (TextView) layout.findViewById(R.id.ul_says);
        userCmt.setText(comment);
        return layout;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

}