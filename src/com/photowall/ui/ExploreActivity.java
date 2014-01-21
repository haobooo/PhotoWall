package com.photowall.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.photowall.bean.DataManagerTest;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;
import com.photowall.ui.earchievement.ArchievementHotTag;
import com.photowall.ui.earchievement.ArchievementJinhuaFeature;
import com.photowall.ui.earchievement.ArchipaihangFramgmentActivity;
import com.photowall.ui.explore.GridAdapter;
import com.photowall.ui.explore.HotPostsFramgmentActivity;
import com.photowall.ui.explore.HotTag;
import com.photowall.ui.explore.QuestAdapter;
import com.photowall.ui.quests.QuestDetailsActivity;
import com.photowall.ui.quests.QuestGenerate;
import com.photowall.ui.quests.QuestObject;

public class ExploreActivity extends Activity 
implements View.OnClickListener
{
	private ImageView backImage;
	private ImageView settingsImage;
	
	private TextView title_text;
	private GridView hot_grid;
	private GridAdapter gridAdapter;
	private ArrayList<HotTag> hotlist= new ArrayList<HotTag>();
	
	private ListView questlist;
	private ArrayList<QuestObject> list = new ArrayList<QuestObject>();
	private QuestAdapter questAdapter;
	
	private ImageView btn_feature_archi;
	private ImageView btn_hot_posts;
	private ImageView btn_archi_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.explore_layout);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_title);
		
		initview();
	}
	public void initview()
	{
		backImage = (ImageView) findViewById(R.id.title_back);
		backImage.setOnClickListener(this);
		
		settingsImage = (ImageView) findViewById(R.id.title_settings);
		settingsImage.setOnClickListener(this);
		
		btn_archi_list = (ImageView) findViewById(R.id.btn_archi_list);
		btn_archi_list.setOnClickListener(this);
		
		btn_hot_posts = (ImageView) findViewById(R.id.btn_hot_posts);
		btn_hot_posts.setOnClickListener(this);
		
		btn_feature_archi = (ImageView) findViewById(R.id.btn_feature_archi);
		btn_feature_archi.setOnClickListener(this);
		
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("EXPLORE");
		
		hot_grid = (GridView) findViewById(R.id.hot_grid);
		gridAdapter = new GridAdapter(hotlist, this);
		hot_grid.setAdapter(gridAdapter);
		
		questlist = (ListView) findViewById(R.id.questlist);
		questAdapter = new QuestAdapter(list, this);
		questlist.setAdapter(questAdapter);
		
		//test
		hotlist.addAll(DataManagerTest.getHottags());
		gridAdapter.notifyDataSetChanged();
		
		list.addAll(QuestGenerate.getTestData());
		questAdapter.notifyDataSetChanged();
		
		hot_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ExploreActivity.this,ArchievementHotTag.class);
				startActivity(intent);
			}
		});
		
		questlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ExploreActivity.this,QuestDetailsActivity.class);
				startActivity(intent);
			}
		});
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_feature_archi:
		{
			Intent intent = new Intent(ExploreActivity.this,ArchievementJinhuaFeature.class);
			startActivity(intent);
			break;
		}
		case R.id.btn_hot_posts:
		{
			Intent intent = new Intent(ExploreActivity.this,HotPostsFramgmentActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.btn_archi_list:
		{
			Intent intent = new Intent(ExploreActivity.this,ArchipaihangFramgmentActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.title_back:
		{
			finish();
			
			break;
		}
		case R.id.title_settings:
		{
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
}
