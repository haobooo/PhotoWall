package com.photowall.photowallcommunity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.photowall.adapters.ArchievementGridView;
import com.photowall.adapters.ProfileGridView;
import com.photowall.adapters.TestFragmentAdapter;
import com.photowall.bean.PhotoWallTools;
import com.photowall.demos.TestDataGenerators;
import com.photowall.net.AchmInfo;
import com.photowall.net.ErrCode;
import com.photowall.net.HomePostInfo;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.widget.AchievedFragment;
import com.photowall.widget.AchmFragment;
import com.photowall.widget.BaseSampleActivity;
import com.photowall.widget.HotFragment;
import com.photowall.widget.NewFragment;
import com.photowall.widget.ProfileFragment;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.viewpagerindicator.TitlePageIndicator.OnCenterItemClickListener;



public class ProfileActivity extends BaseSampleActivity implements OnCenterItemClickListener,
    OnItemSelectedListener, OnClickListener{
	
    private String[] mTitleString;
    private Spinner mSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;
    private String[] mSpinnerArrayStr;
    private ImageView mBackImageView;
    private ImageView mCreateImageView;
    private Button mAddFindButton;
    private LinearLayout mFollowLayout;
    private String mTitleMark;
    private TextView mUserNameTextView;
    private TextView mCommentTextView;
    private String mUserName;
    private boolean isOwn = false;
    private ImageView mMenuImageView;
    private boolean[] checkedItems = {true, false};
    private static int CREATE_RESULT = 1000;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private TypedArray images;
    
    private List<AchmInfo> mAchmInfos = new ArrayList<AchmInfo>();
    private TextView id_tap_types;
    private String[] classifyIDs;
    
    
    private final int MSG_GET_DATA_FAILED = 0x01;
    private final int MSG_GET_DATA_SUCCESS = 0x02;
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;
    public ProgressDialog mProgressDialog;
    private List<HomePostInfo> mHomePostInfos = new ArrayList<HomePostInfo>();
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
            
                case MSG_GET_DATA_FAILED:
                { 
                	if(mProgressDialog.isShowing())
                	{
                		mProgressDialog.dismiss();
                	}
                    String text="";
                    try {
                        if (err.getErrcode()!=null&& err.getError()!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "服务器无响应";
                        }
                        
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(ProfileActivity.this, text, Toast.LENGTH_SHORT).show();
                    break;
                }
                
                case MSG_GET_DATA_SUCCESS:
                { 
                	if(mProgressDialog.isShowing())
                	{
                		mProgressDialog.dismiss();
                	}
                    String text="获取成功";
                    Toast.makeText(ProfileActivity.this, text, Toast.LENGTH_SHORT).show();
                    mAdapter.setFragments(fragments);
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        };
    };
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile);
        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.tip_geting_data));
        initView();
        pagerIndicator();
        getData();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
       
    }
    
    private void initView(){
        Intent intent = getIntent();
        mTitleString = getResources().getStringArray(R.array.title_string2);
        classifyIDs = getResources().getStringArray(R.array.photo_classify_id);
        
        mBackImageView = (ImageView) findViewById(R.id.pf_back);
        mBackImageView.setOnClickListener(this);
        mCreateImageView = (ImageView) findViewById(R.id.pf_creat);
        mCreateImageView.setOnClickListener(this);
        mAddFindButton = (Button) findViewById(R.id.pf_find_friends_text);
        mAddFindButton.setOnClickListener(this);
        mFollowLayout = (LinearLayout) findViewById(R.id.pf_follow_friends_text);
        mFollowLayout.setOnClickListener(this);
        mUserNameTextView = (TextView) findViewById(R.id.pf_user_name_text);
        mCommentTextView = (TextView) findViewById(R.id.pf_self_comment_text);
        mMenuImageView = (ImageView) findViewById(R.id.pf_menu);
        mMenuImageView.setOnClickListener(this);
        
        
        mTitleMark = intent.getStringExtra("title_mark");
        mUserName = intent.getStringExtra("user_name");
        mUserNameTextView.setText(mUserName);
        if ("own".equals(mTitleMark)) {
            isOwn = true;
            mFollowLayout.setVisibility(View.GONE);
            mAddFindButton.setVisibility(View.VISIBLE);
        }else if ("other".equals(mTitleMark)) {
            isOwn = false;
            mFollowLayout.setVisibility(View.VISIBLE);
            mAddFindButton.setVisibility(View.GONE);
        }
        
        mSpinnerArrayStr = getResources().getStringArray(R.array.photo_classify);
        mSpinner = (Spinner) findViewById(R.id.pf_spinnered);
        images = getResources().obtainTypedArray(R.array.photo_classify_drawable_all);
        mSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_item,mSpinnerArrayStr){
            @Override  
            public View getDropDownView(int position, View convertView, ViewGroup parent) {  
                if(convertView==null){  
                    convertView = getLayoutInflater().inflate(R.layout.spinner_dropdown_item, parent, false);  
                }  
                TextView label = (TextView) convertView.findViewById(R.id.label);  
                label.setText(getItem(position));  
                ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
                Drawable drawable = images.getDrawable(position);
                imageView.setImageDrawable(drawable);
                return convertView;  
            }  
        };
        mSpinnerAdapter.setDropDownViewResource( android.R.layout.preference_category);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
        
        id_tap_types = (TextView) findViewById(R.id.id_tap_types);
        id_tap_types.setOnClickListener(this);
        id_tap_types.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				mSpinner.onTouchEvent(event);
				return false;
			}
		});
    }
    public void pagerIndicator()
    {
        
//        for (int i = 0; i < mTitleString.length; i++) {
//            if ("Achieved".equals(mTitleString[i])){
//                fragments.add(AchievedFragment.newInstance(mTitleString[i],mTitleMark));
//            }else if("Challenging".equals(mTitleString[i])){
//                fragments.add(AchievedFragment.newInstance(mTitleString[i],mTitleMark));
//            }
//        }
    	
    	  for (int i = 0; i < mTitleString.length; i++) {
              if ("Achieved".equals(mTitleString[i])){
                  fragments.add(ProfileFragment.newInstance(mTitleString[i]));
              }else if("Challenging".equals(mTitleString[i])){
                  fragments.add(ProfileFragment.newInstance(mTitleString[i]));
              }
          }
        
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),mTitleString,fragments);

        mPager = (ViewPager)findViewById(R.id.pf_view_page);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        indicator.setOnCenterItemClickListener(this);
        indicator.setBackgroundResource(R.drawable.background);
        mIndicator = indicator;
    }

    @Override
    public void onCenterItemClick(int position) {
        
    }
    //Spinner事件处理
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
            long arg3) {
    	
    	id_tap_types.setText(mSpinnerArrayStr[pos]);
    	ProfileGridView.selectType = mSpinnerArrayStr[pos];
    	
    	if(pos == 0)
    	{
    		getData();
    	}
    	else
    	{
    		String classid = classifyIDs[pos-1];
    		getDataByLifeSytleID(classid);
    	}
    
        
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.pf_back:
            finish();
            break;
        case R.id.pf_creat:
            Intent creteIntent = new Intent();
            creteIntent.setClass(ProfileActivity.this, CreateActivity.class);
            startActivityForResult(creteIntent, CREATE_RESULT);
            break;
        case R.id.pf_find_friends_text:
            showDialog();
            break;
        case R.id.pf_follow_friends_text:
            TestDataGenerators.setUserList(mUserNameTextView.getText().toString(),
                    mCommentTextView.getText().toString());
            break;
        case R.id.pf_menu:
            boolean[] isMark = {false,false,false,false,true,true,true,true};
            if (isOwn) {
                isMark[1] = true;
            }
            PhotoWallTools.showPopupWindow(ProfileActivity.this, mMenuImageView, 300, 500, isMark);
            break;

        default:
            break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CREATE_RESULT) {
                mAdapter.setFragments(fragments);
                mAdapter.notifyDataSetChanged();
            }
        }
        
    }
    
    private void showDialog() {
        new AlertDialog.Builder(this).setTitle("Find your friends")
                .setItems(new String[] { "on FaceBook", "on Twitter" ,"via Contacts"}, // 列表内容
                        new DialogInterface.OnClickListener() { // 点击监听
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) { // 点击位置
                                if (which == 0) {
                                    showLinkDialog();
                                }else{
                                    Intent userListIntent = new Intent();
                                    userListIntent.setClass(ProfileActivity.this, UserListActivity.class);
                                    startActivity(userListIntent);
                                }
                                
                                Toast.makeText(ProfileActivity.this,
                                        "点击了第" + (which + 1) + "项", 0).show();
                            }
                        }).show();
    }

    private void showLinkDialog() {
        new AlertDialog.Builder(this)
                .setTitle("FaceBook/Twitter")
                .setMultiChoiceItems(
                        new String[] { "Link FaceBook account",
                                "Share on FaceBook timeline" }, // 复选框的item名称
                        checkedItems, // 复选框的默认选择情况
                        new OnMultiChoiceClickListener() { // 复选框点击监听
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which, // 点击项的位置
                                    boolean isChecked) { // 点击后复选框的勾选情况
                                Toast.makeText(
                                        ProfileActivity.this,
                                        "选项" + (which + 1) + " 选择状态为:"
                                                + isChecked, 0).show();
                            }
                        }).setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null).show();
    }
    
    
    public void getData()
    {
    	if(mProgressDialog!=null && !mProgressDialog.isShowing())
    	mProgressDialog.show();
    	
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("userid", app.getUserContent().getUserId());
//                params.put("lastpid", "0");
//                params.put("offset", "10");
//
//                boolean flag = httpSession.getHomePostInfo(
//                        HttpSession.GET_HOME_POST_LIST, params);
//                if (!flag) {
//                    err = httpSession.getErrCode();
//                    mainHandler.sendEmptyMessage(MSG_LOGIN_FAILED);
//                } else {
//                    mHomePostInfos = httpSession.getmHomePostInfos();
//                    AchievedFragment.mHomePostInfos = mHomePostInfos;
//                    mainHandler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
//                }
            	
            	//by black crystal
            	
            	 Map<String, String> params = new HashMap<String, String>();
                 params.put("start", "0");
                 params.put("offset", "5");
                 boolean flag = httpSession.getAchmInfo(
                         HttpSession.GET_ACHI_LIST, params);
                 if (!flag) {
                     err = httpSession.getErrCode();
                     mainHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                 } else {
                     mAchmInfos = httpSession.getmAchmInfos();
                     ProfileFragment.mAchmInfos = mAchmInfos;
                     mainHandler.sendEmptyMessage(MSG_GET_DATA_SUCCESS);
                 }

            }
        }).start();
    }
    /**
     * 根据类别获取相应的数据
     * @param styleID
     */
    public void getDataByLifeSytleID(final String styleID)
    {
    	 mProgressDialog.show();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   {
                       Map<String, String> params = new HashMap<String, String>();
                       params.put("start", "0");
                       params.put("offset", "5");
                       params.put("lifestyleid", styleID);
                       
                       boolean flag = httpSession.getAchmInfo(
                               HttpSession.GET_ARCHI_LIST_BY_TYPE_URL, params);
                       ;
                       if (!flag) {
                           err = httpSession.getErrCode();
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                       } else {
                           mAchmInfos = httpSession.getmAchmInfos();
                           AchmFragment.mAchmInfos = mAchmInfos;
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_SUCCESS);
                       }
                   }
                   //
               }
           }).start();
       }
    
}