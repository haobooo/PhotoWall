package com.photowall.ui.earchievement;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.photowall.bean.ArchievementItemBean;
import com.photowall.bean.DataManagerTest;
import com.photowall.photowallcommunity.R;
import com.photowall.ui.ArchievementPostsActivity;
import com.photowall.ui.earchievement.ArchievementDetailsActivity;
import com.photowall.ui.quests.QuestDetailsActivity;
import com.photowall.widget.staggedGridView.StaggeredGridView;

public class ChallengNewFragment extends Fragment {

    private String title;
    private View mView;
    private GridView hot_gridView;
    private GridAdapter gridAdapter;
    ArrayList<ArchievementItemBean> mlist;
    private ArchipaihangFramgmentActivity activity;
    public ChallengNewFragment(){};
//    public NewFragment(String title)
//    {
//        this.title = title;
//    };
    @Override
    public void setArguments(Bundle bundle) {
    	// TODO Auto-generated method stub
    	title = bundle.getString("title");
    	super.setArguments(bundle);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlist = new ArrayList<ArchievementItemBean>();
        activity = (ArchipaihangFramgmentActivity) getActivity();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        mView = inflater.inflate(R.layout.tiled_gridview_fragment,container, false);
        hot_gridView = (GridView) mView.findViewById(R.id.hot_gridView);
        gridAdapter = new GridAdapter(mlist, activity);
        hot_gridView.setAdapter(gridAdapter);
        hot_gridView.setOnItemClickListener(new GridView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(activity,ArchievementDetailsActivity.class);
                startActivity(intent); 
			}
            
        });
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	
    	Toast.makeText(activity, title+"", Toast.LENGTH_SHORT).show();
    	mlist.clear();
    	mlist.addAll(DataManagerTest.getArchiData());
    	gridAdapter.notifyDataSetChanged();
    	super.onActivityCreated(savedInstanceState);
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
//					Toast.makeText(activity, position+"", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(activity,QuestDetailsActivity.class);
					startActivity(intent);
				}
			});
			return view;
		}
    	
    }
}
