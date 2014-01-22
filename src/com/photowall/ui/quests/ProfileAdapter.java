package com.photowall.ui.quests;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.photowall.photowallcommunity.R;


public class ProfileAdapter extends BaseAdapter {

	private ArrayList<Object> mlist;
	private Context mcontext;
	private LayoutInflater inflater;
	
	public ProfileAdapter(ArrayList<Object> mlist, Context mcontext) {
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
		view = inflater.inflate(R.layout.tiled_grid_card_item_layout, null);
		return view;
	}

}
