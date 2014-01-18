package com.photowall.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.photowall.photowallcommunity.R;

public class PhotoProfileActivity extends Activity {

	private RadioGroup radio_btns_group;
	private GridView mastry_grid;
	private GridView archieve_grid;
	private GridView challeng_grid;
	private ViewGroup archieve_tips;
	
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
						archieve_tips.setVisibility(View.VISIBLE);
						break;
					}
					case R.id.chalenging_radio:
					{
						archieve_tips.setVisibility(View.VISIBLE);
						break;
					}

				}
			}
		});
	}
	
	public void disableALLgrid()
	{
		mastry_grid.setVisibility(View.GONE);
		archieve_grid.setVisibility(View.GONE);
		challeng_grid.setVisibility(View.GONE);
		archieve_tips.setVisibility(View.GONE);
		
	}
}
