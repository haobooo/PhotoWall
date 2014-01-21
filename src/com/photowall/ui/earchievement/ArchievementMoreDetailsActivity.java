package com.photowall.ui.earchievement;

import com.photowall.photowallcommunity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class ArchievementMoreDetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.archievement_more_details_layout);
		
		initViews();
	}
	
	private void initViews() {
		
	}
}