package com.photowall.photowallcommunity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.photowall.bean.PhotoWallTools;
import com.photowall.net.ErrCode;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity implements OnClickListener{
    private Button mLoginButton;
    
    private final int MSG_LOGIN_FAILED = 0x01;
    private final int MSG_LOGIN_SUCCESS = 0x02;
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;
    
    
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    
    public ProgressDialog mProgressDialog;
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
            
                case MSG_LOGIN_FAILED:
                { 
                    String text="";
                    try {
                        if (err!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "服务器无响应";
                        }
                        
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
                    break;
                }
                
                case MSG_LOGIN_SUCCESS:
                { 
                    String text="登陆成功";
                    Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
                    PhotoWallTools.mUserContent = userContent;
                    PhotoWallApplication.getPhotoWallApplication().login();
                    //by black crystal
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    boolean isfirst = sp.getBoolean("isfirst", true);
                    if(isfirst)
                    {
                    	sp.edit().putBoolean("isfirst", false).commit();
                    	Intent loginIntent = new Intent();
                        loginIntent.putExtra("achievements", "achievements");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usercontent", userContent);
                        loginIntent.putExtras(bundle);
                        loginIntent.setClass(LoginActivity.this,SuccessActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                    else
                    {
                    	Intent okIntent = getIntent();
                    	okIntent.putExtra("achievements", "achievements");
                        okIntent.setClass(LoginActivity.this, HomePageActivity.class);
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
    }
    private void initView(){
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mLoginButton.setOnClickListener(this);
        mUserNameEditText = (EditText) findViewById(R.id.user_name);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在登陆...");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.login_btn:
        {
        	// by black crystal
        	//check network available
            if (PhotoWallTools.isNetworkAvailable(getBaseContext())) 
            {
            	//check data integrity.
            	if(checkNull())
            	{
	                mProgressDialog.show();
	                login();
            	}
            	
            } else {
                Toast.makeText(getBaseContext(), getString(R.string.tip_net_not_availables), 
                		Toast.LENGTH_SHORT).show();
            }
            
            break;
        }
        default:
            break;
        }
    }
    /**
     * check data before login 
     * @return
     */
    public boolean checkNull()
    {
    	if(mUserNameEditText.getText().toString().trim().equals("")
    	|| 	mPasswordEditText.getText().toString().trim().equals("")	
    	)
    	{
    		Toast.makeText(this, getString(R.string.tip_not_null_pass_name), 
    				Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	return true;
    }
    /**
     * login
     */
    public void login()
    {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", mUserNameEditText.getText().toString());
                        params.put("password", mPasswordEditText.getText().toString());
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