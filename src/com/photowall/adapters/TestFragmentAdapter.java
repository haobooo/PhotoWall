package com.photowall.adapters;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.photowall.baseclass.ImageInfo;
import com.photowall.demos.TestDataGenerators;
import com.photowall.photowallcommunity.R;
import com.photowall.widget.HotFragment;
import com.photowall.widget.NewFragment;
import com.photowall.widget.TestFragment;
import com.photowall.widget.UserListFragment;
import com.viewpagerindicator.IconPagerAdapter;

public class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private String[] CONTENT;
    protected static final int[] ICONS = new int[] {
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location
    };
    private List<ImageInfo> mList = null;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private int mCount;

    public TestFragmentAdapter(FragmentManager fm, String[] Content, ArrayList<Fragment> fragments) {
        super(fm);
        this.CONTENT = Content;
        mCount = CONTENT.length;
        this.fm = fm;
        this.fragments=fragments;
    }
    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    
    public FragmentManager getFragmentManager(){
        return fm;
    }
    
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
//        if ("User".equals(CONTENT[position])) {
//            return UserListFragment.newInstance(CONTENT[position % CONTENT.length]);
//        }
//        if ("New".equals(CONTENT[position])) {
//            mList = TestDataGenerators.getNewArchievement();
//            return NewFragment.newInstance(CONTENT[position % CONTENT.length]);
//        }else if ("Hot".equals(CONTENT[position])) {
//            mList = TestDataGenerators.getHotArchievement();
//            return HotFragment.newInstance(CONTENT[position % CONTENT.length]);
//        }
//        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        if(this.fragments != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragments){
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft=null;
            fm.executePendingTransactions();
        }else {
            return;
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return fragments.size();
    }

//    @Override
//    public int getCount() {
//        return mCount;
//    }

    @Override  
    public int getItemPosition(Object object) {  
        return POSITION_NONE;  
    }  
    
    @Override
    public CharSequence getPageTitle(int position) {
      return CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}