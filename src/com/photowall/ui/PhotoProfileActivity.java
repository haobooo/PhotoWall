package com.photowall.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.photowall.bean.ArchievementItemBean;
import com.photowall.bean.DataManagerTest;
import com.photowall.photowallcommunity.R;
import com.photowall.ui.quests.ProfileAdapter;
import com.photowall.ui.quests.ProfileMasteryAdapter;

public class PhotoProfileActivity extends Activity {

	private RadioGroup radio_btns_group;
	private GridView mastry_grid;
	private GridView archieve_grid;
	private GridView challeng_grid;
	private ViewGroup archieve_tips;
	private RadioButton radioMastery;
	private RadioButton radioAchieved;
	private RadioButton radioChallenge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.photo_profile_layout);
		initViews();
	}
	
	public void initViews()
	{
		mastry_grid = (GridView) findViewById(R.id.mastry_grid);
		archieve_grid = (GridView) findViewById(R.id.archieve_grid);
		challeng_grid = (GridView) findViewById(R.id.challeng_grid);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.addAll(DataManagerTest.getArchiData());
		ProfileMasteryAdapter adapter = new ProfileMasteryAdapter(list, this);
		mastry_grid.setAdapter(adapter);
		
		ArrayList<Object> list1 = new ArrayList<Object>();
		list1.addAll(DataManagerTest.getArchiData());
		ProfileAdapter adapter1 = new ProfileAdapter(list1, this);
		archieve_grid.setAdapter(adapter1);
		
		ArrayList<Object> list2 = new ArrayList<Object>();
		list2.addAll(DataManagerTest.getArchiData());
		ProfileAdapter adapter2 = new ProfileAdapter(list2, this);
		challeng_grid.setAdapter(adapter2);
		
		archieve_tips = (ViewGroup) findViewById(R.id.archieve_tips);
		
		radio_btns_group = (RadioGroup) findViewById(R.id.radio_btns_group);
		radio_btns_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				disableALLgrid();
				switch (checkedId) {
				   
					case R.id.mastry_radio:
					{
						mastry_grid.setVisibility(View.VISIBLE);
						break;
					}
					case R.id.archieved_radio:
					{
						archieve_grid.setVisibility(View.VISIBLE);
						break;
					}
					case R.id.chalenging_radio:
					{
						challeng_grid.setVisibility(View.VISIBLE);
						break;
					}

				}
			}
		});
		
		radioMastery = (RadioButton) findViewById(R.id.mastry_radio);
		radioAchieved = (RadioButton) findViewById(R.id.archieved_radio);
		radioChallenge = (RadioButton) findViewById(R.id.chalenging_radio);
		
		 AssetManager assets = getAssets();
		 Typeface typeface = Typeface.createFromAsset(assets, "fonts/TrajanPro-Bold.ttf");
		radioMastery.setTypeface(typeface);
		radioAchieved.setTypeface(typeface);
		radioChallenge.setTypeface(typeface);
		
		
		radioMastery.setText(getString(R.string.profile_mastery, 100));
		radioAchieved.setText(getString(R.string.profile_achieved, 100));
		radioChallenge.setText(getString(R.string.profile_challenge, 100));
		
	}
	
	public void disableALLgrid()
	{
		mastry_grid.setVisibility(View.GONE);
		archieve_grid.setVisibility(View.GONE);
		challeng_grid.setVisibility(View.GONE);
		archieve_tips.setVisibility(View.GONE);
		
	}
	
	public void followerList(View view) {
		Intent intent = new Intent(this, FollowerListActivity.class);
		startActivity(intent);
	}
}
