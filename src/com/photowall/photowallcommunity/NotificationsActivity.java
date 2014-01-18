package com.photowall.photowallcommunity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.photowall.adapters.NotfAdapter;
import com.photowall.bean.ArchievementPostBean;
import com.photowall.bean.PhotoWallTools;
import com.photowall.demos.TestDataGenerators;


public class NotificationsActivity extends Activity implements OnClickListener{
    private ListView mNotfListView;
    private List<ArchievementPostBean> mlist = new ArrayList<ArchievementPostBean>();
    private NotfAdapter mAdapter;
    private ImageView mMenuImageView;
    private ImageView mBackImageView;
    private TextView mTitleTextView;
    
    private final int MSG_REFRESH_DATA = 1000;
    
    private Handler mainHandler = new Handler(){
        
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            
                case MSG_REFRESH_DATA:
                  
                    mAdapter.notifyDataSetChanged();
                    break;

            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.notifications);
        initView();
        initData();
        setTitle();
    }
    
    private void initView() {
        mMenuImageView = (ImageView) findViewById(R.id.menu_image);
        mMenuImageView.setOnClickListener(this);
        mBackImageView = (ImageView) findViewById(R.id.back_image);
        mBackImageView.setOnClickListener(this);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        
        mNotfListView = (ListView) findViewById(R.id.notf_info_list);
        mAdapter = new NotfAdapter(getApplicationContext(), mlist);
        mNotfListView.setAdapter(mAdapter);
    }
    
    private void setTitle(){
        mTitleTextView.setText("Notifications");
    }
    
    public void initData(){
        
        new Thread(new Runnable() {
            
            @Override
            public void run() { 
                
                List<ArchievementPostBean> templist = TestDataGenerators.getArchievementPostBeans(30);
                if(templist != null && templist.size()>0)
                {
                    mlist.clear();
                    mlist.addAll(templist);
                }
                mainHandler.sendEmptyMessage(MSG_REFRESH_DATA);
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.menu_image:
            boolean isMark[] = {false,false,false,false,true,true,true,true};;
            PhotoWallTools.showPopupWindow(NotificationsActivity.this, mMenuImageView, 300, 500, isMark);
            break;
        case R.id.back_image:
            finish();
            break;

        default:
            break;
        }
    }
}
