package com.photowall.ui.explore;

import java.util.ArrayList;

import com.photowall.NewFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class HotPostPhotoPagerAdapter extends FragmentStatePagerAdapter {
	
	private ArrayList<Fragment> mFragments;
    private String[] titles = {
            "◆ NEW","◆ HOT"
    };
	
	public HotPostPhotoPagerAdapter(FragmentManager fm) {
		super(fm);
		 mFragments = new ArrayList<Fragment>();
       
       PostNewFragment newf = new PostNewFragment();
       Bundle bundle = new Bundle();
       bundle.putString("title", titles[0]);
       newf.setArguments(bundle);
       
       PostHotFragment hotf = new PostHotFragment();
       Bundle bundle2 = new Bundle();
       bundle2.putString("title", titles[1]);
       hotf.setArguments(bundle2);
       
       mFragments.add(newf);
       mFragments.add(hotf);
	}
	public int getItemPosition(Object object) {
        return POSITION_NONE;
  }
	@Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
    	Log.i("myfragment","Fragment getItem:"+position);
        return mFragments.get(position);
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
        
    }

}
