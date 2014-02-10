package com.photowall.adapters;

import java.util.ArrayList;

import com.photowall.NewFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
	
	private ArrayList<Fragment> mFragments;
//    private String[] titles = {
//            "● New","● Hot"
//    };
	private String[] titles = new String[2];
	
	public PhotoPagerAdapter(FragmentManager fm, String titleLeft, String titleRight) {
		super(fm);
		mFragments = new ArrayList<Fragment>();
		
       titles[0] = titleLeft;
       titles[1] = titleRight;
       
       NewFragment newf = new NewFragment();
       Bundle bundle = new Bundle();
       bundle.putString("title", titleLeft);
       newf.setArguments(bundle);
       
       NewFragment hotf = new NewFragment();
       Bundle bundle2 = new Bundle();
       bundle2.putString("title", titleRight);
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
