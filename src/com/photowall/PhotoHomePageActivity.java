package com.photowall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.photowall.bean.PhotoWallTools;
import com.photowall.photowallcommunity.HomePageActivity;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;
import com.photowall.tools.ScreenInfo;
import com.photowall.ui.QuestPageActivity;
import com.photowall.ui.earchievement.ArchievementDetailsActivity;
import com.photowall.widget.utils.LoadingManager;

public class PhotoHomePageActivity extends FragmentActivity 
implements View.OnClickListener
{
	private LoadingManager loadingManager;
	
	
    private PhotoMenuFragment photoMenuFragment;
    private Fragment mContent;
    
    private TypedArray images;
    private  ArrayAdapter<String> mSpinnerAdapter;
    private String[] mSpinnerArrayStr;
    private String[] classifyIDs;
    
    
    private ImageView showmenubtn;
    private TextView title_text;
    private TextView id_tap_types;
    private Spinner mSpinner;
    private ImageView showquestbtn;
    private ImageView iv_menu;
    
    private ViewGroup guide_layer;
    
    private SlidingMenu slidingMenu;
    
    private final int MSG_DISMISS_ANIM = 0;
    private Handler mainHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg) {
    		loadingManager.dismissLoading();
    	}
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        ScreenInfo.init(this);
        // set the Above View Fragment
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null)
            mContent = new ViewPagerFragment(); 
        
        Bundle bundle = new Bundle();
        bundle.putInt("pagetype", ViewPagerFragment.PAGE_TYPE_HOME);
        mContent.setArguments(bundle);
       // set the Above View
        setContentView(R.layout.content_frame);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.home_title);
        
        
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.pager_container, mContent)
        .commit();
//       // set the menu view
//        setBehindContentView(R.layout.menu_frame);
//        if (savedInstanceState == null) {
//            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
//            photoMenuFragment = new PhotoMenuFragment();
//            t.replace(R.id.menu_frame, photoMenuFragment);
//            t.commit();
//        } else {
//            photoMenuFragment = (PhotoMenuFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
//        }
//       // customize the SlidingMenu
//        SlidingMenu sm = getSlidingMenu();
//        sm.setShadowWidthRes(R.dimen.shadow_width);
//        sm.setShadowDrawable(R.drawable.shadow);
//        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        sm.setFadeDegree(0.35f);
//        sm.setBehindWidth(ScreenInfo.getScreenw()/2);
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        sm.setBehindScrollScale(0.0f);
//     // customize the SlidingMenu
//        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setBehindWidth(ScreenInfo.getScreenw()/2);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindScrollScale(0.0f);
        
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.menu_frame);
        
        photoMenuFragment = new PhotoMenuFragment();
        getSupportFragmentManager().beginTransaction()
        	.replace(R.id.menu_frame, photoMenuFragment)
        	.commit();
        
        initViews();
        
        loadingManager = LoadingManager.getInstance(this);
        loadingManager.showLoading();
    	mainHandler.sendEmptyMessageDelayed(MSG_DISMISS_ANIM, 1000);
    }
    
    public SlidingMenu getSlidingMenu() {
    	return slidingMenu;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }
    
    public void switchContent(Fragment fragment) {
    	
         mContent = fragment;
         getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.pager_container, mContent)
        .commit();
         getSlidingMenu().showContent();
         

    }
    
    public void initViews()
    {
    	guide_layer = (ViewGroup) findViewById(R.id.guide_layer);
    	if(PhotoWallApplication.getPhotoWallApplication().isGuideMode())
    	{
    		guide_layer.setVisibility(View.VISIBLE);
    		guide_layer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(PhotoHomePageActivity.this,ArchievementDetailsActivity.class);
					startActivity(intent);
				}
			});
    	}
    	else
    	{
    		guide_layer.setVisibility(View.GONE);
    	}
    	
    	showmenubtn = (ImageView) findViewById(R.id.iv_menu);
//    	title_text = (TextView) findViewById(R.id.title_text);
//    	id_tap_types = (TextView) findViewById(R.id.id_tap_types);
//    	mSpinner = (Spinner) findViewById(R.id.spinnered);
    	showquestbtn = (ImageView) findViewById(R.id.iv_creat);
    	iv_menu = (ImageView) findViewById(R.id.iv_set);
    	
    	showmenubtn.setOnClickListener(this);
    	showquestbtn.setOnClickListener(this);
    	iv_menu.setOnClickListener(this);
//    	
//    	//spinner #######################################
//    	 mSpinnerArrayStr = getResources().getStringArray(R.array.photo_classify);
//         classifyIDs = getResources().getStringArray(R.array.photo_classify_id);
//    	 images = getResources().obtainTypedArray(R.array.photo_classify_drawable_all);
//    	 mSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_item,mSpinnerArrayStr){
//             @Override  
//             public View getDropDownView(int position, View convertView, ViewGroup parent) {  
//                 if(convertView==null){  
//                     convertView = getLayoutInflater().inflate(R.layout.spinner_dropdown_item, parent, false);  
//                 }  
//                 TextView label = (TextView) convertView.findViewById(R.id.label);  
//                 label.setText(getItem(position));  
//                 ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
//                 Drawable drawable = images.getDrawable(position);
//                 imageView.setImageDrawable(drawable);
//                 return convertView;  
//             }  
//         };
//    	
//    	id_tap_types.setOnClickListener(this);
//        id_tap_types.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				mSpinner.onTouchEvent(event);
//				return false;
//			}
//		});
//        
//        mSpinnerAdapter.setDropDownViewResource( android.R.layout.preference_category);
//        mSpinner.setAdapter(mSpinnerAdapter);
//        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                    int pos, long arg3) {
//            	//change the name
//            	id_tap_types.setText(mSpinnerArrayStr[pos]);
//            	
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
        //spinner end ##################################
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_menu://menu
		{
			getSlidingMenu().showMenu(true);
//			Toast.makeText(PhotoHomePageActivity.this, "showmenu", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.iv_creat:
		{
			Intent intent = new Intent(PhotoHomePageActivity.this,QuestPageActivity.class);
			startActivity(intent);
			
			break;
		}
		case R.id.iv_set:
		{
//			showSet();
			settings();
			break;
		}
		default:
			break;
		}
	}
	
	public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
	public void showSet()
	{
		 boolean isMark[] = {false,false,false,false,true,true,true,true};
         PhotoWallTools.showPopupWindow(this, iv_menu, 300, 500, isMark);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if( !PhotoWallApplication.getPhotoWallApplication().isGuideMode() )
        {
        	guide_layer.setVisibility(View.GONE);
        }

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
			PhotoHomePageActivity.this.finish();
		}
	})
	.setPositiveButton("ȡ��", null).create().show();
		
	}
}
