package com.photowall.widget;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.photowall.adapters.HelpGridViewAdapter;
import com.photowall.adapters.HomeGridViewAdapter;
import com.photowall.baseclass.ImageInfo;
import com.photowall.photowallcommunity.R;

public final class HelpFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private HelpGridViewAdapter mHelpGridViewAdapter;
    private GridView mGridView;
    private View mView;
    private List<ImageInfo> mList = null;

    public static HelpFragment newInstance(String content) {
        HelpFragment fragment = new HelpFragment();

//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 20; i++) {
//            builder.append(content).append(" ");
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        fragment.mContent = builder.toString();

        return fragment;
    }
    
    public void initData() {
        mList = new ArrayList<ImageInfo>();
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        for (int i = 0; i < 20; i++) {
            ImageInfo imageInfo = new ImageInfo("username", str, "New York City");
            mList.add(imageInfo);
        }
        mHelpGridViewAdapter = new HelpGridViewAdapter(getActivity(),
                mList,R.layout.home_grid_view);
    }

    private String mContent = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.bg_guide_5);
        imageView.setScaleType(ScaleType.FIT_XY);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.addView(imageView);

        return layout;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
