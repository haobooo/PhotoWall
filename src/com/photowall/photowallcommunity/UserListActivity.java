package com.photowall.photowallcommunity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.photowall.adapters.UserListAdapter;
import com.photowall.bean.ArchievementPostBean;
import com.photowall.bean.PhotoWallTools;
import com.photowall.demos.TestDataGenerators;
import com.photowall.list.ContentListElement;
import com.photowall.list.CustomerListAdapter;
import com.photowall.list.ListElement;
import com.photowall.net.ErrCode;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;


public class UserListActivity extends Activity implements OnClickListener{
    private ListView mListView;
    protected View mListHeadView = null;
    private List<ArchievementPostBean> mlist = new ArrayList<ArchievementPostBean>();
    private CustomerListAdapter adapter;
    private ImageView mBackImageView;
    private ImageView mMenuImageView;
    
    
    private final int MSG_LOGIN_FAILED = 0x01;
    private final int MSG_LOGIN_SUCCESS = 0x02;
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;

    private final int MSG_REFRESH_DATA = 1000;
    
    private Handler mainHandler = new Handler(){
        
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            
                case MSG_REFRESH_DATA:
                  
                    adapter.notifyDataSetChanged();
                    break;

            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_list);
        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        initHeadView();
        initView();
        initData();
    }
    
    private void initView(){
        mBackImageView = (ImageView) findViewById(R.id.ul_set);
        mBackImageView.setOnClickListener(this);
        
        mMenuImageView = (ImageView) findViewById(R.id.ul_menu);
        mMenuImageView.setOnClickListener(this);
        
        mListView = (ListView) findViewById(R.id.ul_listview);
//        mListView.addHeaderView(mListHeadView);
        adapter = new CustomerListAdapter(getApplicationContext());
        adapter.addSectionHeaderItem("Friend/Challenging/Accomplished/Liked/etc");
//        ArrayList<ListElement> elements = new ArrayList<ListElement>();
//        for (int i = 0; i < 5; i++) {
//            ContentListElement element = new ContentListElement();
//            element.setTitle("zhangsan","comment");
//            elements.add(element);
//        }
        ArrayList<ListElement> elements = TestDataGenerators.getUserList();
        adapter.addList(elements);
        adapter.addSectionHeaderItem("Follow friends from facebook");
        elements = new ArrayList<ListElement>();
        for (int i = 0; i < 5; i++) {
            ContentListElement element = new ContentListElement();
            element.setTitle("zhangsan","comment");
            elements.add(element);
        }
        adapter.addList(elements);
        adapter.addSectionHeaderItem("Invite friends from facebook");
        elements = new ArrayList<ListElement>();
        for (int i = 0; i < 5; i++) {
            ContentListElement element = new ContentListElement();
            element.setTitle("zhangsan","comment");
            elements.add(element);
        }
        adapter.addList(elements);
        mListView.setAdapter(adapter);
    }
    
    private void initHeadView(){    
        if(mListHeadView==null){
            mListHeadView=getLayoutInflater().inflate(R.layout.user_info, null);
        }
    }
    
    public void initData(){
        
        new Thread(new Runnable() {

            @Override
            public void run() {

                Map<String, String> params = new HashMap<String, String>();
                boolean flag = httpSession.getUserFriends(HttpSession.FRIEND_LIST,params);
                if (!flag) {
                    err = httpSession.getErrCode();
                    mainHandler.sendEmptyMessage(MSG_LOGIN_FAILED);
                } else {
                    userContent = httpSession.getUserContent();
                    httpSession.setUserContent(userContent);
                    mainHandler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
                }

                List<ArchievementPostBean> templist = TestDataGenerators
                        .getArchievementPostBeans(30);
                if (templist != null && templist.size() > 0) {
                    mlist.clear();
                    mlist.addAll(templist);
                }
                mainHandler.sendEmptyMessage(MSG_REFRESH_DATA);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ul_set:
            finish();
            break;
        case R.id.ul_menu:
            boolean isMark[] = {false,false,false,false,true,true,true,true};
            PhotoWallTools.showPopupWindow(UserListActivity.this, mMenuImageView, 300, 500, isMark);
            break;

        default:
            break;
        }
    }
}