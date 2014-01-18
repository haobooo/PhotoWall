package com.photowall.photowallcommunity;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.photowall.adapters.ArchievementGridView;
import com.photowall.adapters.TestFragmentAdapter;
import com.photowall.bean.PhotoWallTools;
import com.photowall.demos.TestDataGenerators;
import com.photowall.net.AchmInfo;
import com.photowall.net.ErrCode;
import com.photowall.net.HomePostInfo;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.tools.Constants;
import com.photowall.ui.ArchievementPostsActivity;
import com.photowall.widget.AchmFragment;
import com.photowall.widget.BaseSampleActivity;
import com.photowall.widget.HotFragment;
import com.photowall.widget.NewFragment;
import com.photowall.widget.SlidingMenuLayout;
import com.photowall.widget.SlidingMenuLayout.OnScrollListener;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.viewpagerindicator.TitlePageIndicator.OnCenterItemClickListener;

public class HomePageActivity extends BaseSampleActivity implements OnTouchListener,
        GestureDetector.OnGestureListener, OnItemClickListener, OnClickListener ,
        OnCenterItemClickListener{
	
	//###########################################
	
	
	//guide
	private RelativeLayout guide_layer;
	private ImageView guide_hand;
	
    private boolean hasMeasured = false;// 是否Measured.
    private LinearLayout layout_left;// 左边布局
    private LinearLayout layout_right;// 右边布局
    private ImageView iv_set;// 图片
    private ListView lv_set;// 设置菜单

    /** 每次自动展开/收缩的范围 */
    private int MAX_WIDTH = 0;
    /** 每次自动展开/收缩的速度 */
    private final static int SPEED = 15;

    private final static int sleep_time = 5;

    private GestureDetector mGestureDetector;// 手势
    private boolean isScrolling = false;
    private float mScrollX; // 滑块滑动距离
    private int window_width;// 屏幕的宽度
    
//############################################################
    
    private String TAG = "MainActivity";

    private View view = null;// 点击的view

    private String title[] = { "Search","Help","Report an issue","Setting" };

    private SlidingMenuLayout mylaout;
    private RelativeLayout mNtificationsImage = null;
    private ImageView mUserNameImage = null;
    private ImageView mHomeImage = null;
    private ImageView mAchievementsImage = null;
    private ImageView mFeedBackImage = null;
    private ViewPager mHomeViewPager;
    private TextView mNewTextView;
    private TextView mHotTextView;
    private PhotoWallFragment mPhotoWallFragment;
//    private LinearLayout mClassifyLinearLayout;
    private LinearLayout mNotificationsLinearLayout;
    private RelativeLayout mUserNameLinearLayout;
    private LinearLayout mHomeLinearLayout;
    private LinearLayout mAchievementsLinearLayout;
    private LinearLayout mFeedBackLinearLayout;
    private Spinner mSpinner;
    private TextView id_tap_types;
    private ImageView mMenuImageView;
    private  ArrayAdapter<String> mSpinnerAdapter;
    
    private String[] mSpinnerArrayStr;
    private String[] classifyIDs;
    
    private String mHomeMark = null;
    private String mAchievementsMark = null;
    private TextView mTitleTextView;
    private ImageView mCreateImageView;
    private String[] mTitleString;
    private List<String> mMenulist;
    private UserContent mUserContent;
    private TextView mUserNameTextView;
    private TypedArray images;
    
  //#############################################################
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private List<AchmInfo> mAchmInfos = new ArrayList<AchmInfo>();
    private List<HomePostInfo> mHomePostInfos = new ArrayList<HomePostInfo>();
    public ProgressDialog mProgressDialog;
    
    private final int MSG_GET_DATA_FAILED = 0x01;
    private final int MSG_GET_DATA_SUCCESS = 0x02;
    /**
     * handler
     */
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
            
                case MSG_GET_DATA_FAILED:
                { 
                    String text="";
                    try {
                        if (err.getError() == null) {
                            text = "Error null";
                        }else
                        text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(HomePageActivity.this, text, Toast.LENGTH_SHORT).show();
                    break;
                }
                
                case MSG_GET_DATA_SUCCESS:
                {
                	if(mProgressDialog.isShowing())
                	{
                		mProgressDialog.dismiss();
                	}
                	mAdapter.setFragments(fragments);
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        };
    };
    

    /***
     * 初始化view
     */
    private void InitView() {
    	
    	guide_layer = (RelativeLayout) findViewById(R.id.guide_layer);
    	guide_hand = (ImageView) findViewById(R.id.guide_hand);
    	
        mTitleString = getResources().getStringArray(R.array.title_string1);
        layout_left = (LinearLayout) findViewById(R.id.layout_left);
        layout_right = (LinearLayout) findViewById(R.id.layout_right);
        iv_set = (ImageView) findViewById(R.id.iv_set);
        lv_set = (ListView) findViewById(R.id.lv_set);
        mylaout = (SlidingMenuLayout) findViewById(R.id.mylaout);
        mNtificationsImage = (RelativeLayout) findViewById(R.id.notifications_image);
        mUserNameImage = (ImageView) findViewById(R.id.user_name_image);
        mHomeImage = (ImageView) findViewById(R.id.home_image);
        mAchievementsImage = (ImageView) findViewById(R.id.achievements_image);
        mFeedBackImage = (ImageView) findViewById(R.id.feed_back_image);
        mHomeViewPager = (ViewPager)findViewById(R.id.vp_main);
        mNewTextView = (TextView) findViewById(R.id.tv_new);
        mNewTextView.setOnClickListener(this);
        mHotTextView = (TextView) findViewById(R.id.tv_hot);
        mHotTextView.setOnClickListener(this);
        mPhotoWallFragment = new PhotoWallFragment();
        mNotificationsLinearLayout = (LinearLayout) findViewById(R.id.layout_notifications);
        mNotificationsLinearLayout.setOnClickListener(this);
        mUserNameLinearLayout = (RelativeLayout) findViewById(R.id.layout_user_name);
        mUserNameLinearLayout.setOnClickListener(this);
        mHomeLinearLayout = (LinearLayout) findViewById(R.id.layout_home);
        mHomeLinearLayout.setOnClickListener(this);
        mAchievementsLinearLayout = (LinearLayout)findViewById(R.id.layout_achievements);
        mAchievementsLinearLayout.setOnClickListener(this);
        mFeedBackLinearLayout = (LinearLayout)findViewById(R.id.layout_feed_back);
        mFeedBackLinearLayout.setOnClickListener(this);
        mUserNameTextView = (TextView) findViewById(R.id.user_name_text);
        
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mCreateImageView = (ImageView) findViewById(R.id.iv_creat);
        mCreateImageView.setOnClickListener(this);
        mMenuImageView = (ImageView) findViewById(R.id.iv_menu);
        mMenuImageView.setOnClickListener(this);
        mMenulist = new ArrayList<String>();
        for (int i = 0; i < title.length; i++) {
            mMenulist.add(title[i]);
        }
        
        mSpinnerArrayStr = getResources().getStringArray(R.array.photo_classify);
        classifyIDs = getResources().getStringArray(R.array.photo_classify_id);
        
        TestDataGenerators.setHotArchievement(mSpinnerArrayStr[0], 70);
        mSpinner = (Spinner) findViewById(R.id.spinnered);
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
        id_tap_types = (TextView) findViewById(R.id.id_tap_types);
        id_tap_types.setOnClickListener(this);
        id_tap_types.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				mSpinner.onTouchEvent(event);
				return false;
			}
		});
        mSpinnerAdapter.setDropDownViewResource( android.R.layout.preference_category);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int pos, long arg3) {
            	
            	//change the name
            	id_tap_types.setText(mSpinnerArrayStr[pos]);
            	ArchievementGridView.selectType = mSpinnerArrayStr[pos];
            	//get the new data by type selected
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
        });
        lv_set.setAdapter(new ArrayAdapter<String>(this, R.layout.item,
                R.id.tv_item, title));
        /***
         * 实现该接口
         */
        mylaout.setOnScrollListener(new OnScrollListener() {
            @Override
            public void doScroll(float distanceX) {
                doScrolling(distanceX);
            }

            @Override
            public void doLoosen() {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                        .getLayoutParams();
                // 缩回去
                if (layoutParams.leftMargin < MAX_WIDTH / 2) {
                    new AsynMove().execute(-SPEED);
                } else {
                    new AsynMove().execute(SPEED);
                }
            }
        });

        // 点击监听
        lv_set.setOnItemClickListener(this);
        iv_set.setOnClickListener(this);

