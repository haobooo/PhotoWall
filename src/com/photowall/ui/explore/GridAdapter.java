package com.photowall.ui.explore;

import java.util.ArrayList;

import com.photowall.photowallcommunity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

	private ArrayList<HotTag> list;
	private Context mcontext;
	private LayoutInflater inflater;
	
	public GridAdapter(ArrayList<HotTag> list, Context mcontext) {
		this.list = list;
		this.mcontext = mcontext;
		inflater = LayoutInflater.from(mcontext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.explore_grid_item, null);
		TextView text = (TextView) view.findViewById(R.id.tag);
		text.setText(list.get(pos).getName());
		return view;
	}

}
