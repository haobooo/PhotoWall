package com.photowall.net;

import java.io.Serializable;

import android.R.integer;


//result:{"content":{"userid":"23089315173217947","usertoken":"test",
//"useremail":"lzd_it@163.com","userfirstname":null,"userlastname":null,"usernickname":"test","userfolder":"8d6f4fba9a38333db6f74eaf19fd68ce",
//"userimgurl":"default_profile_img.jpg","usertitle":"Hello World","userfbid":null,"userfbname":null,"usertype":"0","userlogincount":"2",
//"userfollowercount":"0","userpostcount":"0","userlastloginsource":"0","userlastlogindate":"2013-10-07 04:21:23"}}
public class UserContent implements Serializable{
    private static final long serialVersionUID = -490619186015971861L;
    
    private String userId;
    private String userToken;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String userNickName;
    private String userFolder;
    private String userImgurl;
    private String userTitle;
    private String userFBId;
    private String userFBName;
    private String userType;
    private String userLogInCount;
    private String userFollowerCount;
    private String userPostcount;
    private String userLastLoginSource;
    private String userLastLoginDate;
    
    private String usename;
    private boolean islogin;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserFirstName() {
        return userFirstName;
    }
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }
    public String getUserLastName() {
        return userLastName;
    }
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
    public String getUserNickName() {
        return userNickName;
    }
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
    public String getUserFolder() {
        return userFolder;
    }
    public void setUserFolder(String userFolder) {
        this.userFolder = userFolder;
    }
    public String getUserImgurl() {
        return userImgurl;
    }
    public void setUserImgurl(String userImgurl) {
        this.userImgurl = userImgurl;
    }
    public String getUserTitle() {
        return userTitle;
    }
    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }
    public String getUserFBId() {
        return userFBId;
    }
    public void setUserFBId(String userFBId) {
        this.userFBId = userFBId;
    }
    public String getUserFBName() {
        return userFBName;
    }
    public void setUserFBName(String userFBName) {
        this.userFBName = userFBName;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserLogInCount() {
        return userLogInCount;
    }
    public void setUserLogInCount(String userLogInCount) {
        this.userLogInCount = userLogInCount;
    }
    public String getUserFollowerCount() {
        return userFollowerCount;
    }
    public void setUserFollowerCount(String userFollowerCount) {
        this.userFollowerCount = userFollowerCount;
    }
    public String getUserPostcount() {
        return userPostcount;
    }
    public void setUserPostcount(String userPostcount) {
        this.userPostcount = userPostcount;
    }
    public String getUserLastLoginSource() {
        return userLastLoginSource;
    }
    public void setUserLastLoginSource(String userLastLoginSource) {
        this.userLastLoginSource = userLastLoginSource;
    }
    public String getUserLastLoginDate() {
        return userLastLoginDate;
    }
    public void setUserLastLoginDate(String userLastLoginDate) {
        this.userLastLoginDate = userLastLoginDate;
    }
	public String getUsename() {
		return usename;
	}
	public void setUsename(String usename) {
		this.usename = usename;
	}
	public boolean isIslogin() {
		return islogin;
	}
	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}
    
    
}