package com.photowall.ui.quests;

import android.graphics.Bitmap;

public class QuestObject {

	
	private String title;
	private String archiNumber;
	private Bitmap bitmap;
	private String url;
	private boolean following;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArchiNumber() {
		return archiNumber;
	}
	public void setArchiNumber(String archiNumber) {
		this.archiNumber = archiNumber;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isFollowing() {
		return following;
	}
	public void setFollowing(boolean following) {
		this.following = following;
	}
	
}
