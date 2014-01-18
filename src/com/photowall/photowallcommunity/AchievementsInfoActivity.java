package com.photowall.photowallcommunity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.photowall.adapters.ArchievenmentPostsAdapter;
import com.photowall.baseclass.ImageInfo;
import com.photowall.baseclass.PostInfo;
import com.photowall.bean.AsyncVideoItemImageLoader;
import com.photowall.bean.PhotoWallTools;
import com.photowall.bean.AsyncVideoItemImageLoader.ImageCallback;
import com.photowall.net.AchmInfo;
import com.photowall.net.HomePostInfo;


public class AchievementsInfoActivity extends Activity implements OnItemClickListener, OnClickListener{
    private ListView mListView;
    private List<PostInfo> mlist = new ArrayList<PostInfo>();
    private ArchievenmentPostsAdapter adapter;
    private LayoutInflater layoutInflater;
    private TextView mCategoryText;
    private TextView mAchmTitleText;
    private TextView mDescriptionText;
    private ImageView mBackImageView;
    private ImageView mAchievedImageView;
    private ImageView mMenuImageView;
    private ImageView mAchmImageView;
    private Button mAchieveButton;
    private Button mChallengeButton;
    private List<HomePostInfo> templist;
    private static int POST_RESULT;
    private String title[] = { "Share","Search","Help","Report an issue","Setting" };
    private List<String> mMenulist;
    public AchmInfo mAchmInfo;
    private String mAchid;
    
    private AsyncVideoItemImageLoader asyncImageLoader;
    
    private final int MSG_REFRESH_DATA = 1000;
    
    private Handler mainHandler = new Handler(){
        
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            
                case MSG_REFRESH_DATA:
                  
                    adapter.notifyDataSetChanged();
//                    new Utility().setListViewHeightBasedOnChildren(mListView);
                    break;

            }
        };
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements);
        initData();
        initView();
    }

    public void initView(){
        asyncImageLoader = new AsyncVideoItemImageLoader(getApplicationContext());
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = layoutInflater.inflate(R.layout.achievement_list_head, null);
        
        mListView = (ListView)findViewById(R.id.achm_list);
        mListView.addHeaderView(headView);
        adapter = new ArchievenmentPostsAdapter(this, templist, 0);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mAchievedImageView = (ImageView) findViewById(R.id.achm_achieved);
        mAchievedImageView.setOnClickListener(this);
        mMenuImageView = (ImageView) findViewById(R.id.achm_menu);
        mMenuImageView.setOnClickListener(this);
        
        mBackImageView = (ImageView) findViewById(R.id.achm_back);
        mBackImageView.setOnClickListener(this);
        mAchieveButton = (Button) headView.findViewById(R.id.achm_btn4);
        mAchieveButton.setOnClickListener(this);
        mChallengeButton = (Button) findViewById(R.id.achm_btn5);
        mChallengeButton.setOnClickListener(this);
        mCategoryText = (TextView) headView.findViewById(R.id.achm_category_text);
        mAchmTitleText = (TextView) headView.findViewById(R.id.achm_title_text);
        mDescriptionText = (TextView) headView.findViewById(R.id.achm_description_text);
        mAchmImageView = (ImageView) headView.findViewById(R.id.achm_cover_show);
        Intent intent = getIntent();
//        ImageInfo imageInfo = (ImageInfo) intent.getExtras().getSerializable("imageInfo");
        mCategoryText.setText(mAchmInfo.getLsname());
        mAchmTitleText.setText(mAchmInfo.getAchiname());
        mDescriptionText.setText(mAchmInfo.getAchides());
        
        String imageUrl = mAchmInfo.getAchiimg();
        
        ImageView imageView = mAchmImageView; //这样imageview没有和listview联系起来，导致后续listview.findviewwithTag方法失效
        imageView.setTag(imageUrl);  
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() { 
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) mAchmImageView.findViewWithTag(imageUrl);  
                if (imageViewByTag != null) {  
                    imageViewByTag.setImageDrawable(imageDrawable);  
                }  
            }  
        });
        if (null!=cachedImage) {
            imageView.setImageDrawable(cachedImage);  
        } else {
        }
        
        mMenulist = new ArrayList<String>();
        for (int i = 0; i < title.length; i++) {
            mMenulist.add(title[i]);
        }
    }
    
    public void initData(){
        Intent intent = getIntent();
        mAchmInfo = (AchmInfo) intent.getExtras().getSerializable("imageInfo");
        templist = mAchmInfo.getHomePostInfos();
        mAchid = intent.getStringExtra("achid");
         
        
//        if(templist != null && templist.size()>0)
//        {
//            mlist.clear();
//            mlist.addAll(templist);
//        }
        
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() { 
//                
////                List<ArchievementPostBean> templist = TestDataGenerators.getArchievementPostBeans(30);
//                Intent intent = getIntent();
//                ImageInfo imageInfo = (ImageInfo) intent.getExtras().getSerializable("imageInfo");
//                List<PostInfo> templist = imageInfo.getPostInfos();
//                
//                if(templist != null && templist.size()>0)
//                {
//                    mlist.clear();
//                    mlist.addAll(templist);
//                }
//                mainHandler.sendEmptyMessage(MSG_REFRESH_DATA);
//            }
//        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
//        Intent postInfoIntent = new Intent();
//        postInfoIntent.setClass(AchievementsInfoActivity.this, PostInfoActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("postinfo", mlist.get(pos-1));
//        postInfoIntent.putExtras(bundle);
//        startActivity(postInfoIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.achm_back:
            finish();
            break;
        case R.id.achm_btn4:
            Intent achieveIntent = new Intent();
            achieveIntent.setClass(AchievementsInfoActivity.this, CreateActivity.class);
            achieveIntent.putExtra("title", "Achieve");
            achieveIntent.putExtra("achid", mAchid);
            startActivityForResult(achieveIntent, POST_RESULT);
            break;
        case R.id.achm_btn5:
//            finish();
            break;
        case R.id.achm_menu:
            boolean isMark[] = {true,false,false,false,true,true,true,true};
            PhotoWallTools.showPopupWindow(AchievementsInfoActivity.this, mMenuImageView, 300, 500, isMark);
            break;
        default:
            break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == POST_RESULT) {
                adapter.notifyDataSetChanged();
//                PostInfo postInfo = (PostInfo) data.getExtras().getSerializable("postinfo");
//                mlist.clear();
//                templist.add(0, postInfo);
//                mlist.addAll(templist);
//                adapter.notifyDataSetChanged();
            }
        }
    }
    
    
}