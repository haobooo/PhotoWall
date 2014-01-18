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

public class CheckinActivity extends Activity 
implements View.OnClickListener
{

	private ImageView menu_image;
	private TextView title_text;
	private ImageView back_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.check_in_layout);
		initviews();
	}
	public void initviews()
	{
		menu_image = (ImageView) findViewById(R.id.menu_image);
		menu_image.setOnClickListener(this);
		
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("CHECK IN");
		
		back_image = (ImageView) findViewById(R.id.back_image);
		back_image.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.back_image:
			{
				finish();
				
				break;
			}
			case R.id.menu_image:
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