//        layout_right.setOnTouchListener(this);
//        layout_left.setOnTouchListener(this);
//        iv_set.setOnTouchListener(this);
//        mGestureDetector = new GestureDetector(this);
//        // 禁用长按监听
//        mGestureDetector.setIsLongpressEnabled(false);
        
        //by black crystal
        
        
        getMAX_WIDTH();
    }
    private void setTitle(){
        Intent intent = getIntent();
        mHomeMark = intent.getStringExtra("home");
        mAchievementsMark = intent.getStringExtra("achievements");
        if (mHomeMark != null) {
            mTitleTextView.setText("Home");
        }else if(mAchievementsMark != null){
            mTitleTextView.setText("Achievements");
        }
        mUserContent = httpSession.getUserContent();
        if (mUserContent != null) {
            mUserNameTextView.setText(mUserContent.getUserNickName());
        }
        
    }
    /**
     * 初始化 view page的相关数据
     */
    private void initViewPage() {

//        List<Fragment> fragments = new ArrayList<Fragment>();
//        fragments.add(mPhotoWallFragment.newInstance("-------New-------"));
//        fragments.add(mPhotoWallFragment.newInstance("-------Hot-------"));

//        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
//        mHomeViewPager.setAdapter(mAdapter);
//        mHomeViewPager.setCurrentItem(0);
//        changeTextColor(0);
//        mHomeViewPager.setOnPageChangeListener(new FragmentPageChangeListener());

    }
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public void pagerIndicator()
    {
        getData();
        if (mHomeMark != null) {
            for (int i = 0; i < mTitleString.length; i++) {
                if ("New".equals(mTitleString[i])){
                    fragments.add(NewFragment.newInstance(mTitleString[i]));
                }else if("Hot".equals(mTitleString[i])){
                    fragments.add(HotFragment.newInstance(mTitleString[i]));
                }
            }
        }else if(mAchievementsMark != null){
            for (int i = 0; i < mTitleString.length; i++) {
                if ("New".equals(mTitleString[i])){
                    fragments.add(AchmFragment.newInstance(mTitleString[i]));
                }else if("Hot".equals(mTitleString[i])){
                    fragments.add(AchmFragment.newInstance(mTitleString[i]));
                }
            }
        }
        
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),mTitleString,fragments);
        mPager = (ViewPager)findViewById(R.id.vp_main);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        indicator.setOnCenterItemClickListener(this);
        //by black crystal
        indicator.setBackgroundColor(Color.WHITE);
        mIndicator = indicator;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        TestDataGenerators.PIbitmap = TestDataGenerators.drawableToBitmap(getResources().
                getDrawable(R.drawable.xpic9869_s));
        //init net
        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        //init dialog
        mProgressDialog = new ProgressDialog(HomePageActivity.this);
        mProgressDialog.setMessage(getString(R.string.tip_geting_data));
        mProgressDialog.setCanceledOnTouchOutside(false);
        //
        
        InitView();
        setTitle();
