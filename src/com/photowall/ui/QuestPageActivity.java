package com.photowall.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.photowall.PhotoHomePageActivity;
import com.photowall.photowallcommunity.HomePageActivity;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;
import com.photowall.tools.Constants;
import com.photowall.ui.quests.QuestGenerate;
import com.photowall.ui.quests.QuestObject;
import com.photowall.ui.quests.QuestPageAdapter;

public class QuestPageActivity extends Activity 
implements View.OnClickListener
{
	private ImageView menu_image;

	private ListView questlist;
	private TextView fllowedNumber;
	private ImageView startbtn;
	private ViewGroup guide_bottom;
	private ImageView back_image;
	
	private QuestPageAdapter questPageAdapter;
	private ArrayList<QuestObject> list = new ArrayList<QuestObject>();
	
	public static final int MSG_UPDATE_FOLLOW = 0;
	private int fnumber = 0;
	
	private boolean isGuide = false;
	
	private Handler mainHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			
			case MSG_UPDATE_FOLLOW:
			{
				int pos = msg.arg1;
				list.get(pos).setFollowing(true);
				questPageAdapter.notifyDataSetChanged();
				
				fnumber++;
				fllowedNumber.setText(fnumber+"/3");
				if(fnumber >= 3)
				{
					startbtn.setEnabled(true);
				}
				break;
			}
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quest_page_layout);
		isGuide = getIntent().getBooleanExtra(Constants.IS_REGISTING, false);
		PhotoWallApplication.getPhotoWallApplication().setGuideMode(isGuide);
		initviews();
		
	}
	public void initviews()
	{
//		menu_image = (ImageView) findViewById(R.id.menu_image);
//		menu_image.setOnClickListener(this);
		
		guide_bottom = (ViewGroup) findViewById(R.id.guide_bottom);
		if(isGuide)
		{
			guide_bottom.setVisibility(View.VISIBLE);
		}
		else
		{
			guide_bottom.setVisibility(View.GONE);
			findViewById(R.id.headimg).setVisibility(View.GONE);
		}
//		back_image = (ImageView) findViewById(R.id.back_image);
//		back_image.setOnClickListener(this);
		questlist = (ListView) findViewById(R.id.questlist);
		questPageAdapter = new QuestPageAdapter(list, this);
		questPageAdapter.setMainhaHandler(mainHandler);
		questlist.setAdapter(questPageAdapter);
		list.addAll(QuestGenerate.getTestData());
		questPageAdapter.notifyDataSetChanged();
		
		fllowedNumber = (TextView) findViewById(R.id.fllowedNumber);
		startbtn = (ImageView) findViewById(R.id.startbtn);
		
		if(getIntent().getBooleanExtra(Constants.IS_REGISTING
				, false))
		{
			startbtn.setEnabled(false);
		}
		startbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuestPageActivity.this,PhotoHomePageActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			case R.id.back_image:
//			{
//				finish();
//				
//				break;
//			}
//			case R.id.menu_image:
//			{
//				settings();
//				break;
//			}
		}
	}
	public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
}
