package com.photowall.widget.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.photowall.photowallcommunity.R;

public class LoadingManager {

	private static Dialog loadingDialog;
	private static  Animation hyperspaceJumpAnimation;
	private static ImageView spaceshipImage;
	
	private static LoadingManager loadingManager = null;
	
	public static LoadingManager getInstance(Context context)
	{
		if(loadingManager == null)
		{
			loadingManager = new LoadingManager();
			
		}
		initDialog(context);
		return loadingManager;
	}
	private  static void initDialog(Context context)
	{
		LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  
        // main.xml中的ImageView  
        spaceshipImage = (ImageView) v.findViewById(R.id.img);  
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.loading_animation);  
        // 使用ImageView显示动画  
        loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog  
  
        loadingDialog.setCancelable(false); 
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.FILL_PARENT,  
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局  
	}
	
	public void showLoading()
	{
		if(!hyperspaceJumpAnimation.hasStarted())
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
		loadingDialog.show();
	}
	public void dismissLoading()
	{
		hyperspaceJumpAnimation.cancel();
		loadingDialog.dismiss();
	}
	
}

