package com.photowall.photowallcommunity;


import com.photowall.bean.PhotoWallTools;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Window;

public class PhotoWallSetting extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {
    /** Called when the activity is first created. */
    public static final String PREF_KEY_NAME = "pref_key_name";
    public static final String PREF_KEY_USER_NAME = "pref_key_username";
    public static final String PREF_KEY_EMAIL = "pref_key_email";
    public static final String PREF_KEY_BIO = "pref_key_bio";
    public static final String PREF_KEY_LOCATION = "pref_key_location";
    
    
    private EditTextPreference mNamePreference;
    private EditTextPreference mUserNamePreference;
    private EditTextPreference mEmailPreference;
    private EditTextPreference mBioPreference;
    private EditTextPreference mLocationPreference;
    private UserContent mUserContent;
    private PhotoWallApplication app;
    
//    private HttpSession httpSession;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_combined);
        app = PhotoWallApplication.getPhotoWallApplication();
//        httpSession = app.getHttpSession();
        initView();
        initData();
    }
    
    private void initView(){
        mNamePreference = (EditTextPreference) findPreference(PREF_KEY_NAME);
//        mNamePreference.setText(mNamePreference.getSummary().toString());
        mNamePreference.setOnPreferenceChangeListener(this);
        mNamePreference.setOnPreferenceClickListener(this);
        
        mUserNamePreference = (EditTextPreference) findPreference(PREF_KEY_USER_NAME);
//        mUserNamePreference.setText(mUserNamePreference.getSummary().toString());
        mUserNamePreference.setOnPreferenceChangeListener(this);
        mUserNamePreference.setOnPreferenceClickListener(this);
        
        mEmailPreference = (EditTextPreference) findPreference(PREF_KEY_EMAIL);
//        mEmailPreference.setText(mEmailPreference.getSummary().toString());
        mEmailPreference.setOnPreferenceChangeListener(this);
        mEmailPreference.setOnPreferenceClickListener(this);
        
        mBioPreference = (EditTextPreference) findPreference(PREF_KEY_BIO);
//        mBioPreference.setText(mBioPreference.getSummary().toString());
        mBioPreference.setOnPreferenceChangeListener(this);
        mBioPreference.setOnPreferenceClickListener(this);
        
        mLocationPreference = (EditTextPreference) findPreference(PREF_KEY_LOCATION);
//        mLocationPreference.setText(mLocationPreference.getSummary().toString());
        mLocationPreference.setOnPreferenceChangeListener(this);
        mLocationPreference.setOnPreferenceClickListener(this);
    }
    
    private void initData(){
//        mUserContent = httpSession.getUserContent();
        if (mUserContent != null) {
            mUserNamePreference.setSummary(mUserContent.getUserNickName());
            mEmailPreference.setSummary(mUserContent.getUserEmail());
            mBioPreference.setSummary(mUserContent.getUserTitle());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mNamePreference || preference == mUserNamePreference
                || preference == mEmailPreference || preference == mBioPreference
              || preference == mLocationPreference) {
            preference.setSummary(newValue.toString());
            ((EditTextPreference) preference).setText(newValue.toString());
        }
        
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == mNamePreference || preference == mUserNamePreference
                || preference == mEmailPreference || preference == mBioPreference
              || preference == mLocationPreference) {
//            mNamePreference.setText(mNamePreference.getSummary().toString());
//            mUserNamePreference.setText(mUserNamePreference.getSummary().toString());
//            mEmailPreference.setText(mEmailPreference.getSummary().toString());
//            mBioPreference.setText(mBioPreference.getSummary().toString());
//            mLocationPreference.setText(mLocationPreference.getSummary().toString());
        }
        return false;
    }
}