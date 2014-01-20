package com.photowall.ui.explore;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.photowall.adapters.PhotoPagerAdapter;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.viewpagerindicator.TitlePageIndicator.OnCenterItemClickListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HotPostsFramgmentActivity extends FragmentActivity
implements OnCenterItemClickListener, View.OnClickListener
{
	private ImageView settingsImage;
	private ImageView backImage;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.title_viewpager_fragment);
		initviews();
		
	}
	
	public void initviews()
	{
		settingsImage = (ImageView) findViewById(R.id.title_settings);
		settingsImage.setOnClickListener(this);
		
		backImage = (ImageView) findViewById(R.id.title_back);
		backImage.setOnClickListener(this);
		
		TextView title = (TextView) findViewById(R.id.title_text);
		title.setText("HOT POSTS");
		
		//Set the pager with an adapter
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        HotPostPhotoPagerAdapter adapter = new HotPostPhotoPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        
        //Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);
        titleIndicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        titleIndicator.setOnCenterItemClickListener(this);
        titleIndicator.setBackgroundColor(Color.WHITE);
        titleIndicator.notifyDataSetChanged();
        titleIndicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) { }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageSelected(int position) {
            }

        });
        pager.setCurrentItem(0);
        adapter.notifyDataSetChanged();
	}

	@Override
	public void onCenterItemClick(int position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.title_back:
			{
				finish();
				
				break;
			}
			case R.id.title_settings:
			{
				settings();
				break;
			}
		}
	}
	public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
}
