package com.photowall.tools;

import com.photowall.photowallcommunity.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class NetProgressDialog {

	private Context mcontext;
	private ProgressDialog pd;
	private LayoutInflater inflater;
	
	public NetProgressDialog(Context context)
	{
		this.mcontext = context;
		inflater = LayoutInflater.from(context);
		initDialog();
	}
	public void initDialog()
	{
		pd = new ProgressDialog(mcontext);
		pd.setCanceledOnTouchOutside(false);
//		pd.setCancelable(false);
		
	}
	public void show()
	{
		pd.show();
		View view = inflater.inflate(R.layout.progress_dialog, null);
		pd.setContentView(view);
	}
	public void dismiss()
	{
		if(pd.isShowing())
		pd.dismiss();
	}
}
