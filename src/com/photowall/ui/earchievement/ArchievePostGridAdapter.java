package com.photowall.ui.earchievement;

import java.util.ArrayList;

import com.photowall.baseclass.PostInfo;
import com.photowall.photowallcommunity.R;
import com.photowall.ui.post.PostObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ArchievePostGridAdapter extends BaseAdapter {

	private ArrayList<PostObject> mlist;
	private Context mcontext;
	private LayoutInflater inflater;
	
	public ArchievePostGridAdapter(ArrayList<PostObject> mlist, Context mcontext) {
		super();
		this.mlist = mlist;
		this.mcontext = mcontext;
		inflater = LayoutInflater.from(mcontext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		view = inflater.inflate(R.layout.post_grid_item_layout, null);
		return view;
	}

}
