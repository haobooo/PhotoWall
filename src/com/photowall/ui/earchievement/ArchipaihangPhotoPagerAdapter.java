package com.photowall.ui.earchievement;

import java.util.ArrayList;

import com.photowall.NewFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class ArchipaihangPhotoPagerAdapter extends FragmentStatePagerAdapter {
	
	private ArrayList<Fragment> mFragments;
    private String[] titles = {
            "Challenging","Archievement"
    };
	
	public ArchipaihangPhotoPagerAdapter(FragmentManager fm) {
		super(fm);
		 mFragments = new ArrayList<Fragment>();
       
       ChallengNewFragment newf = new ChallengNewFragment();
       Bundle bundle = new Bundle();
       bundle.putString("title", titles[0]);
       newf.setArguments(bundle);
       
       MostArchimentNewFragment hotf = new MostArchimentNewFragment();
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
