package com.photowall.ui;

import org.json.JSONException;
import org.json.JSONObject;


import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.oauth.base.BasicActivity;
import com.photowall.oauth.util.AppContext;
import com.photowall.oauth.util.PlatformAccount;
import com.photowall.oauth.util.PlatformBindConfig;
import com.photowall.photowallcommunity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


//	APP ID: 
//	1423764471191329 
//	APP SECRET :
//	1a2c995908f125ae893b7988f44d3768 
//	Client Token:
//	035d6b53b56b85dc765c122c80a84b08 

public class MainLoginActivity extends Activity 
implements View.OnClickListener
{
	
	 private Session.StatusCallback statusCallback = new SessionStatusCallback();
	 
	private ImageView sign_email;
	private ImageView sign_fb;
	private TextView login_txt;

	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppContext.setContext(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_login_layout);
		//facebook
		initFacebook(savedInstanceState);
		initviews();
		
	}
	
	public void initFacebook(Bundle savedInstanceState)
	{
		 Settings.addLoggingBehavior(LoggingBehavior.REQUESTS);

	        Session session = Session.getActiveSession();
	        if (session == null) {
	            if (savedInstanceState != null) {
	                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
	            }
	            if (session == null) {
	                session = new Session(this);
	            }
	            Session.setActiveSession(session);
	            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	            }
	        }
	}
	public void initviews()
	{
		sign_email = (ImageView) findViewById(R.id.sign_email);
		sign_email.setOnClickListener(this);
		sign_fb = (ImageView) findViewById(R.id.sign_fb);
		sign_fb.setOnClickListener(this);
		login_txt = (TextView) findViewById(R.id.login_txt);
		login_txt.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sign_email:
		{
			Intent intent = new Intent(MainLoginActivity.this,SignEmailActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.sign_fb:
		{
			onClickLogin();
			break;
		}
		case R.id.login_txt:
		{
			Intent intent = new Intent(MainLoginActivity.this,LoginEmailActivity.class);
			startActivity(intent);
			break;
		}
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	 @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        Session session = Session.getActiveSession();
	        Session.saveSession(session, outState);
	    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Session.getActiveSession().removeCallback(statusCallback);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 Session.getActiveSession().addCallback(statusCallback);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	 private void onClickLogin() {
	        Session session = Session.getActiveSession();
	        if (!session.isOpened() && !session.isClosed()) {
	            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	        } else {
	            Session.openActiveSession(this, true, statusCallback);
	        }
	    }

	    private void onClickLogout() {
	        Session session = Session.getActiveSession();
	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	        }
	    }
	private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        	
        	if (state.isOpened()) //success
        	{
                 
     			Intent intent = new Intent(MainLoginActivity.this,ModifyUserActivity.class);
     			intent.putExtra("fblogin", true);
     			startActivity(intent);
                finish();
        	}
        	else if(state.isClosed())
        	{
        		Toast.makeText(MainLoginActivity.this, "Login to create a link to fetch account data", Toast.LENGTH_SHORT).show();
        	}
        }
    }
}
