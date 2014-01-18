package com.photowall.bean;


public class ArchievementPostBean {

//    holder.userName = (TextView) convertView.findViewById(R.id.achm_user_name);
//    holder.imageInfo = (TextView) convertView.findViewById(R.id.achm_comment_text);
//    holder.time = (TextView) convertView.findViewById(R.id.achm_time_text);
//    holder.achieveImage = (ImageView) convertView.findViewById(R.id.achm_image);
//    holder.comment = (TextView) convertView.findViewById(R.id.achm_comment);
    
    
    private String username;
    private String imageinfo;
    private String time;
    private String commentString;
    private String url;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getImageinfo() {
        return imageinfo;
    }
    public void setImageinfo(String imageinfo) {
        this.imageinfo = imageinfo;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getCommentString() {
        return commentString;
    }
    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
