package com.photowall.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.util.Log;

public class HttpSession {

    
    public final String TAG = "HttpSession";
    public final String baseUrlString = "";
    
    public static final String BASE_URL = "http://dev.ilegend.me/app/api/";
    public static final String USERREG_STRING = "";
    public static final String LOGIN_STRING = "http://dev.ilegend.me/app/api/user/login";
    public static final String SIGNUP_STRING = "http://dev.ilegend.me/app/api/user/register";
    public static final String UPLOAD_FREE_POST = "http://dev.ilegend.me/app/api/achievement/AddFreePost";
    public static final String UPLOAD_ACHID_POST = "http://dev.ilegend.me/app/api/achievement/AddPostByAchid";
    public static final String GET_ACHI_LIST = "http://dev.ilegend.me/app/api/achievement/achilist?";
    public static final String GET_HOME_POST_LIST = "http://dev.ilegend.me/app/api/achievement/postlist?";
    public static final String UPDATE_POST_LIKE = "http://dev.ilegend.me/app/api/achievement/UpdatePostLike?";
    //
    public static final String GET_ARCHI_LIST_BY_TYPE_URL = BASE_URL+"achievement/AchiListByLifestyleId?";
    
    public static final String ADD_FRIEND = "http://dev.ilegend.me/app/api/user/addfriend";//post
    public static final String CHECK_IS_FRIEND = "http://dev.ilegend.me/app/api/user/checkisfriend";//post
    public static final String FRIEND_LIST = "http://dev.ilegend.me/app/api/user/friendlist";//get
    public static final String POST_LIST_BY_ACHID = "http://dev.ilegend.me/app/api/achievement/postlistbyachid?";//post
//    public static final String UPDATE_POST_LIKE = "http://dev.ilegend.me/app/api/achievement/postlistbyachid";//get
//    public static final String UPDATE_POST_LIKE = "http://dev.ilegend.me/app/api/achievement/UpdatePostLike?";
    
    private ErrCode errCode = new ErrCode();
    private UserContent userContent = new UserContent();
    private List<AchmInfo> mAchmInfos= new ArrayList<AchmInfo>();
    private List<HomePostInfo> mHomePostInfos= new ArrayList<HomePostInfo>();
    
