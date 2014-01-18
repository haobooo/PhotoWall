package com.photowall.demos;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.photowall.baseclass.ImageInfo;
import com.photowall.baseclass.PostInfo;
import com.photowall.baseclass.ProfileInfo;
import com.photowall.bean.ArchievementPostBean;
import com.photowall.list.ContentListElement;
import com.photowall.list.ListElement;
import com.photowall.photowallcommunity.R;

public class TestDataGenerators {

    private static List <ImageInfo> mImageInfos;
    private static ArrayList<ProfileInfo> mProfileInfos = new ArrayList<ProfileInfo>();
    private static String mArchievementTitle;
    private static ArrayList<ListElement> mUserListElement = new ArrayList<ListElement>();
    private static int mNum;
    public static Bitmap PIbitmap;
    
    public static List<ArchievementPostBean> getArchievementPostBeans(int nums)
    {
        ArrayList<ArchievementPostBean>  list = new ArrayList<ArchievementPostBean>();
        
        for(int i=0;i<nums;i++)
        {
            ArchievementPostBean bean = new ArchievementPostBean();
            bean.setUsername("zhangsan");
            bean.setImageinfo("ddddddddddddddddddaaaaaaaaaaa\ndddddddddddddddd");
            bean.setTime("2 days ago");
            bean.setCommentString("30 comments ");
            
            list.add(bean);
        }
        return list;
    }
    
    public static List<ImageInfo> getHotArchievement()
    {
        ArrayList<ImageInfo> list = new ArrayList<ImageInfo>();
        int mCont = 0;
        Bitmap bitmap = PIbitmap;
        

        for (int i = 0; i < mNum; i++) {
            ArrayList<PostInfo> list1 = new ArrayList<PostInfo>();
            for (int j = 0; j < mNum; j++) {
                PostInfo postInfo = new PostInfo();
                postInfo.setPiUserName("lisi");
//                postInfo.setPiBitmap(bitmap);
                postInfo.setComment("this is a test");
                postInfo.setOtherComment(mCont+" comments");
                postInfo.setLikeNum("100");
                postInfo.setTime("10 min ago");
                list1.add(postInfo);
            }
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImageInfo("从服务器获取数据 Hot Hot Hot Hot Hot Hot Hot"+"\n"+mArchievementTitle);
            imageInfo.setLocal("New York City");
            imageInfo.setUserName("Lisi");
            imageInfo.setImageMark(mArchievementTitle);
            imageInfo.setImageTitle("Good");
            imageInfo.setPostInfos(list1);
            mCont = i;
            list.add(imageInfo);
        }

        return list;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
        drawable.getIntrinsicWidth(),
        drawable.getIntrinsicHeight(),
        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;

    }
    public static void setHotArchievement(String mark, int nums){
        mArchievementTitle = mark;
        mNum = nums;
    }
    
    public static List<ImageInfo> getNewArchievement()
    {
        ArrayList<ImageInfo> list = new ArrayList<ImageInfo>();
        int mCont = 0;

        for (int i = 0; i < mNum; i++) {
            ImageInfo imageInfo = new ImageInfo();
            ArrayList<PostInfo> list1 = new ArrayList<PostInfo>();
            for (int j = 0; j < mNum; j++) {
                PostInfo postInfo = new PostInfo();
                postInfo.setPiUserName("zhangsan");
//                postInfo.setPiBitmap(bitmap);
                postInfo.setComment("this is a test2");
                postInfo.setOtherComment(mCont+" comments");
                postInfo.setLikeNum("200");
                postInfo.setTime("20 min ago");
                list1.add(postInfo);
            }
            imageInfo.setImageInfo("从服务器获取数据 New New New New New New New New New"+"\n"+mArchievementTitle);
            imageInfo.setLocal("New York City");
            imageInfo.setUserName("zhangsan");
            imageInfo.setImageMark(mArchievementTitle);
            imageInfo.setImageTitle("Love");
            imageInfo.setPostInfos(list1);
            list.add(imageInfo);
        }

        return list;
    }
    
    public static List<ImageInfo> getZiJiArchievement(){
        if (mImageInfos == null) {
            mImageInfos = new ArrayList<ImageInfo>();
        }
        return mImageInfos;
    }
    
    public static void setArchievement(Bitmap bitmap,String imageMark )
    {
        ArrayList<ImageInfo>  list = new ArrayList<ImageInfo>();

        PostInfo postInfo = new PostInfo();
        postInfo.setPiUserName("lisi");
        postInfo.setPiBitmap(bitmap);
        postInfo.setComment("this is a test");
        postInfo.setOtherComment("it's  ok");
        postInfo.setLikeNum("100");
        postInfo.setTime("10 min ago");
        
        if(mImageInfos == null){
            list = new ArrayList<ImageInfo>();
            for(int i=0;i<20;i++)
            {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setImageInfo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                imageInfo.setLocal("New York City");
                imageInfo.setUserName("zhangsan");
                imageInfo.setPostInfo(postInfo);
                imageInfo.setLocal("BeiJing");
                imageInfo.setImageMark(imageMark);
                list.add(imageInfo);
            }
        }else {
            list.addAll(mImageInfos);
        }
        mImageInfos.addAll(list);
    }
    //ProfileInfo 数据
    public static List<ProfileInfo> getOtherProfileInfos()
    {
        ArrayList<ProfileInfo> list = new ArrayList<ProfileInfo>();

        for (int i = 0; i < mNum; i++) {
            ProfileInfo imageInfo = new ProfileInfo();
            imageInfo.setAchievementTitle("Achm Name "+mArchievementTitle);
            imageInfo.setAchievedNum((i*2)+"");
            imageInfo.setChallengingNum(((i+1)*3)+"");
            list.add(imageInfo);
        }

        return list;
    }
    
    public static List<ProfileInfo> getOwnProfileInfos()
    {
        return mProfileInfos;
    }
    
    public static void setProfileInfos(Bitmap bitmap,String achmtitle,String achmMark,String location,String comments)
    {
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setAchievementTitle(achmtitle);
        profileInfo.setImageBitmap(bitmap);
        profileInfo.setAchievedNum(0+"");
        profileInfo.setChallengingNum(0+"");
        profileInfo.setLocation(location);
        profileInfo.setAchievementMark(achmMark);
        profileInfo.setAchievementInfo(comments);

        mProfileInfos.add(profileInfo);
    }
    
    //UserList 数据
    
    public static ArrayList<ListElement> getUserList()
    {
        return mUserListElement;
    }
    
    public static void setUserList(String name,String comment)
    {
        ContentListElement element = new ContentListElement();
        element.setTitle(name, comment);
        mUserListElement.add(element);
    }
    
    //achievement info 数据
    
}
