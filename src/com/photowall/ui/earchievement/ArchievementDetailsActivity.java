package com.photowall.ui.earchievement;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.photowall.adapters.HelpFragmentAdapter;
import com.photowall.photowallcommunity.CreateActivity;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;
import com.photowall.ui.post.CheckinActivity;
import com.photowall.ui.post.PostAstoryActivity;
import com.photowall.ui.post.PostObject;
import com.photowall.ui.post.PostStoryDetailActivity;
import com.photowall.widget.ui.DialogUtils;
import com.photowall.widget.ui.ScrollGridView;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class ArchievementDetailsActivity extends Activity
implements View.OnClickListener
{
	
	private ImageView backImage;
	private ImageView settingsImage;
	
	private ViewGroup guide_layer;
	
	
	private StickyScrollView  ScrollView;
	private ImageView btn_post;
	private ImageView btn_checkin;
	
	protected ViewPager mPager;
    protected PageIndicator mIndicator;
	private ArrayList<ArchievementHeadInfo> mlist = new ArrayList<ArchievementHeadInfo>();
    private ArchievementHeadPagerAdater adater;
    
    private ArrayList<PostObject> postlist = new ArrayList<PostObject>();
    private ArchievePostGridAdapter postadAdapter ;
    private ScrollGridView postgrid;
    
    public static final int MSG_POST_PHOTO = 1000;
    public static final int MSG_POST_SOTRY = 1001;
    
    private Handler mainhander = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case 0:
				ScrollView.scrollTo(0, 0);
				
				break;
			case MSG_POST_SOTRY:
			{
				Intent intent = new Intent(ArchievementDetailsActivity.this,PostAstoryActivity.class);
				startActivity(intent);
				break;
			}
			case MSG_POST_PHOTO:
			{
				Intent intent = new Intent(ArchievementDetailsActivity.this,CreateActivity.class);
				startActivity(intent);
				break;
			}
			default:
				break;
			}
    	};
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.archievement_details_layout);
		initview();
		
	}
	
	public void initview()
	{
		//test{
		for(int i=0;i<3;i++)
		{
			mlist.add(new ArchievementHeadInfo());
		}
		for(int i=0;i<18;i++)
		{
			postlist.add(new PostObject());
		}
		//}
		
			
		backImage = (ImageView) findViewById(R.id.title_back);
		backImage.setOnClickListener(this);
		
		
		settingsImage = (ImageView) findViewById(R.id.title_settings);
		settingsImage.setOnClickListener(this);
			
			btn_checkin = (ImageView) findViewById(R.id.btn_checkin);
			btn_checkin.setOnClickListener(this);
			btn_post = (ImageView) findViewById(R.id.btn_post);
			btn_post.setOnClickListener(this);
		
			ScrollView = (StickyScrollView) findViewById(R.id.ScrollView);
		    mPager = (ViewPager)findViewById(R.id.help_pager);
//		    adater= new ArchievementHeadPagerAdater(mlist, this);
		    
		    LayoutInflater inflater= LayoutInflater.from(this);
		    listViews.add(inflater.inflate(R.layout.archievement_head_info_layout,null));
		    listViews.add(inflater.inflate(R.layout.archievement_head_info_layout,null));
		    listViews.add(inflater.inflate(R.layout.archievement_head_info_layout,null));
		    
		    mPager.setAdapter(new MyPagerAdapter(listViews));
		    mPager.setCurrentItem(0);
		    if(mPager.getAdapter() == null)
            {
                throw new  IllegalArgumentException("ArchievementDetailsActivity" +
                        " adapter not null");
            }
               
		    
	        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.help_indicator);
	        mIndicator = indicator;
	        indicator.setViewPager(mPager);
	        
	        
//	        indicator.setBackgroundColor(0x00000000);
	        
	        
	        //post grid
	        postgrid = (ScrollGridView) findViewById(R.id.postgrid);
	        postgrid.setMainhaHandler(mainhander);
	        postadAdapter = new ArchievePostGridAdapter(postlist, this);
	        postgrid.setAdapter(postadAdapter);
	        postadAdapter.notifyDataSetChanged();
	        
	        postgrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent = new Intent(ArchievementDetailsActivity.this, PostStoryDetailActivity.class);
					startActivity(intent);
				}
	        	
	        });
	        
	        //guide
	        guide_layer = (ViewGroup) findViewById(R.id.guide_layer);
	        if(PhotoWallApplication.getPhotoWallApplication().isGuideMode())
	        {
	        	guide_layer.setVisibility(View.VISIBLE);
	        	guide_layer.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ArchievementDetailsActivity.this,CreateActivity.class);
						startActivity(intent);
					}
				});
	        }
	        else
	        {
	        	guide_layer.setVisibility(View.GONE);
	        }
	        
	        //scrollview
	        
	        
	        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if( !PhotoWallApplication.getPhotoWallApplication().isGuideMode() )
        {
        	guide_layer.setVisibility(View.GONE);
        }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_post:
			DialogUtils.showPostSelectDialog(ArchievementDetailsActivity.this, 
					btn_post, mainhander);
			break;
		case R.id.btn_checkin:
		{
			Intent intent = new Intent(ArchievementDetailsActivity.this,CheckinActivity.class);
			startActivity(intent);
			break;
		}
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
		default:
			break;
		}
	}
	public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
	
	public void showArchieveDetail(View view) {
		Intent intent = new Intent(this, ArchievementMoreDetailsActivity.class);
		startActivity(intent);
	}
	
	/************************************************
	 * 
	 * Viewpager adapter
	 * 
	 * ********************************************
	 */
	private ArrayList<View> listViews = new ArrayList<View>();

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
}
