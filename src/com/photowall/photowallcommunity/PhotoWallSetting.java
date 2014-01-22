package com.photowall.photowallcommunity;


import com.photowall.bean.PhotoWallTools;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class PhotoWallSetting extends Activity {
    /** Called when the activity is first created. */
    public static final String PREF_KEY_NAME = "pref_key_name";
    public static final String PREF_KEY_USER_NAME = "pref_key_username";
    public static final String PREF_KEY_EMAIL = "pref_key_email";
    public static final String PREF_KEY_BIO = "pref_key_bio";
    public static final String PREF_KEY_LOCATION = "pref_key_location";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_layout);
		
		initViews();
    }
    
    private void initViews(){
    	TextView title = (TextView) findViewById(R.id.title_text);
    	title.setText("SETTINGS");
    }

}