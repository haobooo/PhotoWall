package com.photowall.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.photowall.net.ErrCode;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.R;
import com.photowall.tools.Constants;

public class ModifyUserActivity extends Activity {

	private TextView nextbtn;
	
	private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;
    private GraphUser graphUser;
    
    private TextView username;
    private TextView email;
    private boolean isfb = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_user_layout);
		isfb = getIntent().getBooleanExtra("fblogin", false);
		app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        userContent = app.getUserContent();
        
		initviews();
	}
	
	public void initviews()
	{
		nextbtn = (TextView) findViewById(R.id.nextbtn);
		nextbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ModifyUserActivity.this,QuestPageActivity.class);
				intent.putExtra(Constants.IS_REGISTING, true);
				startActivity(intent);
				finish();
			}
		});
		
		username = (TextView) findViewById(R.id.username);
		email = (TextView) findViewById(R.id.email);
		
		if(!isfb)
		{
			username.setText(userContent.getUsename());
			email.setText(userContent.getUserEmail());
		}
		
	}
}
