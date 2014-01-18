package com.photowall.baseclass;

import java.io.Serializable;

import android.graphics.Bitmap;

public class PostInfo implements Serializable{
    private static final long serialVersionUID = -199534327972481952L;
    String piUserName;
    Bitmap userBitmap;
    Bitmap piBitmap;
    String comment;
    String time;
    String likeNum;
    String otherComment;
    
    
    public String getPiUserName() {
        return piUserName;
    }
    public void setPiUserName(String piUserName) {
        this.piUserName = piUserName;
    }
    public Bitmap getUserBitmap() {
        return userBitmap;
    }
    public void setUserBitmap(Bitmap userBitmap) {
        this.userBitmap = userBitmap;
    }
    public Bitmap getPiBitmap() {
        return piBitmap;
    }
    public void setPiBitmap(Bitmap piBitmap) {
        this.piBitmap = piBitmap;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLikeNum() {
        return likeNum;
    }
    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
    public String getOtherComment() {
        return otherComment;
    }
    public void setOtherComment(String otherComment) {
        this.otherComment = otherComment;
    }
}