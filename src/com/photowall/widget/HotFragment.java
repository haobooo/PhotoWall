package com.photowall.widget;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.photowall.adapters.HomeGridViewAdapter;
import com.photowall.baseclass.ImageInfo;
import com.photowall.demos.TestDataGenerators;
import com.photowall.net.HomePostInfo;
import com.photowall.photowallcommunity.R;

public final class HotFragment extends Fragment implements OnItemClickListener{
    private static final String KEY_CONTENT = "TestFragment";
    private HomeGridViewAdapter mHomeGridViewAdapter;
    private GridView mGridView;
    private View mView;
    private static List<ImageInfo> mList = null;
    private static String mTitleMarkString;
    public static List<HomePostInfo> mHomePostInfos = new ArrayList<HomePostInfo>();

    public static HotFragment newInstance(String content) {
        HotFragment fragment = new HotFragment();

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
        mList = TestDataGenerators.getHotArchievement();
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
    
    public void refresh(){
        if (mHomeGridViewAdapter != null ) {
            mList = TestDataGenerators.getHotArchievement();
            mHomeGridViewAdapter.notifyDataSetChanged();
        }
    }
    
    public interface OnArticleSelectedListener {
        public void onArticleSelected();
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
        mGridView = (GridView) mView.findViewById(R.id.hot_gridView);
        mGridView.setAdapter(mHomeGridViewAdapter);
        mGridView.setOnItemClickListener(this);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        mHomeGridViewAdapter.setPosition(position);
    }
}
