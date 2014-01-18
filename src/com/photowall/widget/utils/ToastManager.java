package com.photowall.widget.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.photowall.photowallcommunity.R;

public class ToastManager {

	public static void showToast(Context context)
	{
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View view = LayoutInflater.from(context).inflate(R.layout.successtoast, null);
		toast.setView(view);
		
		toast.show();
	}
}
