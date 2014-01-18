package com.photowall.photowallcommunity;

import java.util.ArrayList;
import java.util.List;

import com.photowall.bean.PhotoWallTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


public class FeedBackActivity extends Activity implements OnClickListener{
	
	
    private WebView mFeedBackView;
    private ImageView mBackImageView;
    private TextView mTitleTextView;
    private ImageView mMenuImageView;
    private List<String> mMenulist;
    private String title[] = { "Search","Help","Report an issue","Setting" };
    private Handler mHandler = new Handler();   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feed_back);
        initView();
        setTitle();
    }
    
    private void initView() {
       
		
    	mBackImageView = (ImageView) findViewById(R.id.back_image);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mMenuImageView = (ImageView) findViewById(R.id.menu_image);
        
        mMenulist = new ArrayList<String>();
        for (int i = 0; i < title.length; i++) {
            mMenulist.add(title[i]);
        }
        
        mBackImageView.setOnClickListener(this);
        mMenuImageView.setOnClickListener(this);
        
        
        mFeedBackView = (WebView) findViewById(R.id.feed_back_web);
        WebSettings webSettings = mFeedBackView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mFeedBackView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mFeedBackView
                .loadUrl("http://ilegend.uservoice.com/forums/234337-general");
    }
    
    private void setTitle(){
        mTitleTextView.setText("Feedback");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.back_image:
            finish();
            break;
//        case R.id.menu_image:
//            boolean isMark[] = {false,false,false,false,true,true,true,true};
//            PhotoWallTools.showPopupWindow(FeedBackActivity.this, mMenuImageView, 300, 500, isMark);
//            break;
        case R.id.menu_image:
		{
			settings();
			break;
		}
        default:
            break;
        }
    }
    public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
}