    //登陆，注册
    public String getRequestResult(String actionUrl,Map<String, String> params) throws Exception
    {
        
        URL uri = new URL(actionUrl); 
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection(); 
        conn.setReadTimeout(60 * 1000); //请求超时
        conn.setDoInput(true);//
        conn.setDoOutput(true);//
        conn.setUseCaches(false);// 
        conn.setRequestMethod("POST");//post  
        conn.setRequestProperty("Charset", "UTF-8"); //
        
        //
      //构建请求参数
        StringBuffer sb = new StringBuffer();
        if(params!=null){
            
            for (Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        //send
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream()); 
        outStream.write(sb.toString().getBytes());
        
        //receive
        int rescode = conn.getResponseCode();
        InputStream in = null;
        if (rescode == 200) {//success
          in = conn.getInputStream(); 
          StringBuffer sBuffer  = new StringBuffer();
          int len = 0;
          byte[] b = new byte[1024];
          byte[] temp = null;
          while((len = in.read(b))!=-1)
          {
              temp = new byte[len];
              System.arraycopy(b, 0, temp, 0, len);
              sBuffer.append(new String(temp));
          }
          
          in.close();
          outStream.close();
          conn.disconnect();
          return sBuffer.toString();
        } else {
        	 outStream.close();
             conn.disconnect();
            return "code:"+rescode+"";
        }
       
    }
    
    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     * 
     * @param url Service net address
     * @param params text content
     * @param files pictures
     * @return String result of Service response
     * @throws IOException
     */
    public  String getPostResult(String url, Map<String, String> params, Map<String, File> files)
            throws IOException {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";


        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
//        conn.setReadTimeout(2*60 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);


        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }


        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null)
        {
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\""+"imagename"+"\"; filename=\""
                        + file.getValue().getName() + "\"" + LINEND);
                sb1.append("Content-Type: image/*" + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());


                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[500];
                int len = 0;
                long totalbytes = file.getValue().length();
                long uploadbytes  = 0;
                while ((len = is.read(buffer)) != -1) {
                	uploadbytes+=len;
                    outStream.write(buffer, 0, len);
                   double p= (uploadbytes*1.00)/totalbytes;
                   if(postImageListener!=null)
                   {
                	   postImageListener.uploadingProgress(p);
                   }
                }
                is.close();
                outStream.write(LINEND.getBytes());
            }

        }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        StringBuilder sb2 = new StringBuilder();
        if (res == 200) {
            byte[] tempbyte = new byte[1024];
            byte[] bff=null;
            int len = 0;
            while ((len = in.read(tempbyte)) != -1) {
                bff = new byte[len];
                System.arraycopy(tempbyte, 0, bff, 0, len);
                sb2.append(new String(bff));
            }
        }else {
        	sb2.append("{errorcode:"+res+",error:http error}");
        }
        in.close();
        outStream.close();
        conn.disconnect();
        
        Log.i("httpsession",""+sb2.toString());
        return sb2.toString();
    }
    
    //Login登陆界面
    public boolean Login(String actionUrl,Map<String, String> params)
    {
        
        try {
            String result = getRequestResult(actionUrl, params);
            Log.i(TAG, "result:" + result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);

            if(jsonObject == null)
            {
            	errCode.setError(result);
             	return false;
            }
            Object errnum = jsonObject.get("errorcode");
            
            if (errnum != null){
                errCode.setErrcode((Long)jsonObject.get("errorcode"));
                errCode.setError((String) jsonObject.get("error"));
                return false;
            } else if(jsonObject.get("content")!=null)
            {
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("content");
                userContent.setUserId((String) jsonObject1.get("userid"));
                userContent.setUserToken((String) jsonObject1.get("usertoken"));
                userContent.setUserEmail((String) jsonObject1.get("useremail"));
                userContent.setUserFirstName((String) jsonObject1.get("userfirstname"));
                userContent.setUserLastName((String) jsonObject1.get("userlastname"));
                userContent.setUserNickName((String) jsonObject1.get("usernickname"));
                userContent.setUserFolder((String) jsonObject1.get("userfolder"));
                userContent.setUserImgurl((String) jsonObject1.get("userimgurl"));
                userContent.setUserTitle((String) jsonObject1.get("usertitle"));
                userContent.setUserFBId((String) jsonObject1.get("userfbid"));
                userContent.setUserFBName((String) jsonObject1.get("userfbname"));
                userContent.setUserType((String) jsonObject1.get("usertype"));
                userContent.setUserLogInCount((String) jsonObject1.get("userlogincount"));
                userContent.setUserFollowerCount((String) jsonObject1.get("userfollowercount"));
                userContent.setUserPostcount((String) jsonObject1.get("userpostcount"));
                userContent.setUserLastLoginSource((String) jsonObject1.get("userlastloginsource"));
                userContent.setUserLastLoginDate((String) jsonObject1.get("userlastlogindate"));
                return true;
            }
            else//http system error
            {
            	 errCode.setError(result);
            	return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    //注册数据获取
    public boolean SignUp(String actionUrl,Map<String, String> params)
    {
        
        try {
            String result = getRequestResult(actionUrl,params);
            Log.i(TAG,"result:"+result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
           
            Object errnum = jsonObject.get("errorcode");
            if(errnum != null)
            {
                errCode.setErrcode((Long) jsonObject.get("errorcode"));
                errCode.setError((String)jsonObject.get("error"));
                return false;
                
            }else{
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("content");
                String userId = (String) jsonObject1.get("userid");
                String msg = (String) jsonObject1.get("msg");
                userContent.setUserId(userId);
                return true;
            }
            
            
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
    }
    //upload post
    public boolean  uploadPost(String url, Map<String, String> params, Map<String, File> files){
        String result = null;
        try {
            result = getPostResult(url,params,files);
            Log.i(TAG, "result:" + result);
            if(result !=null)
            {
	            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
	            Object errnum = jsonObject.get("errorcode");
	            if (errnum != null){
	                errCode.setErrcode((Long)jsonObject.get("errorcode"));
	                errCode.setError((String) jsonObject.get("error"));
	                return false;
	            } else if(jsonObject.get("content")!=null)
	            {
	                JSONObject jsonObject1 = (JSONObject) jsonObject.get("content");
	                String pId = (String) jsonObject1.get("pid");
	                String msg = (String) jsonObject1.get("msg");
	                Log.i(TAG, "userId:" + pId);
	                return true;
	            }
	            else
	            {
	            	errCode.setError(result);
	            	return false;
	            }
            }
            else
            {
            	errCode.setError("getResponseCode == null");
            	return false;
            }
        } catch (Exception e) {
        	errCode.setError(e.getMessage());
            return false;
        }
//        return true;
    }
    //获取数据
    public String getHttpAchm(String url, Map<String, String> params) throws Exception{

        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000); // 请求超时
        conn.setDoInput(true);//
        conn.setDoOutput(true);//
        conn.setUseCaches(false);//
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Charset", "UTF-8"); //

        //
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {

            for (Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        // send
        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes());

        // receive
        int rescode = conn.getResponseCode();
        InputStream in = null;
        if (rescode == 200) {// success
            in = conn.getInputStream();

            StringBuffer sBuffer = new StringBuffer();
            int len = 0;
            byte[] b = new byte[1024];
            byte[] temp = null;
            while ((len = in.read(b)) != -1) {
                temp = new byte[len];
                System.arraycopy(b, 0, temp, 0, len);

                sBuffer.append(new String(temp));
            }

            in.close();
            outStream.close();
            conn.disconnect();
            return sBuffer.toString();
        } else {
            return null;
        }
    }

    //achmInfo
    public boolean  getAchmInfo(String url, Map<String, String> params){
        String result = null;
        mAchmInfos.clear();
        try {
            result = getHttpAchm(url,params);
            Log.i(TAG, "result:" + result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
            Object errnum = jsonObject.get("errorcode");
            if (errnum != null){
                errCode.setErrcode((Long)jsonObject.get("errorcode"));
                errCode.setError((String) jsonObject.get("error"));
                return false;
            } else {
                Object Object = jsonObject.get("content");
                JSONArray jsonArray = (JSONArray)Object;
                Log.i(TAG, "--------11-------jsonObject1:"+jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    AchmInfo achmInfo = new AchmInfo();
                    JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                    Log.i(TAG, "--------11-------jsonObject1:"+jsonObject1.toString());
                    achmInfo.setAchid((String) jsonObject1.get("achid"));
                    achmInfo.setAchiname((String) jsonObject1.get("achiname"));
                    achmInfo.setAchides((String) jsonObject1.get("achides"));
                    achmInfo.setAchiimg((String) jsonObject1.get("achiimg"));
                    achmInfo.setAchivideo((String) jsonObject1.get("achivideo"));
                    achmInfo.setAchivideosource((String) jsonObject1.get("achivideosource"));
                    achmInfo.setAchiviewcount((String) jsonObject1.get("achiviewcount"));
                    achmInfo.setAchifollowcount((String) jsonObject1.get("achifollowcount"));
                    achmInfo.setAchicompletecount((String) jsonObject1.get("achicompletecount"));
                    achmInfo.setAchicreatedate((String) jsonObject1.get("achicreatedate"));
                    achmInfo.setLsname((String) jsonObject1.get("lsname"));
                    achmInfo.setLsid((String) jsonObject1.get("lsid"));
                    achmInfo.setUserid((String) jsonObject1.get("userid"));
                    achmInfo.setUserfolder((String) jsonObject1.get("userfolder"));
                    achmInfo.setUsernickname((String) jsonObject1.get("usernickname"));
                    achmInfo.setUserimg((String) jsonObject1.get("userimg"));
                    mAchmInfos.add(achmInfo);
                }
                Log.i(TAG, "--------00-------result:"+mAchmInfos.size());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //get all post
    public boolean  getHomePostInfo(String url, Map<String, String> params){
        String result = null;
        mHomePostInfos.clear();
        try {
            result = getHttpAchm(url,params);
            Log.i(TAG, "result:" + result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
            Object errnum = jsonObject.get("errorcode");
            if (errnum != null){
                errCode.setErrcode((Long)jsonObject.get("errorcode"));
                errCode.setError((String) jsonObject.get("error"));
                return false;
            } else {
                Object Object = jsonObject.get("content");
                JSONArray jsonArray = (JSONArray)Object;
                for (int i = 0; i < jsonArray.size(); i++) {
                    HomePostInfo homePostInfo = new HomePostInfo();
                    JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                    homePostInfo.setPid((String) jsonObject1.get("pid"));
                    homePostInfo.setAchid((String) jsonObject1.get("achid"));
                    homePostInfo.setAchiname((String) jsonObject1.get("achiname"));
                    homePostInfo.setLifestyleid((String) jsonObject1.get("lifestyleid"));
                    homePostInfo.setLifestyleid((String) jsonObject1.get("lifestylename"));
                    homePostInfo.setPtitle((String) jsonObject1.get("ptitle"));
                    homePostInfo.setPcontent((String) jsonObject1.get("pcontent"));
                    homePostInfo.setPvurl((String) jsonObject1.get("pvurl"));
                    homePostInfo.setPvsource((String) jsonObject1.get("pvsource"));
                    homePostInfo.setPstatus((String) jsonObject1.get("pstatus"));
                    homePostInfo.setPcreatedate((String) jsonObject1.get("pcreatedate"));
                    homePostInfo.setPreplycount((String) jsonObject1.get("preplycount"));
                    homePostInfo.setPlikecount((String) jsonObject1.get("plikecount"));
                    homePostInfo.setPviewcount((String) jsonObject1.get("pviewcount"));
                    homePostInfo.setPcommentcount((String) jsonObject1.get("pcommentcount"));
                    homePostInfo.setPlocation((String) jsonObject1.get("plocation"));
                    homePostInfo.setPimgid((String) jsonObject1.get("pimgid"));
                    homePostInfo.setPimgname((String) jsonObject1.get("pimgname"));
                    homePostInfo.setPimgwidth((String) jsonObject1.get("pimgwidth"));
                    homePostInfo.setPimgheight((String) jsonObject1.get("pimgheight"));
                    homePostInfo.setPimgtype((String) jsonObject1.get("pimg_type"));
                    homePostInfo.setUserid((String) jsonObject1.get("userid"));
                    homePostInfo.setUsernickname((String) jsonObject1.get("usernickname"));
                    homePostInfo.setUserimgurl((String) jsonObject1.get("userimgurl"));
                    homePostInfo.setUserfolder((String) jsonObject1.get("userfolder"));
                    mHomePostInfos.add(homePostInfo);
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    //update like counts
    public boolean  updatePostLike(String url, Map<String, String> params){
    	
    	// result:{"errorcode":50006,"error":"LIKE POST\u5df2\u5b58\u5728"} 
        try {
            String result = getPostResult(url, params, null);//getRequestResult(url,params);
            Log.i(TAG,"result:"+result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
           
            Object errnum = jsonObject.get("errorcode");
            if(errnum != null)
            {
                errCode.setErrcode((Long) jsonObject.get("errorcode"));
                errCode.setError((String)jsonObject.get("error"));
                return false;
                
            }else{
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("content");
                String userId = (String) jsonObject1.get("userid");
                String msg = (String) jsonObject1.get("msg");
                return true;
            }
            
            
        } catch (Exception  e) {
            return false;
        }
    }
    
  //get achi post
    public boolean  getAchiPost(String url, Map<String, String> params){
        mHomePostInfos.clear();
        try {
            String result = getHttpAchm(url,params);
            Log.i(TAG,"result:"+result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
           
            Object errnum = jsonObject.get("errorcode");
            if(errnum != null)
            {
                errCode.setErrcode((Long) jsonObject.get("errorcode"));
                errCode.setError((String)jsonObject.get("error"));
                return false;
                
            }else{
                Object Object = jsonObject.get("content");
                JSONArray jsonArray = (JSONArray)Object;
                for (int i = 0; i < jsonArray.size(); i++) {
                    HomePostInfo homePostInfo = new HomePostInfo();
                    JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                    homePostInfo.setPid((String) jsonObject1.get("pid"));
                    homePostInfo.setAchid((String) jsonObject1.get("achid"));
                    homePostInfo.setAchiname((String) jsonObject1.get("achiname"));
                    homePostInfo.setLifestyleid((String) jsonObject1.get("lifestyleid"));
                    homePostInfo.setLifestyleid((String) jsonObject1.get("lifestylename"));
                    homePostInfo.setPtitle((String) jsonObject1.get("ptitle"));
                    homePostInfo.setPcontent((String) jsonObject1.get("pcontent"));
                    homePostInfo.setPvurl((String) jsonObject1.get("pvurl"));
                    homePostInfo.setPvsource((String) jsonObject1.get("pvsource"));
                    homePostInfo.setPstatus((String) jsonObject1.get("pstatus"));
                    homePostInfo.setPcreatedate((String) jsonObject1.get("pcreatedate"));
                    homePostInfo.setPreplycount((String) jsonObject1.get("preplycount"));
                    homePostInfo.setPlikecount((String) jsonObject1.get("plikecount"));
                    homePostInfo.setPviewcount((String) jsonObject1.get("pviewcount"));
                    homePostInfo.setPcommentcount((String) jsonObject1.get("pcommentcount"));
                    homePostInfo.setPlocation((String) jsonObject1.get("plocation"));
                    homePostInfo.setPimgid((String) jsonObject1.get("pimgid"));
                    homePostInfo.setPimgname((String) jsonObject1.get("pimgname"));
                    homePostInfo.setPimgwidth((String) jsonObject1.get("pimgwidth"));
                    homePostInfo.setPimgheight((String) jsonObject1.get("pimgheight"));
                    homePostInfo.setPimgtype((String) jsonObject1.get("pimg_type"));
                    homePostInfo.setUserid((String) jsonObject1.get("userid"));
                    homePostInfo.setUsernickname((String) jsonObject1.get("usernickname"));
                    homePostInfo.setUserimgurl((String) jsonObject1.get("userimgurl"));
                    homePostInfo.setUserfolder((String) jsonObject1.get("userfolder"));
                    mHomePostInfos.add(homePostInfo);
                }
                return true;
            }
            
            
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //get user friend list
    public boolean getUserFriends(String url, Map<String, String> params){
        try {
            String result = getRequestResult(url,params);
            Log.i(TAG,"result:"+result);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
           
            Object errnum = jsonObject.get("errorcode");
            if(errnum != null)
            {
                errCode.setErrcode((Long) jsonObject.get("errorcode"));
                errCode.setError((String)jsonObject.get("error"));
                return false;
                
            }else{
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("content");
                String userId = (String) jsonObject1.get("userid");
                String msg = (String) jsonObject1.get("msg");
                userContent.setUserId(userId);
                return true;
            }
            
            
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLikeNumber(String url, Map<String, String> params)
    {
    	try {
			String result = getPostResult(url,params,null);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
    
    public UserContent getUserContent() {
        return userContent;
    }
    public void setUserContent(UserContent userContent) {
        this.userContent = userContent;
    }
    
    public ErrCode getErrCode() {
        return errCode;
    }

    public void setErrCode(ErrCode errCode) {
        this.errCode = errCode;
    }
    
    public List<AchmInfo> getmAchmInfos() {
        return mAchmInfos;
    }

    public void setmAchmInfos(List<AchmInfo> mAchmInfos) {
        this.mAchmInfos = mAchmInfos;
    }

    public List<HomePostInfo> getmHomePostInfos() {
        return mHomePostInfos;
    }

    public void setmHomePostInfos(List<HomePostInfo> mHomePostInfos) {
        this.mHomePostInfos = mHomePostInfos;
    }

    /////######################
    private  PostImageListener postImageListener;
    public void setPostImageListener(PostImageListener listener)
    {
    	this.postImageListener = listener;
    }
    
    public interface PostImageListener
    {
    	public void uploadingProgress(double percent);
    	public void uploadComplete();
    }
}

