package com.photowall;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.photowall.adapters.PhotoPagerAdapter;
import com.photowall.photowallcommunity.R;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.viewpagerindicator.TitlePageIndicator.OnCenterItemClickListener;

public class ViewPagerFragment extends Fragment 
implements OnCenterItemClickListener
{

    public static final int PAGE_TYPE_HOME = 0;
    public static final int PAGE_TYPE_ARCHIEVEMENT = 1;
    
    private int pageType = PAGE_TYPE_HOME;
    PhotoHomePageActivity pageActivity = null;
    
    
    public ViewPagerFragment(){};
//    public ViewPagerFragment(int type)
//    {
//        pageType = type;
//    };
    
    @Override
    public void setArguments(Bundle args) {
    	
    	pageType = args.getInt("pagetype");
    	super.setArguments(args);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.i("myfragment","onCreateView...");
        if(getActivity() instanceof PhotoHomePageActivity)
        {
            pageActivity = (PhotoHomePageActivity) getActivity();
        }
//        if(pageActivity == null) return null;
        
        View view = inflater.inflate(R.layout.viewpager_fragment, container,false);
        
      //Set the pager with an adapter
        ViewPager pager = (ViewPager)view.findViewById(R.id.viewpager);
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(pageActivity.getSupportFragmentManager(), getString(R.string.indicator_new), getString(R.string.indicator_hot));
        pager.setAdapter(adapter);
        
        //Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator)view.findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);
        titleIndicator.setFooterIndicatorStyle(IndicatorStyle.None);
        titleIndicator.setFooterLineHeight(0);
        //titleIndicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        titleIndicator.setOnCenterItemClickListener(this);
        titleIndicator.setBackgroundColor(Color.WHITE);
        //titleIndicator.setFooterColor(Color.rgb(238, 84, 88));
        titleIndicator.notifyDataSetChanged();
        titleIndicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) { }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                case 0:
                    pageActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                default:
                    pageActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                    break;
                }
            }

        });
        pager.setCurrentItem(0);
        adapter.notifyDataSetChanged();
        return view;
    }
    

    
    
    @Override
    public void onCenterItemClick(int position) {
        
    }
}
