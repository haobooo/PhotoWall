package com.photowall.tools;

import android.app.Activity;
import android.content.Context;
import android.view.Display;

public class ScreenInfo {

	private static int screenw;
	private static int screenh;
	public static void init(Activity context)
	{
		Display display = context.getWindowManager().getDefaultDisplay();  
		screenw = display.getWidth();
		screenh = display.getHeight();
	}
	public static int getScreenw() {
		return screenw;
	}
	public static int getScreenh() {
		return screenh;
	}
	
}
