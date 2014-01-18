package com.photowall.photowallcommunity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.photowall.adapters.TestFragmentAdapter;
import com.photowall.bean.PhotoWallTools;
import com.photowall.widget.BaseSampleActivity;
import com.photowall.widget.HotFragment;
import com.photowall.widget.NewFragment;
import com.photowall.widget.UserListFragment;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.viewpagerindicator.TitlePageIndicator.OnCenterItemClickListener;



public class SearchActivity extends BaseSampleActivity implements OnCenterItemClickListener,
    OnClickListener{
    private String[] mTitleString;
    private AutoCompleteTextView mSearchView;
    private ImageView mClearImageView;
    private ImageView mMenuImageView;
    private ImageView mBackImageView;
    private TextView mTitleTextView;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        initView();
        setTitle();
    }
    
    private void initView(){
        mTitleString = getResources().getStringArray(R.array.title_string3);
        
        mMenuImageView = (ImageView) findViewById(R.id.menu_image);
        mMenuImageView.setOnClickListener(this);
        mBackImageView = (ImageView) findViewById(R.id.back_image);
        mBackImageView.setOnClickListener(this);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        
        mSearchView = (AutoCompleteTextView) findViewById(R.id.search_auto_com_tv);
        mClearImageView = (ImageView) findViewById(R.id.clear_mark);
        mSearchView.addTextChangedListener(watcher);
        mClearImageView.setOnClickListener(this);
        
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < mTitleString.length; i++) {
            if ("Post".equals(mTitleString[i])){
                fragments.add(NewFragment.newInstance(mTitleString[i]));
            }else if("Achievement".equals(mTitleString[i])){
                fragments.add(HotFragment.newInstance(mTitleString[i]));
            }else if("User".equals(mTitleString[i])){
                fragments.add(UserListFragment.newInstance(mTitleString[i]));
            }
        }
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),mTitleString,fragments);

        mPager = (ViewPager)findViewById(R.id.search_viewpage);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.search_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        indicator.setOnCenterItemClickListener(this);
        indicator.setBackgroundResource(R.drawable.background);
        mIndicator = indicator;
        showSearchImage(mSearchView.getText().toString());
        
        setlistener();
        
    }
    
    public void setlistener()
    {
    	mSearchView.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {  
                    
					search();
					return true;  
                }  
				return false;
			}
		});
    }
    
    public void search()
    {
    	Toast.makeText(this, "searching...", Toast.LENGTH_SHORT).show();
    }
    private void setTitle(){
        mTitleTextView.setText("Search");
    }

    private void showSearchImage(String text){
        if ("".equals(text) && text.trim().equals("")) {
            mClearImageView.setBackgroundResource(R.drawable.abs__ic_search_api_holo_light);
            mClearImageView.setEnabled(false);
        }else{
            mClearImageView.setBackgroundResource(R.drawable.abs__ic_search_api_holo_light);
            mClearImageView.setEnabled(true);
        }
            
    }
    
    
    @Override
    public void onCenterItemClick(int position) {
        
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
            
        }

        @Override
        public void onTextChanged(CharSequence text, int arg1, int arg2,
                int arg3) {
            showSearchImage(text.toString());
        }
        
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.clear_mark:
            mSearchView.setText(null);
            showSearchImage(mSearchView.getText().toString());
            break;
        case R.id.menu_image:
            boolean isMark[] = {false,false,false,false,false,true,true,true};;
            PhotoWallTools.showPopupWindow(SearchActivity.this, mMenuImageView, 300, 500, isMark);
            break;
        case R.id.back_image:
            finish();
            break;

        default:
            break;
        }
    }
    
}