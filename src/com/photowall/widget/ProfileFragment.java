package com.photowall.widget;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.photowall.adapters.ProfileGridView;
import com.photowall.baseclass.ImageInfo;
import com.photowall.baseclass.PostInfo;
import com.photowall.baseclass.ProfileInfo;
import com.photowall.bean.PhotoWallTools;
import com.photowall.demos.TestDataGenerators;
import com.photowall.net.AchmInfo;
import com.photowall.net.ErrCode;
import com.photowall.net.HomePostInfo;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.photowallcommunity.AchievementsInfoActivity;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.R;
import com.photowall.photowallcommunity.SignUpActivity;
import com.photowall.photowallcommunity.SuccessActivity;
import com.photowall.ui.ArchievementPostsActivity;

public final class ProfileFragment extends Fragment implements OnItemClickListener{
    private static final String KEY_CONTENT = "TestFragment";
    private ProfileGridView mAchmGridViewAdapter;
    private GridView mGridView;
    private View mView;
    private List<ProfileInfo> mList = null;
    private static String mTitleMarkString;
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    public static List<AchmInfo> mAchmInfos = new ArrayList<AchmInfo>();
    private List<HomePostInfo> mHomePostInfos;
    public AchmInfo mAchmInfo = new AchmInfo();
    private int mSelect=0;
    
    private final int MSG_GET_DATA_FAILED = 0x01;
    private final int MSG_GET_DATA_SUCCESS = 0x02;
    
    public ProgressDialog mProgressDialog;
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
            
                case MSG_GET_DATA_FAILED:
                { 
                    String text="";
                    try {
                        if (err.getErrcode()!=null && err.getError()!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "服务器无响应";
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    break;
                }
                case MSG_GET_DATA_SUCCESS:
                { 
                    String text="获取成功";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    Intent imageIntent = new Intent();
                    imageIntent.setClass(getActivity(), AchievementsInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("imageInfo", mAchmInfo);
                    imageIntent.putExtras(bundle);
                    imageIntent.putExtra("achid", mAchmInfos.get(mSelect).getAchid());
                    getActivity().startActivity(imageIntent);
                    break;
                }
            }
        };
    };
    
    public static ProfileFragment newInstance(String content) {
        ProfileFragment fragment = new ProfileFragment();

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
//        mList = TestDataGenerators.getNewArchievement();
//        getData();
//        mList = TestDataGenerators.getOtherProfileInfos();
//        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//        for (int i = 0; i < 20; i++) {
//            ImageInfo imageInfo = new ImageInfo("username", str, "New York City");
//            mList.add(imageInfo);
//        }
    }

    private String mContent = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("正在获取数据...");
        mView = inflater.inflate(R.layout.hot_fragment_gridview, null);
        return mView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView = (GridView) mView.findViewById(R.id.hot_gridView);
        mAchmGridViewAdapter = new ProfileGridView(getActivity(),
                mAchmInfos,R.layout.home_grid_view);
        mGridView.setAdapter(mAchmGridViewAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
//        getAchiPost(pos);
        PhotoWallApplication.getPhotoWallApplication().setCurrAchminfo(mAchmInfos.get(pos));
        //by black crystal
        Intent intent = new Intent(getActivity(),ArchievementPostsActivity.class);
        startActivity(intent);
    }
    
    
    public void getAchiPost(final int pos)
    {
        mProgressDialog.show();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userid",mAchmInfos.get(pos).getUserid());
                        params.put("achid",mAchmInfos.get(pos).getAchid());
                        params.put("start", "0");
                        params.put("offset", "10");
                        
                       boolean flag =  httpSession.getAchiPost(HttpSession.POST_LIST_BY_ACHID, params);
                        
                       if(!flag)
                       {
                          err =  httpSession.getErrCode();
                          mainHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                       } else {
                           mAchmInfo = mAchmInfos.get(pos);
                           mHomePostInfos=httpSession.getmHomePostInfos();
                           mAchmInfo.setHomePostInfos(mHomePostInfos);
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_SUCCESS);
                       }
                    }
                }
                ).start();
    }
}
