package com.photowall.photowallcommunity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.photowall.adapters.PostInfoListAdapter;
import com.photowall.baseclass.PostInfo;
import com.photowall.bean.ArchievementPostBean;
import com.photowall.bean.PhotoWallTools;
import com.photowall.demos.TestDataGenerators;



public class PostInfoActivity extends Activity implements OnClickListener{
    
    private ListView mCommentListView;
    private List<ArchievementPostBean> mlist = new ArrayList<ArchievementPostBean>();
    private PostInfoListAdapter mAdapter;
    private TextView mUserNameTextView;
    private TextView mTimeTextView;
    private TextView mCommentTextView;
    private ImageView mPostPicView;
    private TextView mLikeNumTextView;
    private TextView mCommentsNumTextView;
    private ImageView mBackImageView;
    private ImageView mAchieveImageView;
    private ImageView mShareImageView;
    private ImageView mMenuImageView;
    private LayoutInflater layoutInflater;
    private Button mPostBtn;
    private EditText mCommentEditText;
    
    private final int MSG_REFRESH_DATA = 1000;
    
   private Handler mainHandler = new Handler(){
        
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            
                case MSG_REFRESH_DATA:
                  
                    mAdapter.notifyDataSetChanged();
                    break;

            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.post_info);
        initView();
        initData();
    }
    private void initView() {
        Intent intent = getIntent();
        PostInfo postInfo = (PostInfo) intent.getExtras().getSerializable("postinfo");
        postInfo.getPiUserName();
        
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = layoutInflater.inflate(R.layout.post_list_head, null);
        
        
        mCommentListView = (ListView) findViewById(R.id.pi_commit_list);
        mCommentListView.addHeaderView(headView);
        
        mAdapter = new PostInfoListAdapter(getApplicationContext(), mlist);
        mCommentListView.setAdapter(mAdapter);
        mBackImageView = (ImageView) findViewById(R.id.pi_back);
        mAchieveImageView = (ImageView) findViewById(R.id.pi_achieve);
        mShareImageView = (ImageView) findViewById(R.id.pi_share_image);
        mBackImageView.setOnClickListener(this);
        mAchieveImageView.setOnClickListener(this);
        mShareImageView.setOnClickListener(this);
        mUserNameTextView = (TextView) findViewById(R.id.pi_user_name);
        mTimeTextView = (TextView) findViewById(R.id.pi_time_ago);
        mCommentTextView = (TextView) findViewById( R.id.pi_comment_text);
        mLikeNumTextView = (TextView) findViewById(R.id.pi_like_num);
        mCommentsNumTextView = (TextView) findViewById(R.id.pi_comment_num);
        mPostPicView = (ImageView) findViewById(R.id.pi_post_image);
        mPostPicView.setImageBitmap(TestDataGenerators.PIbitmap);
        mUserNameTextView.setText(postInfo.getPiUserName());
        mTimeTextView.setText(postInfo.getTime());
        mCommentTextView.setText(postInfo.getComment());
        mLikeNumTextView.setText(postInfo.getLikeNum()+" likes");
        mCommentsNumTextView.setText(postInfo.getOtherComment());
        mMenuImageView = (ImageView) findViewById(R.id.pi_menu);
        mMenuImageView.setOnClickListener(this);
        mPostBtn = (Button) findViewById(R.id.pi_post_btn);
        mPostBtn.setOnClickListener(this);
        mCommentEditText = (EditText) findViewById(R.id.pi_comment);
    }
    
    public void initData(){
        
        new Thread(new Runnable() {
            
            @Override
            public void run() { 
                
                List<ArchievementPostBean> templist = TestDataGenerators.getArchievementPostBeans(30);
                
                if(templist != null && templist.size()>0)
                {
                    mlist.clear();
                    mlist.addAll(templist);
                }
                mainHandler.sendEmptyMessage(MSG_REFRESH_DATA);
            }
        }).start();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.pi_back:
               finish();
            break;
        case R.id.pi_achieve:
               
            break;
        case R.id.pi_share_image:

            break;
        case R.id.pi_menu:
            boolean isMark[] = {true,false,false,false,true,true,true,true};
            PhotoWallTools.showPopupWindow(PostInfoActivity.this, mMenuImageView, 300, 500, isMark);
            break;
        case R.id.pi_post_btn:
            mCommentEditText.getText().toString();
            break;

        default:
            break;
        }
    }
}