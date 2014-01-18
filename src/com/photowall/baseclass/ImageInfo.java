package com.photowall.baseclass;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;


public class ImageInfo implements Serializable{
    private static final long serialVersionUID = 9206292662261131229L;
    public Bitmap mImageBitmap;
    public String mUserName;
    public String mImageInfo;
    public String mLocal;
    public List<PostInfo> mPostInfos;
    public PostInfo postInfo;
    public String mImageMark;
    public String mImageTitle;
    
    
    public ImageInfo(String userName,String imageInfo,String local){
        this.mUserName = userName;
        this.mImageInfo = imageInfo;
        this.mLocal = local;
    }
    
    public List<PostInfo> getPostInfos() {
        return mPostInfos;
    }

    public void setPostInfos(List<PostInfo> postInfos) {
        this.mPostInfos = postInfos;
    }

    public  ImageInfo() {
        
    }
    
    public String getUserName() {
        return mUserName;
    }
    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }
    public String getImageInfo() {
        return mImageInfo;
    }
    public void setImageInfo(String mImageInfo) {
        this.mImageInfo = mImageInfo;
    }
    public String getLocal() {
        return mLocal;
    }
    public void setLocal(String mLocal) {
        this.mLocal = mLocal;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public void setImageBitmap(Bitmap mImageBitmap) {
        this.mImageBitmap = mImageBitmap;
    }
    
    public PostInfo getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
    }
    
    public String getImageMark() {
        return mImageMark;
    }

    public void setImageMark(String imageMark) {
        this.mImageMark = imageMark;
    }
    
    public String getImageTitle() {
        return mImageTitle;
    }

    public void setImageTitle(String mImageTitle) {
        this.mImageTitle = mImageTitle;
    }
}