package com.photowall.ui.quests;

import java.util.ArrayList;

public class QuestGenerate {

	private static String[] titles={
			"Must In MY life",
			"Once In MY life",
			"Explore World Amazings",
			"Childhood dreams",
			"Must In MY life",
			"Once In MY life",
			"Explore World Amazings",
			"Childhood dreams"
	};
	
	public static ArrayList<QuestObject> getTestData()
	{
		ArrayList<QuestObject> list = new ArrayList<QuestObject>();
		for(int i=0;i<titles.length;i++)
		{
			QuestObject obj = new QuestObject();
			obj.setTitle(titles[i]);
			obj.setArchiNumber("100 archievements");
			list.add(obj);
		}
		return list;
	}
	
}
