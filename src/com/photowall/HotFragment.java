package com.photowall;

import com.photowall.photowallcommunity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HotFragment extends Fragment {

    private String title;
    private View mView;
    
    public HotFragment(){};
    public HotFragment(String title)
    {
        this.title = title;
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        mView = inflater.inflate(R.layout.hot_fragment_gridview, null);
        return mView;
    }
}
