package com.photowall.ui.earchievement;

import java.util.ArrayList;

import com.photowall.photowallcommunity.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArchievementHeadPagerAdater extends PagerAdapter {

	private ArrayList<ArchievementHeadInfo> mlist;
	private Context mContext;
	private LayoutInflater inflater;
	
	public ArchievementHeadPagerAdater(ArrayList<ArchievementHeadInfo> mlist,
			Context mContext) {
		this.mlist = mlist;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		View v = inflater.inflate(R.layout.archievement_head_info_layout,null);
//		container.addView(container,position);
//		return v; 
//	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeViewAt(position);
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.archievement_head_info_layout,null);
		((ViewPager)container).addView(v);
		return v;
	}
}
