package com.photowall.ui;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.photowall.PhotoHomePageActivity;
import com.photowall.bean.PhotoWallTools;
import com.photowall.net.ErrCode;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.photowallcommunity.HomePageActivity;
import com.photowall.photowallcommunity.LoginActivity;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.R;
import com.photowall.photowallcommunity.SuccessActivity;
import com.photowall.tools.NetProgressDialog;

public class LoginEmailActivity extends Activity {
	
	
    private final int MSG_LOGIN_FAILED = 0x01;
    private final int MSG_LOGIN_SUCCESS = 0x02;
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;
    private NetProgressDialog netProgressDialog;
	
    private EditText et_login_email;
    private EditText et_login_password;
    private View loginbtn;
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            
                case MSG_LOGIN_FAILED:
                { 
                	netProgressDialog.dismiss();
                	
                    String text="";
                    try {
                        if (err!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "";
                        }
                        
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    break;
                }
                
                case MSG_LOGIN_SUCCESS:
                { 
                	netProgressDialog.dismiss();
                	
                    String text="success";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    PhotoWallTools.mUserContent = userContent;
                    PhotoWallApplication.getPhotoWallApplication().login();
                    //by black crystal
//                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
//                    boolean isfirst = sp.getBoolean("isfirst", true);
//                    if(isfirst)
//                    {
//                    	sp.edit().putBoolean("isfirst", false).commit();
//                    	Intent loginIntent = new Intent();
//                        loginIntent.putExtra("achievements", "achievements");
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("usercontent", userContent);
//                        loginIntent.putExtras(bundle);
//                        loginIntent.setClass(LoginEmailActivity.this,SuccessActivity.class);
//                        startActivity(loginIntent);
//                        finish();
//                    }
//                    else
                    {
                    	Intent okIntent = getIntent();
                        okIntent.setClass(LoginEmailActivity.this, PhotoHomePageActivity.class);
                        startActivity(okIntent);
                        finish();
                    }
                    
                   
                    break;
                }
            }
        };
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_email_layout);
		
		 app = PhotoWallApplication.getPhotoWallApplication();
	     httpSession = app.getHttpSession();
	     userContent = app.getUserContent();
	     
		initViews();
		
	}
	public boolean checkNotnull()
	{
		if(
		et_login_email.getText().toString().trim().equals("")
		|| et_login_password.getText().toString().trim().equals("")
		)
		{
			return true;
		}
		return true;
	}
	
	public void initViews()
	{
		netProgressDialog = new NetProgressDialog(this);
		et_login_email = (EditText) findViewById(R.id.et_login_email);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		loginbtn = findViewById(R.id.loginbtn);
		loginbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(PhotoWallTools.isNetworkAvailable(getBaseContext()) || true)
				{
					if(checkNotnull())
					{
						login();
					}
					else
					{
						Toast.makeText(getBaseContext(), getString(R.string.tip_not_null_pass_name), 
								Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(getBaseContext(), getString(R.string.net_accessable_no), Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
	}
	
	public void login()
    {
		netProgressDialog.show();
		
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                    	
                    	//test
                    	if(!PhotoWallApplication.getPhotoWallApplication().isNet())
                    	{
	                    	mainHandler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
	                    	if(true)
	                    	{
	                    		return;
	                    	}
                    	}
                    	
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", et_login_email.getText().toString());
                        params.put("password", et_login_password.getText().toString());
                        params.put("token", "test");
                       boolean flag =  httpSession.Login(HttpSession.LOGIN_STRING, params);
                       if(!flag)
                       {
                           err =  httpSession.getErrCode();
                           mainHandler.sendEmptyMessage(MSG_LOGIN_FAILED);
                       } else {
                           userContent = httpSession.getUserContent();
                           httpSession.setUserContent(userContent);
                           app.setUserContent(userContent);
                           mainHandler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
                       }
                       
                    }
                }
                ).start();
    }
}
