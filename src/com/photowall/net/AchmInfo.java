package com.photowall.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

public class AchmInfo implements Serializable {
    private String achid;
    private String achiname;
    private String achides;
    private String achiimg;
    private String achivideo;
    private String achivideosource;
    private String achiviewcount;
    private String achifollowcount;
    private String achicompletecount;
    private String achicreatedate;
    private String lsname;
    private String lsid;
    private String userid;
    private String userfolder;
    private String usernickname;
    private String userimg;
    private List<HomePostInfo> homePostInfos;
    
    
    public String getAchid() {
        return achid;
    }
    public void setAchid(String achid) {
        this.achid = achid;
    }
    public String getAchiname() {
        return achiname;
    }
    public void setAchiname(String achiname) {
        this.achiname = achiname;
    }
    public String getAchides() {
        return achides;
    }
    public void setAchides(String achides) {
        this.achides = achides;
    }
    public String getAchiimg() {
        return achiimg;
    }
    public void setAchiimg(String achiimg) {
        this.achiimg = achiimg;
    }
    public String getAchivideo() {
        return achivideo;
    }
    public void setAchivideo(String achivideo) {
        this.achivideo = achivideo;
    }
    public String getAchivideosource() {
        return achivideosource;
    }
    public void setAchivideosource(String achivideosource) {
        this.achivideosource = achivideosource;
    }
    public String getAchiviewcount() {
        return achiviewcount;
    }
    public void setAchiviewcount(String achiviewcount) {
        this.achiviewcount = achiviewcount;
    }
    public String getAchifollowcount() {
        return achifollowcount;
    }
    public void setAchifollowcount(String achifollowcount) {
        this.achifollowcount = achifollowcount;
    }
    public String getAchicompletecount() {
        return achicompletecount;
    }
    public void setAchicompletecount(String achicompletecount) {
        this.achicompletecount = achicompletecount;
    }
    public String getAchicreatedate() {
        return achicreatedate;
    }
    public void setAchicreatedate(String achicreatedate) {
        this.achicreatedate = achicreatedate;
    }
    public String getLsname() {
        return lsname;
    }
    public void setLsname(String lsname) {
        this.lsname = lsname;
    }
    public String getLsid() {
        return lsid;
    }
    public void setLsid(String lsid) {
        this.lsid = lsid;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUserfolder() {
        return userfolder;
    }
    public void setUserfolder(String userfolder) {
        this.userfolder = userfolder;
    }
    public String getUsernickname() {
        return usernickname;
    }
    public void setUsernickname(String usernickname) {
        this.usernickname = usernickname;
    }
    public String getUserimg() {
        return userimg;
    }
    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }
    
    public Bitmap getUserimgIcon() {
        return returnBitMap(userimg);
    }
    
    public List<HomePostInfo> getHomePostInfos() {
        return homePostInfos;
    }
    public void setHomePostInfos(List<HomePostInfo> homePostInfos) {
        this.homePostInfos = homePostInfos;
    }
    
    private Bitmap returnBitMap(String urlString) {
        if (TextUtils.isEmpty(urlString)) {
            return null;
        }
        URL myFileUrl = null;
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
        myFileUrl = new URL(urlString);
        } catch (MalformedURLException e) {
        e.printStackTrace();
        }
        try {
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
        e.printStackTrace();
        }finally{
            conn.disconnect();
        }
        //Log.d(TAG, "before compress bitmap size is ==="+bitmap.getByteCount());
        return bitmap; 
    }

    
}
