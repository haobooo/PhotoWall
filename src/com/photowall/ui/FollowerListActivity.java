package com.photowall.ui;

import java.util.ArrayList;

import com.photowall.bean.DataManagerTest;
import com.photowall.photowallcommunity.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class FollowerListActivity extends Activity {
	
	private ListView mListView;
	private Mydapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.follower_list_layout);
		
		initViews();
	}
	
	private void initViews() {
		mListView = (ListView) findViewById(android.R.id.list);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.addAll(DataManagerTest.getArchiData());
		mAdapter = new Mydapter(list, this);
		
		mListView.setAdapter(mAdapter);
	}
	
	private static class Mydapter extends BaseAdapter {

		private ArrayList<Object> mlist;
		private Context mcontext;
		private LayoutInflater inflater;
		
		public Mydapter(ArrayList<Object> mlist, Context mcontext) {
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
			view = inflater.inflate(R.layout.follower_list_item_layout, null);
			return view;
		}

	}

}