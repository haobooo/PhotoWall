package com.photowall.widget;

import java.util.ArrayList;
import java.util.List;

import com.photowall.adapters.HomeGridViewAdapter;
import com.photowall.baseclass.ImageInfo;
import com.photowall.demos.TestDataGenerators;
import com.photowall.net.AchmInfo;
import com.photowall.net.HomePostInfo;
import com.photowall.photowallcommunity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class NewFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment";
    private HomeGridViewAdapter mHomeGridViewAdapter;
    private GridView mGridView;
    private View mView;
    private List<ImageInfo> mList = null;
    private static String mTitleMarkString;
    public static List<HomePostInfo> mHomePostInfos = new ArrayList<HomePostInfo>();

    public static NewFragment newInstance(String content) {
        NewFragment fragment = new NewFragment();

        mTitleMarkString = content;
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 20; i++) {
//            builder.append(content).append(" ");
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        fragment.mContent = builder.toString();

        return fragment;
    }
    
    public void initData() {
        mList = TestDataGenerators.getNewArchievement();
//        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//        for (int i = 0; i < 20; i++) {
//            ImageInfo imageInfo = new ImageInfo("username", str, "New York City");
//            mList.add(imageInfo);
//        }
        mHomeGridViewAdapter = new HomeGridViewAdapter(getActivity(),
                mHomePostInfos,R.layout.home_grid_view);
        
    }

    private String mContent = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hot_fragment_gridview, null);
        return mView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        mGridView = (GridView) mView.findViewById(R.id.hot_gridView);
        mGridView.setAdapter(mHomeGridViewAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
