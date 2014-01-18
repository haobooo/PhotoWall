package com.photowall.ui.explore;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.photowall.photowallcommunity.R;
import com.photowall.ui.QuestPageActivity;
import com.photowall.ui.quests.QuestObject;

public class QuestAdapter extends BaseAdapter {

	private ArrayList<QuestObject> list;
	private Context mContext;

	private LayoutInflater inflater;
	
	private Handler mainhaHandler;
	
	public QuestAdapter(ArrayList<QuestObject> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
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
		view = inflater.inflate(R.layout.explore_quest_item_layout, null);
		ImageView cover = (ImageView) view.findViewById(R.id.quest_cover);
		TextView title = (TextView) view.findViewById(R.id.quest_title);
		TextView quest_arch_number = (TextView) view.findViewById(R.id.quest_arch_number);
		
		QuestObject tempobj = list.get(pos);
		final int selpos = pos;
		
		title.setText(tempobj.getTitle());
		quest_arch_number.setText(tempobj.getArchiNumber());
		return view;
	}

	
}
