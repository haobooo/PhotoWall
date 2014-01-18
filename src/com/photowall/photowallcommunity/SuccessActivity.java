package com.photowall.photowallcommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SuccessActivity extends Activity implements OnClickListener{
    private Button mOkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        initView();
    }
    private void initView(){
        mOkButton = (Button) findViewById(R.id.ok_btn);
        mOkButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ok_btn:
            Intent okIntent = getIntent();
            okIntent.setClass(SuccessActivity.this, HomePageActivity.class);
            startActivity(okIntent);
            finish();
            break;

        default:
            break;
        }
    }
}