package com.photowall.baseclass;

import android.graphics.Bitmap;


public class ProfileInfo{
    public Bitmap mImageBitmap;
    public String achievementTitle;
    public String achievedNum;
    public String challengingNum;
    public String achievementMark;
    public String location;
    public String achievementInfo;
    
    
    public  ProfileInfo() {
        
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public void setImageBitmap(Bitmap mImageBitmap) {
        this.mImageBitmap = mImageBitmap;
    }

    public String getAchievementTitle() {
        return achievementTitle;
    }

    public void setAchievementTitle(String achievement) {
        this.achievementTitle = achievement;
    }

    public String getAchievedNum() {
        return achievedNum;
    }

    public void setAchievedNum(String achievedNum) {
        this.achievedNum = achievedNum;
    }

    public String getChallengingNum() {
        return challengingNum;
    }

    public void setChallengingNum(String challengingNum) {
        this.challengingNum = challengingNum;
    }
    
    public String getAchievementMark() {
        return achievementMark;
    }

    public void setAchievementMark(String achievementMark) {
        this.achievementMark = achievementMark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAchievementInfo() {
        return achievementInfo;
    }

    public void setAchievementInfo(String achievementInfo) {
        this.achievementInfo = achievementInfo;
    }
    
}