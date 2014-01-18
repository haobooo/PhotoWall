package com.photowall.bean;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.photowall.adapters.GroupAdapter;
import com.photowall.baseclass.PopupWindowList;
import com.photowall.net.UserContent;
import com.photowall.photowallcommunity.HelpActivity;
import com.photowall.photowallcommunity.PhotoWallSetting;
import com.photowall.photowallcommunity.R;
import com.photowall.photowallcommunity.SearchActivity;

public class PhotoWallTools{
    
    public static final int MENU_SEARCH = 0;
    public static final int MENU_HELP = 1;
    public static final int MENU_REPORT = 2;
    public static final int MENU_SETTING = 3;
    private static Context mContext;
    private static List<PopupWindowList> list;
    private static String title[] = { "Share","Invite Friends","Cancel","Delete","Search","Help","Report an issue","Setting" };
    private static Class<?> gotoClass[] = 
    {null,null,null,null,SearchActivity.class,HelpActivity.class,null,PhotoWallSetting.class};
//    private static boolean is[] = {false,false,false,false,true,true,true,true};//4个
//    private static boolean is1[] = {true,false,false,false,true,true,true,true};//5个 Share
//    private static boolean is2[] = {false,true,false,false,true,true,true,true};//5 Invite
//    private static boolean is3[] = {false,false,true,false,true,true,true,true};//5 Cancel
//    private static boolean is4[] = {false,false,false,true,true,true,true,true};//5 Delete
//    private static boolean is5[] = {false,false,false,false,false,true,true,true};//3 Search
//    private static boolean is6[] = {false,false,false,false,true,false,true,true};//3 Help
    
    public static UserContent mUserContent;
    
    public static void showPopupWindow(Activity context,View parent, int x,int y, boolean[] mark ) {
        final PopupWindow popupWindow;
        LinearLayout layout;
        ListView listView;
        GroupAdapter groupAdapter;
        mContext = context;
        list = new ArrayList<PopupWindowList>();
        final boolean isShare = mark[0];
        final boolean isInviteFriends = mark[1];
        final boolean isCancel = mark[2];
        final boolean isDelete = mark[3];
        final boolean isSearch = mark[4];
        final boolean isHelp = mark[5];
        final boolean isReport = mark[6];
        PopupWindowList  popupWindowList;
        for (int i = 0; i < title.length; i++) {
            popupWindowList = new PopupWindowList(title[i], gotoClass[i]);
            if (mark[i]) {
                list.add(popupWindowList);
            }
        }
        
        groupAdapter = new GroupAdapter(context, list);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pupop_window, null);
        listView = (ListView) layout.findViewById(R.id.pop_window_listview);
        listView.setAdapter(groupAdapter);
        popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;

        popupWindow.showAsDropDown(parent, xPos, 0);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                    int position, long id) {
                
                if (list.get(position).getClsString() != null) {
                    Intent intent;
                    UserContent userContent;
                    if (((Activity)mContext).getIntent() != null) {
                        intent = ((Activity)mContext).getIntent();
//                        Bundle bundle = new Bundle();
//                        bundle = intent.getExtras();
//                        userContent = (UserContent) bundle.getSerializable("usercontent");
                    }else {
                        intent = new Intent();
                    }
                    intent.setClass(mContext, list.get(position).getClsString());
                    mContext.startActivity(intent);
                    return;
                }
                
                if (isShare) {
                    setShare(list.get(position).getTitle(),null);
                    return;
                }
                
                if (isInviteFriends) {
                    return;
                }
                
                if (isCancel) {
                    ((Activity)mContext).finish();
                    return;
                }
                if (isReport) {
                    return;
                    
                }
                
//                switch (position) {
//                case MENU_SEARCH:
//                    Intent searchIntent = new Intent();
//                    searchIntent.setClass(mContext, SearchActivity.class);
//                    mContext.startActivity(searchIntent);
//                    break;
//                case MENU_HELP:
//                    Intent helpIntent = new Intent();
//                    helpIntent.setClass(mContext, HelpActivity.class);
//                    mContext.startActivity(helpIntent);
//                    break;
//                case MENU_REPORT:
////                    Intent feedbackIntent = new Intent();
////                    feedbackIntent.setClass(mContext, FeedBackActivity.class);
////                    mContext.startActivity(feedbackIntent);
//                    break;
//                case MENU_SETTING:
//                    Intent settingIntent = new Intent();
//                    settingIntent.setClass(mContext, PhotoWallSetting.class);
//                    mContext.startActivity(settingIntent);
//                    break;
//
//                default:
//                    break;
//                }
//                Toast.makeText(mContext,
//                        "groups.get(position)" + list.get(position).getTitle(), 1000)
//                        .show();

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }
    private static void setShare(String content, Uri uri){  
        Intent shareIntent = new Intent(Intent.ACTION_SEND);   
        if(uri!=null){  
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);  
            shareIntent.setType("image/*");   
            //当用户选择短信时使用sms_body取得文字  
            shareIntent.putExtra("sms_body", content);  
        }else{  
            shareIntent.setType("text/plain");   
        }  
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);  
        //自定义选择框的标题  
        //startActivity(Intent.createChooser(shareIntent, "邀请好友"));  
        //系统默认标题  
        mContext.startActivity(shareIntent);  
    }  
    
    public static boolean isNetworkAvailable(Context context) { 
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }
    
    /**
    * 获取指定路径，的数据。
    * 
    * **/
    public static byte[] getImage(String urlpath) throws Exception {
      URL url = new URL(urlpath);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setConnectTimeout(6 * 1000);
      // 别超过10秒。
      if(conn.getResponseCode()==200){
       InputStream inputStream=conn.getInputStream();
       return readStream(inputStream);
      }
      return null;
    }
    
    /**
     * 读取数据 
     * 输入流
     * 
     * */
   public static byte[] readStream(InputStream inStream) throws Exception {
     ByteArrayOutputStream outstream=new ByteArrayOutputStream();
     byte[] buffer=new byte[1024];
     int len=-1;
     while((len=inStream.read(buffer)) !=-1){
      outstream.write(buffer, 0, len);
     }
     outstream.close();
     inStream.close();
     
   return outstream.toByteArray();
   }
    
//    public static Bitmap getImage(String Url) throws Exception {
//        try {
//            URL url = new URL(Url);
//            String responseCode = url.openConnection().getHeaderField(0);
//            if (responseCode.indexOf("200") < 0)
//                throw new Exception("图片文件不存在或路径错误，错误代码：" + responseCode);
//            return BitmapFactory.decodeStream(url.openStream());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw new Exception(e.getMessage());
//        }
//
//    }
}
