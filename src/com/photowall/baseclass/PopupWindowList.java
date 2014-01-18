package com.photowall.baseclass;

public class PopupWindowList{
    private String title;
    private Class<?> gotoClass;
    private boolean isShare;
    private boolean isInviteFriends;
    private boolean isCancel;
    private boolean isDelete;
    private boolean isReportAnIssue;
    
    public PopupWindowList(String title, Class<?> gotoClass){
        this.title = title;
        this.gotoClass = gotoClass;
//        this.isShare = isShare;
//        this.isInviteFriends = isInviteFriends;
//        this.isCancel = isCancel;
//        this.isDelete = isDelete;
//        this.isReportAnIssue = isReporAnIssue;
    }
    public boolean isShare() {
        return isShare;
    }
    public void setShare(boolean isShare) {
        this.isShare = isShare;
    }
    public boolean isInviteFriends() {
        return isInviteFriends;
    }
    public void setInviteFriends(boolean isInviteFriends) {
        this.isInviteFriends = isInviteFriends;
    }
    public boolean isCancel() {
        return isCancel;
    }
    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }
    public boolean isDelete() {
        return isDelete;
    }
    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
    public boolean isReportAnIssue() {
        return isReportAnIssue;
    }
    public void setReportAnIssue(boolean isReportAnIssue) {
        this.isReportAnIssue = isReportAnIssue;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Class<?> getClsString() {
        return gotoClass;
    }
    public void setClsString(Class<?> clsString) {
        this.gotoClass = clsString;
    }
} 