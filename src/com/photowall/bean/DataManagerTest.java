package com.photowall.bean;

import java.util.ArrayList;

import com.photowall.photowallcommunity.R;
import com.photowall.ui.explore.HotTag;

public class DataManagerTest {

	public static ArrayList<ArchievementItemBean> getArchiData()
	{
		ArrayList<ArchievementItemBean> list = new ArrayList<ArchievementItemBean>();
		
		for(int i=0;i<8;i++)
		{
			ArchievementItemBean bean = new ArchievementItemBean();
			if(i%2==0)
			{
			    bean.setTestDrawableid(R.drawable.testimg);
			}
			else
			{
			    bean.setTestDrawableid(R.drawable.testhead);
			}
			
			list.add(bean);
		}
		
		return list;
	}
	public static ArrayList<HotTag> getHottags()
	{
		ArrayList<HotTag> list = new ArrayList<HotTag>();
		for(int i=0;i<5;i++)
		{
			HotTag tag = new HotTag();
			tag.setName("Hot tags");
			list.add(tag);
		}
		return list;
	}
}
