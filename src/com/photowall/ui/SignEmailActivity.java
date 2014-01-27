package com.photowall.ui;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.photowall.bean.PhotoWallTools;
import com.photowall.net.ErrCode;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.R;
import com.photowall.photowallcommunity.SignUpActivity;
import com.photowall.photowallcommunity.SuccessActivity;
import com.photowall.tools.Constants;
import com.photowall.tools.NetProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignEmailActivity extends Activity 
implements View.OnClickListener
{
	private View signemail;
	
	private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;
    
    NetProgressDialog netProgressDialog;
    private EditText et_login_username;
    private EditText et_login_email;
    private EditText et_login_password;
	
    private final int MSG_SIGNUP_FAILED = 0x01;
    private final int MSG_SIGNUP_SUCCESS = 0x02;
    
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_SIGNUP_FAILED:
                { 
                	netProgressDialog.dismiss();
                    String text="";
                    
                    try {
                        if (err.getErrcode()!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "����������Ӧ";
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    break;
                }
                case MSG_SIGNUP_SUCCESS:
                { 
                	netProgressDialog.dismiss();
                	
                    String text="ע��ɹ�";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    
                    userContent.setIslogin(true);
                    userContent.setUsename(et_login_username.getText().toString().trim());
                    userContent.setUserEmail(et_login_email.getText().toString().trim());
                    
        			Intent intent = new Intent(SignEmailActivity.this,ModifyUserActivity.class);
        			startActivity(intent);
                    finish();
                    break;
                }
            }
        };
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sign_email_layout);
		app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        userContent = app.getUserContent();
        
        netProgressDialog = new NetProgressDialog(this);
		initviews();
	}
	
	public void initviews()
	{
		signemail = findViewById(R.id.signemail);
		signemail.setOnClickListener(this);
		
		et_login_username = (EditText) findViewById(R.id.et_login_username);
		et_login_email = (EditText) findViewById(R.id.et_login_email);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		
		
	}

	public boolean checkNotnull()
	{
		if(et_login_username.getText().toString().trim().equals("")
		|| et_login_email.getText().toString().trim().equals("")
		|| et_login_password.getText().toString().trim().equals("")
		)
		{
			return false;
		}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.signemail:
		{
			if(PhotoWallTools.isNetworkAvailable(getBaseContext()) || true)
			{
				if(checkNotnull())
				{
					signUp();
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
			break;

		default:
			break;
		}
	}
	
	public void signUp()
    {
		netProgressDialog.show();
		
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                    	
                    	//test
                    	if(!PhotoWallApplication.getPhotoWallApplication().isNet())
                    	{
	                    	mainHandler.sendEmptyMessage(MSG_SIGNUP_SUCCESS);
	                    	if(true)
	                    	{
	                    		return;
	                    	}
                    	}
                        Map<String, String> params = new HashMap<String, String>();
                        String userName = et_login_username.getText().toString().trim();
                        String email = et_login_email.getText().toString().trim();
                        String password = et_login_password.getText().toString().trim();
                        
                        params.put("typeid", "0");
                        params.put("username",userName);
                        params.put("email", email);
                        params.put("password", password);
                        
                       boolean flag =  httpSession.SignUp(HttpSession.SIGNUP_STRING, params);
                        
                       if(!flag)
                       {
                          err =  httpSession.getErrCode();
                          mainHandler.sendEmptyMessage(MSG_SIGNUP_FAILED);
                       } else {
//                           Map<String, String> params1 = new HashMap<String, String>();
//                           params1.put("token", "test");
//                           params1.put("email", email);
//                           params1.put("password", password);
//                           httpSession.SignUp(HttpSession.LOGIN_STRING, params1);
                    	   PhotoWallApplication.getPhotoWallApplication().login();
                           userContent.setIslogin(true);
                           userContent.setUserId(httpSession.getUserContent().getUserId());
                           mainHandler.sendEmptyMessage(MSG_SIGNUP_SUCCESS);
                       }
                    }
                }
                ).start();
    }
}
