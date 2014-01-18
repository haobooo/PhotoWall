package com.photowall.widget.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.photowall.photowallcommunity.R;
import com.photowall.ui.earchievement.ArchievementDetailsActivity;

public class DialogUtils {

	public static void showPostSelectDialog(Context context,View v,final Handler handler)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.post_sel_pop_layout, null);
		view.setLayoutParams(new ViewGroup.LayoutParams(0,0));
		view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		
		ImageView story = (ImageView) view.findViewById(R.id.story);
		ImageView photo = (ImageView) view.findViewById(R.id.photo);
		
		
		final PopupWindow popupWindow = new PopupWindow(view, view.getMeasuredWidth(),view.getMeasuredHeight());
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(v);
		
		story.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(handler!=null)
				{
					handler.sendEmptyMessage(ArchievementDetailsActivity.MSG_POST_SOTRY);
				}
				popupWindow.dismiss();
			}
		});
		photo.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(handler!=null)
				{
					handler.sendEmptyMessage(ArchievementDetailsActivity.MSG_POST_PHOTO);
				}
				popupWindow.dismiss();
			}
		});
		
		
		
	}
}
