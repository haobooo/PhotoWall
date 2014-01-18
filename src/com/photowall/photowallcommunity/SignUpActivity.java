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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends Activity implements OnClickListener{
    private Button mSignUpButton;
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private UserContent userContent;
    
    
    private final int MSG_SIGNUP_FAILED = 0x01;
    private final int MSG_SIGNUP_SUCCESS = 0x02;
    
    private EditText mFristNameEditText;
    private EditText mUserNameEditText;
    private EditText mEmailEditText;
    private EditText mPassWordEditText;
    public ProgressDialog mProgressDialog;
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
            
                case MSG_SIGNUP_FAILED:
                { 
                    String text="";
                    try {
                        if (err.getErrcode()!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "服务器无响应";
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(SignUpActivity.this, text, Toast.LENGTH_SHORT).show();
                    break;
                }
                case MSG_SIGNUP_SUCCESS:
                { 
                    String text="注册成功";
                    Toast.makeText(SignUpActivity.this, text, Toast.LENGTH_SHORT).show();
                    PhotoWallTools.mUserContent = userContent;
                    Intent loginIntent = new Intent();
                    loginIntent.putExtra("achievements", "achievements");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usercontent", userContent);
                    loginIntent.putExtras(bundle);
                    loginIntent.setClass(SignUpActivity.this,SuccessActivity.class);
                    startActivity(loginIntent);
                    finish();
                    break;
                }
            }
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        initView();
    }
    private void initView(){
        mSignUpButton = (Button) findViewById(R.id.sign_up_btn);
        mSignUpButton.setOnClickListener(this);
        mFristNameEditText = (EditText) findViewById(R.id.first_name);
        mUserNameEditText = (EditText) findViewById(R.id.user_name);
        mEmailEditText = (EditText) findViewById(R.id.email_et);
        mPassWordEditText = (EditText) findViewById(R.id.password_et);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在注册...");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.sign_up_btn:
//            Intent signUpIntent = new Intent();
//            signUpIntent.setClass(SignUpActivity.this,SuccessActivity.class);
//            startActivity(signUpIntent);
            if (PhotoWallTools.isNetworkAvailable(getBaseContext())) {
                mProgressDialog.show();
                signUp();
            } else {
                Toast.makeText(getBaseContext(), "网络不可用", Toast.LENGTH_SHORT).show();
            }
            break;

        default:
            break;
        }
    }
    
    public void signUp()
    {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("email", mUserNameEditText.getText().toString());
//                        params.put("password", mUserNameEditText.getText().toString());
                        String userName = mUserNameEditText.getText().toString();
                        String email = mEmailEditText.getText().toString();
                        String password = mPassWordEditText.getText().toString();
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
                           Map<String, String> params1 = new HashMap<String, String>();
                           params1.put("token", "test");
                           params1.put("email", email);
                           params1.put("password", password);
                           httpSession.SignUp(HttpSession.LOGIN_STRING, params1);
                           userContent = httpSession.getUserContent();
                           mainHandler.sendEmptyMessage(MSG_SIGNUP_SUCCESS);
                       }
                    }
                }
                ).start();
    }
}