//        initViewPage();
        pagerIndicator();
        
        if(getIntent().getBooleanExtra(Constants.IS_REGISTING, false))
        {
        	PhotoWallApplication.getPhotoWallApplication().setLeadingMode(true);
        	guide_layer.setVisibility(View.VISIBLE);
        	guide_layer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Toast.makeText(HomePageActivity.this, "to post", Toast.LENGTH_SHORT).show();
					PhotoWallApplication.getPhotoWallApplication().setCurrAchminfo(AchmFragment.mAchmInfos.get(0));
			        //by black crystal
			        Intent intent = new Intent(HomePageActivity.this,ArchievementPostsActivity.class);
			        startActivity(intent);
				}
			});
        }
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if(!PhotoWallApplication.getPhotoWallApplication().isLeadingMode())
		{
			guide_layer.setVisibility(View.GONE);
		}
    }
    /***
     * listview 正在滑动时执行.
     */
    void doScrolling(float distanceX) {
        isScrolling = true;
        mScrollX += distanceX;// distanceX:向左为正，右为负

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                .getLayoutParams();
        RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
                .getLayoutParams();
//        layoutParams.leftMargin -= mScrollX;
//        layoutParams_1.leftMargin = window_width + layoutParams.leftMargin;
        layoutParams.leftMargin -= mScrollX;
        layoutParams_1.leftMargin = MAX_WIDTH + layoutParams.leftMargin;
        if (layoutParams.leftMargin >= MAX_WIDTH) {
            isScrolling = false;// 拖过头了不需要再执行AsynMove了
            layoutParams.leftMargin = MAX_WIDTH;
            layoutParams_1.leftMargin = 0;

        } else if (layoutParams.leftMargin <= 0) {
            // 拖过头了不需要再执行AsynMove了
            isScrolling = false;
            layoutParams.leftMargin = 0;
            layoutParams_1.leftMargin = - MAX_WIDTH;
        }
        Log.v(TAG, "layoutParams.leftMargin=" + layoutParams.leftMargin
                + ",layoutParams_1.leftMargin =" + layoutParams_1.leftMargin);

        layout_left.setLayoutParams(layoutParams);
        layout_right.setLayoutParams(layoutParams_1);
    }

    /***
     * 获取移动距离 移动的距离其实就是layout_left的宽度
     */
    void getMAX_WIDTH() {
        ViewTreeObserver viewTreeObserver = layout_left.getViewTreeObserver();
        // 获取控件宽度
        viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    window_width = getWindowManager().getDefaultDisplay()
                            .getWidth();
                    MAX_WIDTH = layout_right.getWidth();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                            .getLayoutParams();
                    RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
                            .getLayoutParams();
                    ViewGroup.LayoutParams layoutParams_2 = mylaout
                            .getLayoutParams();
                    // 注意： 设置layout_left的宽度。防止被在移动的时候控件被挤压
                    layoutParams.width = window_width;
                    layout_left.setLayoutParams(layoutParams);

                    // 设置layout_right的初始位置.
                    layoutParams_1.leftMargin = -MAX_WIDTH;
                    layout_right.setLayoutParams(layoutParams_1);
                    // 注意：设置lv_set的宽度防止被在移动的时候控件被挤压
                    layoutParams_2.width = MAX_WIDTH;
                    mylaout.setLayoutParams(layoutParams_2);

                    Log.v(TAG, "MAX_WIDTH=" + MAX_WIDTH + "width="
                            + window_width);
                    hasMeasured = true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                    .getLayoutParams();
            if (layoutParams.leftMargin < 0) {
                new AsynMove().execute(SPEED);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        view = v;// 记录点击的控件

        // 松开的时候要判断，如果不到半屏幕位子则缩回去，
        if (MotionEvent.ACTION_UP == event.getAction() && isScrolling == true) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                    .getLayoutParams();
            // 缩回去
            if (layoutParams.leftMargin > MAX_WIDTH / 2) {
                new AsynMove().execute(-SPEED);
            } else {
                new AsynMove().execute(SPEED);
            }
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {

        int position = lv_set.pointToPosition((int) e.getX(), (int) e.getY());
        if (position != ListView.INVALID_POSITION) {
            View child = lv_set.getChildAt(position
                    - lv_set.getFirstVisiblePosition());
            if (child != null)
                child.setPressed(true);
        }

        mScrollX = 0;
        isScrolling = false;
        // 将之改为true，才会传递给onSingleTapUp,不然事件不会向下传递.
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    /***
     * 点击松开执行
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // 点击的不是layout_left
        if (view != null && view == iv_set) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                    .getLayoutParams();
            // 左移动
            if (layoutParams.leftMargin > 0) {
                new AsynMove().execute(-SPEED);
            } else {
                // 右移动
                new AsynMove().execute(SPEED);
                lv_set.setSelection(0);// 设置为首位
                
            }
        } else if (view != null && view == layout_left) {
            RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) layout_left
                    .getLayoutParams();
            if (layoutParams.leftMargin < 0) {
                // 说明layout_left处于移动最左端状态，这个时候如果点击layout_left应该直接所以原有状态.(更人性化)
                // 右移动
                new AsynMove().execute(SPEED);
            }
        }

        return true;
    }

    /***
     * 滑动监听 就是一个点移动到另外一个点. distanceX=后面点x-前面点x，如果大于0，说明后面点在前面点的右边及向右滑动
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        // 执行滑动.
        doScrolling(distanceX);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        return false;
    }

    class AsynMove extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int times = 0;
            if (MAX_WIDTH % Math.abs(params[0]) == 0)// 整除
                times = MAX_WIDTH / Math.abs(params[0]);
            else
                times = MAX_WIDTH / Math.abs(params[0]) + 1;// 有余数

            for (int i = 0; i < times; i++) {
                publishProgress(params[0]);
                try {
                    Thread.sleep(sleep_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        /**
         * update UI
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                    .getLayoutParams();
            RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
                    .getLayoutParams();
            // 右移动
            if (values[0] > 0) {
                layoutParams.leftMargin = Math.min(layoutParams.leftMargin
                        + values[0], MAX_WIDTH);
                layoutParams_1.leftMargin = Math.min(layoutParams_1.leftMargin
                        + values[0], 0);
                layoutParams.width = window_width;
                layoutParams.rightMargin = -layoutParams.leftMargin;
                Log.v(TAG, "layout_left右" + layoutParams.leftMargin
                        + ",layout_right右" + layoutParams_1.leftMargin);
            } else {
                // 左移动
                layoutParams.leftMargin = Math.max(layoutParams.leftMargin
                        + values[0], 0);
                layoutParams_1.leftMargin = Math.max(layoutParams_1.leftMargin
                        + values[0], 0);
                layoutParams.width = window_width;
                Log.v(TAG, "layout_left左" + layoutParams.leftMargin
                        + ",layout_right左" + layoutParams_1.leftMargin);
            }
            layout_right.setLayoutParams(layoutParams_1);
            layout_left.setLayoutParams(layoutParams);

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                .getLayoutParams();
        // 只要没有滑动则都属于点击
        if (layoutParams.leftMargin == -MAX_WIDTH)
            Toast.makeText(HomePageActivity.this, title[position], 1).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.iv_set:
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
                    .getLayoutParams();
            // 左移动
            if (layoutParams.leftMargin > 0) {
                new AsynMove().execute(-SPEED);
            } else {
                // 右移动
                new AsynMove().execute(SPEED);
                lv_set.setSelection(0);// 设置为首位

            }
            break;
        case R.id.layout_notifications:
            Intent notfIntent = new Intent();
            notfIntent.setClass(HomePageActivity.this, NotificationsActivity.class);
            startActivity(notfIntent);
            break;
        case R.id.layout_user_name:
            Intent userIntent = new Intent();
            userIntent.setClass(HomePageActivity.this, ProfileActivity.class);
            userIntent.putExtra("title_mark", "own");
            userIntent.putExtra("user_name", "User Name");
            startActivity(userIntent);
            break;
        case R.id.layout_home:
            Intent homeIntent = new Intent();
            homeIntent.putExtra("home", "home");
            homeIntent.setClass(HomePageActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
            break;
        case R.id.layout_achievements:
            Intent achmIntent = new Intent();
            achmIntent.putExtra("achievements", "achievements");
            achmIntent.setClass(HomePageActivity.this, HomePageActivity.class);
            startActivity(achmIntent);
            break;
        case R.id.layout_feed_back:
            Intent feedbackIntent = new Intent();
            feedbackIntent.setClass(HomePageActivity.this, FeedBackActivity.class);
            startActivity(feedbackIntent);
            break;
        case R.id.tv_new:
//            mHomeViewPager.setCurrentItem(0);
            break;
        case R.id.tv_hot:
//            mHomeViewPager.setCurrentItem(1);
            break;
        case R.id.classify_layout:
//            showWindow(view);
            break;
        case R.id.iv_creat:
            Intent creteIntent = new Intent();
            creteIntent.setClass(HomePageActivity.this, CreateActivity.class);
            startActivity(creteIntent);
            break;
        case R.id.iv_menu:
            boolean isMark[] = {false,false,false,false,true,true,true,true};
            PhotoWallTools.showPopupWindow(HomePageActivity.this, mMenuImageView, 300, 500, isMark);
            break;

        case R.id.id_tap_types://select image by type all .....
        {
        	
        	break;
        }
        default:
            break;
        }
    }
    
    /**
     * 设置下面文本的颜色。
     * @param location
     */
    private void changeTextColor(int location) {
        switch (location) {
        case 0:
            mNewTextView.setTextColor(Color.YELLOW);
            mHotTextView.setTextColor(Color.BLACK);
            break;
        case 1:
            mHotTextView.setTextColor(Color.YELLOW);
            mNewTextView.setTextColor(Color.BLACK);
            break;
        default:
            break;
        }
    }
    
    private class FragmentPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int location) {
            changeTextColor(location);
        }

    }

    @Override
    public void onCenterItemClick(int position) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "You clicked the center title!", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 获取所有类别的数据
     */
    public void getData()
    {
    	 mProgressDialog.show();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   if (mHomeMark != null) {
                       Map<String, String> params = new HashMap<String, String>();
                       params.put("lastpid", "0");
                       params.put("offset", "10");

                       boolean flag = httpSession.getHomePostInfo(
                               HttpSession.GET_HOME_POST_LIST, params);
                       ;
                       if (!flag) {
                           err = httpSession.getErrCode();
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                       } else {
                           mHomePostInfos = httpSession.getmHomePostInfos();
//                           if (mPager.getCurrentItem() == 1) {
                               NewFragment.mHomePostInfos = mHomePostInfos;
//                           }else {
                               HotFragment.mHomePostInfos = mHomePostInfos;
//                           }
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_SUCCESS);
                       }
                   }else if (mAchievementsMark != null) {
                       Map<String, String> params = new HashMap<String, String>();
                       params.put("start", "0");
                       params.put("offset", "10000");

                       boolean flag = httpSession.getAchmInfo(
                               HttpSession.GET_ACHI_LIST, params);
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
                   if (mHomeMark != null) {
                       Map<String, String> params = new HashMap<String, String>();
                       params.put("lastpid", "0");
                       params.put("offset", "10");
                       params.put("lifestyleid", styleID);
                       
                       boolean flag = httpSession.getHomePostInfo(
                               HttpSession.GET_ARCHI_LIST_BY_TYPE_URL, params);
                       if (!flag) {
                           err = httpSession.getErrCode();
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                       } else {
                           mHomePostInfos = httpSession.getmHomePostInfos();
//                           if (mPager.getCurrentItem() == 1) {
                               NewFragment.mHomePostInfos = mHomePostInfos;
//                           }else {
                               HotFragment.mHomePostInfos = mHomePostInfos;
//                           }
                           mainHandler.sendEmptyMessage(MSG_GET_DATA_SUCCESS);
                       }
                   }
                   //by black crystal
                   else if (mAchievementsMark != null) 
                   {
                       Map<String, String> params = new HashMap<String, String>();
                       params.put("start", "0");
                       params.put("offset", "5");
                       params.put("lifestyleid", styleID);
                       
                       boolean flag = httpSession.getAchmInfo(
                               HttpSession.GET_ARCHI_LIST_BY_TYPE_URL, params);
                       
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
    
    @Override
	public void onBackPressed() {
		
	  new AlertDialog.Builder(this)
	  .setTitle("提示")
	  .setMessage("确定要退出吗?")
	  .setNegativeButton("确定", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int arg1) {
			dialog.dismiss();
			PhotoWallApplication.getPhotoWallApplication().loginOut();
			HomePageActivity.this.finish();
		}
	})
	.setPositiveButton("取消", null).create().show();
		
	}
   
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	if(hasFocus && PhotoWallApplication.getPhotoWallApplication().isLeadingMode() 
//    			&&  guide_hand.getVisibility() == View.GONE
    			)
    	{  int[] location = new int[2];
    	    mHomeViewPager.getLocationOnScreen(location);
    	    int x = location[0];
		    int y = location[1];
		    guide_hand.setVisibility(View.VISIBLE);
		    guide_hand.layout(x+guide_hand.getWidth(), y+guide_hand.getHeight(), x+guide_hand.getWidth()*2, y+guide_hand.getHeight()*2);
    	}
    	super.onWindowFocusChanged(hasFocus);
    }
}  

