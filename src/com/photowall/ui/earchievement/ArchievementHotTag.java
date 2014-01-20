package com.photowall.ui.earchievement;

import java.util.ArrayList;

import com.photowall.bean.ArchievementItemBean;
import com.photowall.bean.DataManagerTest;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ArchievementHotTag extends Activity 
implements View.OnClickListener
{
	private ImageView menu_image;
	
	private TextView title_text;
	private GridView gridView;
	private GridAdapter gridAdapter;
	
	private ImageView back_image;
    ArrayList<ArchievementItemBean> mlist = new ArrayList<ArchievementItemBean>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.archi_hottag_layout);
		initviews();
	}
	public void initviews()
	{
		menu_image = (ImageView) findViewById(R.id.menu_image);
		menu_image.setOnClickListener(this);
		
		back_image = (ImageView) findViewById(R.id.back_image);
		back_image.setOnClickListener(this);
		
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("Hot TAG NAME");
		
		gridView = (GridView) findViewById(R.id.gridView);
		gridAdapter = new GridAdapter(mlist, this);
		gridView.setAdapter(gridAdapter);
		
		//test
		mlist.addAll(DataManagerTest.getArchiData());
    	gridAdapter.notifyDataSetChanged();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.back_image:
			{
				finish();
				
				break;
			}
			case R.id.menu_image:
			{
				settings();
				break;
			}
		}
	}
	public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
	 private class GridAdapter extends BaseAdapter
	    {

	    	private ArrayList<ArchievementItemBean> list;
	    	private Context context;
	    	private LayoutInflater inflater;
	    	
			public GridAdapter(ArrayList<ArchievementItemBean> list, Context context) {
				this.list = list;
				this.context = context;
				inflater = LayoutInflater.from(context);
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
				view = inflater.inflate(R.layout.tiled_grid_card_item_layout, null);
				ImageView quest_border = (ImageView) view.findViewById(R.id.quest_border);
				final int position = pos;
				quest_border.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Toast.makeText(ArchievementHotTag.this, position+"", Toast.LENGTH_SHORT).show();
					}
				});
				return view;
			}
	    	
	    }
}
