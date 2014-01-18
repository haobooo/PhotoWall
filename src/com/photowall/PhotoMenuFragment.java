package com.photowall;

import com.photowall.photowallcommunity.FeedBackActivity;
import com.photowall.photowallcommunity.HomePageActivity;
import com.photowall.photowallcommunity.NotificationsActivity;
import com.photowall.photowallcommunity.ProfileActivity;
import com.photowall.photowallcommunity.R;
import com.photowall.ui.ExploreActivity;
import com.photowall.ui.PhotoProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhotoMenuFragment extends Fragment 
implements View.OnClickListener
{

    private ViewGroup archievement_menu;
    private ViewGroup layout_notifications;
    private ViewGroup layout_user_name;
    private ViewGroup layout_feed_back;
    private ViewGroup explore_menu;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_menu, container,false);
        archievement_menu = (ViewGroup) view.findViewById(R.id.archievement_menu);
        layout_notifications = (ViewGroup) view.findViewById(R.id.layout_notifications);
        layout_user_name = (ViewGroup) view.findViewById(R.id.layout_user_name);
        layout_feed_back = (ViewGroup) view.findViewById(R.id.layout_feed_back);
        explore_menu = (ViewGroup) view.findViewById(R.id.explore_menu);
        
        archievement_menu.setOnClickListener(this);
        layout_notifications.setOnClickListener(this);
        layout_user_name.setOnClickListener(this);
        layout_feed_back.setOnClickListener(this);
        explore_menu.setOnClickListener(this);
        
        return view; 
    }

    @Override
    public void onClick(View v) {
        
        
        
        switch (v.getId()) {
        
            case R.id.archievement_menu:
            {
            	Fragment newContent = null;
                newContent = new ViewPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pagetype", ViewPagerFragment.PAGE_TYPE_ARCHIEVEMENT);
                newContent.setArguments(bundle);
                if (newContent != null)
                    switchFragment(newContent);
                break;
            }
            case R.id.layout_notifications:
            {
            	Intent notfIntent = new Intent();
                notfIntent.setClass(getActivity(), NotificationsActivity.class);
                startActivity(notfIntent);
            	break;
            }
            case R.id.layout_user_name:
            {
            	Intent userIntent = new Intent();
                userIntent.setClass(getActivity(), PhotoProfileActivity.class);
                userIntent.putExtra("title_mark", "own");
                userIntent.putExtra("user_name", "User Name");
                startActivity(userIntent);
            	break;
            }
            case R.id.layout_feed_back:
            {
            	Intent feedbackIntent = new Intent();
                feedbackIntent.setClass(getActivity(), FeedBackActivity.class);
                startActivity(feedbackIntent);
            	break;
            }
            case R.id.explore_menu:
            {
            	Intent feedbackIntent = new Intent();
                feedbackIntent.setClass(getActivity(), ExploreActivity.class);
                startActivity(feedbackIntent);
            	break;
            }
        }
        
    }
    
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;
        if (getActivity() instanceof PhotoHomePageActivity) {
            PhotoHomePageActivity fca = (PhotoHomePageActivity) getActivity();
            fca.switchContent(fragment);
        } 
    }
}
