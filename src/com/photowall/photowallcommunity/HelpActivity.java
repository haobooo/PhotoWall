package com.photowall.photowallcommunity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.photowall.adapters.HelpFragmentAdapter;
import com.photowall.bean.PhotoWallTools;
import com.photowall.widget.HelpBaseActivity;
import com.viewpagerindicator.CirclePageIndicator;



public class HelpActivity extends HelpBaseActivity implements OnClickListener{
    private ImageView mMenuImageView;
    private ImageView mBackImageView;
    private TextView mTitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.help);
        
        initView();
        mAdapter = new HelpFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.help_pager);
        mPager.setAdapter(mAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.help_indicator);
        mIndicator = indicator;
        indicator.setViewPager(mPager);
        
        final float density = getResources().getDisplayMetrics().density;
        indicator.setBackgroundColor(0xFFCCCCCC);
        indicator.setRadius(10 * density);
        indicator.setPageColor(0x880000FF);
        indicator.setFillColor(0xFF888888);
        indicator.setStrokeColor(0xFF000000);
        indicator.setStrokeWidth(2 * density);
        setTitle();
    }
    
    private void initView() {
        mMenuImageView = (ImageView) findViewById(R.id.menu_image);
        mMenuImageView.setOnClickListener(this);
        mBackImageView = (ImageView) findViewById(R.id.back_image);
        mBackImageView.setOnClickListener(this);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
    }

    private void setTitle(){
        mTitleTextView.setText("Help");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.menu_image:
            boolean isMark[] = {false,false,false,false,true,false,true,true};;
            PhotoWallTools.showPopupWindow(HelpActivity.this, mMenuImageView, 300, 500, isMark);
            break;
        case R.id.back_image:
            finish();
            break;

        default:
            break;
        }
    }
}