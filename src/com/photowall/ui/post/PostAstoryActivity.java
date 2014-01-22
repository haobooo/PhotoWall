package com.photowall.ui.post;

import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAstoryActivity extends Activity 
implements View.OnClickListener
{
	private ImageView settingsImage;
	private TextView title_text;
	private ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.post_story_layout);
		initviews();
	}
	
	public void initviews()
	{
		settingsImage = (ImageView) findViewById(R.id.title_settings);
		settingsImage.setOnClickListener(this);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("POST A STORY");
		backImage = (ImageView) findViewById(R.id.title_back);
		backImage.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
		}
	}
	public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
}
