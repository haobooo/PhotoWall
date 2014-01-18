package com.photowall.widget.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.GridView;

public class ScrollGridView extends GridView {
	
	private Handler mainhaHandler;
	private boolean isScrolled = false;
	
	public void setMainhaHandler(Handler mainhaHandler) {
		this.mainhaHandler = mainhaHandler;
	}

	public ScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ScrollGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {      
        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
        if(mainhaHandler!=null && !isScrolled)
        {
        	isScrolled = true;
        	mainhaHandler.sendEmptyMessage(0);
        }
    } 
}
