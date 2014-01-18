package com.photowall.widget;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.photowall.adapters.UserListAdapter;
import com.photowall.baseclass.ImageInfo;
import com.photowall.bean.ArchievementPostBean;
import com.photowall.demos.TestDataGenerators;
import com.photowall.photowallcommunity.R;



public final class UserListFragment extends Fragment {
    private static final String KEY_CONTENT = "UserListFragment";
    private UserListAdapter mUserListAdapter;
    private ListView mListView;;
    private View mView;
    private List<ArchievementPostBean> mList = null;
    private static String mTitleMarkString;
    private RelativeLayout mRelativeLayout;

    public static UserListFragment newInstance(String content) {
        UserListFragment fragment = new UserListFragment();

        mTitleMarkString = content;

        return fragment;
    }
    
    public void initData() {
        mList = new ArrayList<ArchievementPostBean>();
        List<ArchievementPostBean> templist = TestDataGenerators.getArchievementPostBeans(30);
        if(templist != null && templist.size()>0)
        {
            mList.clear();
            mList.addAll(templist);
        }
        mUserListAdapter = new UserListAdapter(getActivity(),mList);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
            mView = inflater.inflate(R.layout.user_list, null);
            return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.user_list_bar);
        mRelativeLayout.setVisibility(View.GONE);
        mListView = (ListView) mView.findViewById(R.id.ul_listview);
        mListView.setAdapter(mUserListAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
