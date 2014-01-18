package com.photowall.photowallcommunity;

import java.text.SimpleDateFormat;
import java.util.Locale;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oauth.OAuth;
import com.oauth.base.BasicActivity;
import com.oauth.base.ConstantS;
import com.photowall.oauth.util.AppContext;
import com.photowall.oauth.util.PlatformAccount;
import com.photowall.oauth.util.PlatformBindConfig;
import com.sina.weibo.sdk.WeiboSDK;
import com.sina.weibo.sdk.api.IWeiboAPI;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.AccessTokenKeeper;


public class LogoActivity extends BasicActivity implements OnClickListener, Callback {
    private ImageButton mFaceBookButton;
    private ImageButton mTwitterButton;
    private TextView mSignUpText;
    private TextView mLoginText;
    private LinearLayout mEnLayout;
    private LinearLayout mCHlLayout;
    private Button mWbButton;
    private Button mQQButton;
    private Button mDbButton;
    private Button mRRButton;
    
    private Handler handler;
    private String platform;
    public OAuth oauth;
    
    
    private Weibo mWeibo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private IWeiboAPI mIWeiboAPI;
    
    
    public static final String CALLBACKURL = "app:authorize";
    
    public static final int oauth_sucess = 1;
    
    
    public ProgressDialog mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        initView();
        
        int screenWidth  = getWindowManager().getDefaultDisplay().getWidth();      
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); 
        PhotoWallApplication.getPhotoWallApplication().setScreenwh(new int[]{screenWidth,screenHeight});
        
    }
    
    private void initView() {
        mEnLayout = (LinearLayout) (LinearLayout)findViewById(R.id.en_layout);
        mCHlLayout = (LinearLayout)(LinearLayout) findViewById(R.id.ch_layout);
        if (isZhLocale()) {
            mCHlLayout.setVisibility(View.VISIBLE);
            mEnLayout.setVisibility(View.GONE);
        }else {
            mEnLayout.setVisibility(View.VISIBLE);
            mCHlLayout.setVisibility(View.GONE);
        }
        mWbButton = (Button) findViewById(R.id.weibo_image);
        mQQButton = (Button) findViewById(R.id.qq_image);
        mDbButton = (Button) findViewById(R.id.douban_image);
        mRRButton = (Button) findViewById(R.id.renren_image);
        mSpinner = new ProgressDialog(this);
        mSpinner.setMessage("Loading...");  
        
        mFaceBookButton = (ImageButton) findViewById(R.id.facebook_image);
        mTwitterButton = (ImageButton) findViewById(R.id.twitter_image);
        mSignUpText = (TextView) findViewById(R.id.sign_up_text);
        mLoginText = (TextView) findViewById(R.id.login_text);
        mSignUpText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mLoginText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mSignUpText.setTextColor(Color.BLUE);
        mLoginText.setTextColor(Color.BLUE);
        mFaceBookButton.setOnClickListener(this);
        mTwitterButton.setOnClickListener(this);
        mSignUpText.setOnClickListener(this);
        mLoginText.setOnClickListener(this);
        mWbButton.setOnClickListener(this);
        mQQButton.setOnClickListener(this);
        mDbButton.setOnClickListener(this);
        mRRButton.setOnClickListener(this);
        mWeibo = Weibo.getInstance(ConstantS.APP_KEY, ConstantS.REDIRECT_URL, ConstantS.SCOPE);
        
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                    .format(new java.util.Date(mAccessToken.getExpiresTime()));
//            mText.setText("access_token 仍在有效期内,无需再次登录: \naccess_token:"
//                    + mAccessToken.getToken() + "\n有效期：" + date);
        } else {
//            mText.setText("使用SSO登录前，请检查手机上是否已经安装新浪微博客户端，"
//                    + "目前仅3.0.0及以上微博客户端版本支持SSO；如果未安装，将自动转为Oauth2.0进行认证");
        }
        initWeiboSDK();
    }
    
    private void initWeiboSDK(){
        //初始化SDK
        mIWeiboAPI = WeiboSDK.createWeiboAPI(this, ConstantS.APP_KEY);
    }
    
    @Override
    public void onClick(View v) {
//        mSpinner.show();
        switch (v.getId()) {
        case R.id.facebook_image:
            break;
        case R.id.twitter_image:
            platform = PlatformBindConfig.Twitter;
            oauth = new OAuth(this, handler, PlatformBindConfig.Twitter,
                    PlatformBindConfig.Twitter_ConsumerKey, PlatformBindConfig.Twitter_ConsumerSecret);
            oauth.requestAcccessTokenForTwitter();
            break;
        case R.id.weibo_image:
            mWeibo.anthorize(LogoActivity.this, new AuthDialogListener());
//            mSsoHandler = new SsoHandler(LogoActivity.this, mWeibo);
//            mSsoHandler.authorize(new AuthDialogListener(), null);
//            platform = PlatformBindConfig.Sina;
//            oauth = new OAuth(this, handler, platform);
//            oauth.requestAccessTokenForOAuth2(PlatformBindConfig.Sina_Authorize2);
            break;
        case R.id.qq_image:
            platform = PlatformBindConfig.QQ;
            oauth = new OAuth(this, handler, platform);
            oauth.requestAccessTokenForOAuth2(PlatformBindConfig.QQ_Authorize);
            break;
        case R.id.douban_image:
            platform = PlatformBindConfig.Douban;
            oauth = new OAuth(this, handler, platform, PlatformBindConfig.Douban_AppKey,
                    PlatformBindConfig.Douban_AppSecret);
            oauth.requestAccessToken(CALLBACKURL, PlatformBindConfig.Douban_Request_Token,
                    PlatformBindConfig.Douban_Access_Token, PlatformBindConfig.Douban_Authorize);
            break;
        case R.id.renren_image:
            platform = PlatformBindConfig.Renren;
            oauth = new OAuth(this, handler, platform);
            oauth.requestAccessTokenForOAuth2(PlatformBindConfig.Renren_Authorize);
            break;
        case R.id.sign_up_text:
            Intent signUpIntent = new Intent();
            signUpIntent.setClass(LogoActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
            break;
        case R.id.login_text:
            Intent loginIntent = new Intent();
            loginIntent.setClass(LogoActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            break;

        default:
            break;
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        mSpinner.dismiss();
    }
    
    private boolean isZhLocale() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }
    
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
        case oauth_sucess:
            mSpinner.hide();
            PlatformAccount account = (PlatformAccount) msg.obj;
            if (account != null) {
            }
            break;

        default:
            break;
        }
        return false;
    }
    
    
    
    
    
    //sina 微博
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // SSO 授权回调
        // 重要：发起 SSO 登陆的Activity必须重写onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    
    class AuthDialogListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            String code = values.getString("code");
            String refresh_token = values.getString("refresh_token");
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            mAccessToken = new Oauth2AccessToken(token, expires_in);
            if (mAccessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        .format(new java.util.Date(mAccessToken.getExpiresTime()));
//                mText.setText("认证成功: \r\n access_token: " + token + "\r\n" + "expires_in: "
//                        + expires_in + "\r\n有效期：" + date);

                AccessTokenKeeper.keepAccessToken(LogoActivity.this, mAccessToken);
                Toast.makeText(LogoActivity.this, "认证成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LogoActivity.this, "认证失败", Toast.LENGTH_SHORT).show();
            }
                
        }

        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(LogoActivity.this, 
                    "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LogoActivity.this, "Auth cancel", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LogoActivity.this, 
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}