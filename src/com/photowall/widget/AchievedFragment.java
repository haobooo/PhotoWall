package com.photowall.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.photowall.adapters.AchievedGridViewAdapter;
import com.photowall.baseclass.ImageInfo;
import com.photowall.baseclass.PostInfo;
import com.photowall.baseclass.ProfileInfo;
import com.photowall.demos.TestDataGenerators;
import com.photowall.net.HomePostInfo;
import com.photowall.photowallcommunity.AchievementsInfoActivity;
import com.photowall.photowallcommunity.R;

public final class AchievedFragment extends Fragment implements OnClickListener,
    OnItemClickListener{
    private static final String KEY_CONTENT = "TestFragment";
    private AchievedGridViewAdapter mAchievedGridViewAdapter;
    private GridView mGridView;
    private Button mBackButton;
    private View mView;
    private static List<ProfileInfo> mList = null;
    private static String mTitleMarkString;
    private static String mContentString;
    public static List<HomePostInfo> mHomePostInfos = new ArrayList<HomePostInfo>();

    public static AchievedFragment newInstance(String content,String title) {
        AchievedFragment fragment = new AchievedFragment();

        mContentString = content;
        mTitleMarkString = title;
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 20; i++) {
//            builder.append(content).append(" ");
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        fragment.mContent = builder.toString();

        return fragment;
    }
    
    public void initData() {
        if ("own".equals(mTitleMarkString)) {
            mList = TestDataGenerators.getOwnProfileInfos();
        }
        
        if ("other".equals(mTitleMarkString)) {
            mList = TestDataGenerators.getOtherProfileInfos();
        }
//        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//        for (int i = 0; i < 20; i++) {
//            ImageInfo imageInfo = new ImageInfo("username", str, "New York City");
//            mList.add(imageInfo);
//        }
        mAchievedGridViewAdapter = new AchievedGridViewAdapter(getActivity(),
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
//        mBackButton = (Button) mView.findViewById(R.id.hot_button);
        mGridView.setOnItemClickListener(this);
        mGridView.setAdapter(mAchievedGridViewAdapter);
        mBackButton.setOnClickListener(this);
        if(mGridView.getCount() == 0){
            mBackButton.setVisibility(View.VISIBLE);
        }else{
            mBackButton.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//        case R.id.hot_button:
//            getActivity().finish();
//            break;

        default:
            break;
        }
    }
    
    

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        
//        ProfileInfo profileInfo = mList.get(pos);
//        ArrayList<PostInfo> list1 = new ArrayList<PostInfo>();
//        
//        
//        if ("own".equals(mTitleMarkString)) {
//            PostInfo postInfo = new PostInfo();
//            postInfo.setPiUserName("zhangsansan");
////            postInfo.setPiBitmap(profileInfo.getImageBitmap());
//            postInfo.setComment("this is a test3");
//            postInfo.setOtherComment(0+" comments");
//            postInfo.setLikeNum("200");
//            postInfo.setTime("20 min ago");
//            list1.add(postInfo);
//        }
//        
//        if ("other".equals(mTitleMarkString)) {
//            for (int i = 0; i < 20; i++) {
//                PostInfo postInfo = new PostInfo();
//                postInfo.setPiUserName("zhangsansan");
////                postInfo.setPiBitmap(profileInfo.getImageBitmap());
//                postInfo.setComment("this is a test3");
//                postInfo.setOtherComment(0+" comments");
//                postInfo.setLikeNum("200");
//                postInfo.setTime("20 min ago");
//                list1.add(postInfo);
//            }
//        }
//        
//        ImageInfo imageInfo = new ImageInfo();
//        imageInfo.setImageInfo(profileInfo.getAchievementInfo());
////        imageInfo.setImageBitmap(profileInfo.getImageBitmap());
//        imageInfo.setImageMark(profileInfo.getAchievementMark());
//        imageInfo.setLocal(profileInfo.getLocation());
//        imageInfo.setUserName(mTitleMarkString);
//        imageInfo.setImageTitle(profileInfo.getAchievementTitle());
//        imageInfo.setPostInfos(list1);
//        
//        
//        Intent imageIntent = new Intent();
//        imageIntent.setClass(getActivity(), AchievementsInfoActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("imageInfo", imageInfo);
//        imageIntent.putExtras(bundle);
//        getActivity().startActivity(imageIntent);
    }
}
